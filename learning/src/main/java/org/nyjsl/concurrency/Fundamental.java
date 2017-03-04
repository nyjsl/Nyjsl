package org.nyjsl.concurrency;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;

/**
 * Created by pc on 2017/3/1.
 */

public class Fundamental {

    //volatile 关键字,用来确保对一个变量的修改被正确传播到其他线程中
    //final 关键词,在代码执行时,final域的值可以被保存在寄存器中,而不用从主寸中频繁读取
    //JAVA提供的线程同步机制:

    //1.synchronized关键字:方法或代码块的互斥性来完成实际上的一个原子操做.所有的Java对象都有一个与synchronized关联的监视器对象(mointor),允许线程在该对象上进行加锁和解锁操纵
    //2.Object 对象的wait[将当前线程放入该对象的等待池中] notify[将该对象中等待池中的线程,随机选取一个放入的对象锁池,当前线程结束后释放掉锁]
    // 和 notifyAll[将对象中等待池中的线程,全部放入锁池] 方法   生产者和消费者模型

    //线程之间同步机制的核心是监视器对象上的锁,竞争锁来获得执行代码的机会.当一个对象获取对象的锁,然后其他尝试获取锁的对象会处于等待状态,这种锁机制的实现方式很大
    //程度限制了多线程程序的吞吐量和性能,且会带来死锁和优先级倒置等问题.如果能不阻塞线程,又能保证对线程的正确顺序,就有更好的性能.

    //在程序中对共享变量的使用一般遵循一定的模式,即读取,修改和写入三步组成.这三步操作执行中可能切换了线程,造成了非原子操作.锁机制是把这三步变成一个原子操作

    //高级同步机制:
    //Lock,ReadWriteLock,ReentrantLock,ReentrantReadWriteLock
    //Condition

    //高级同步对象:
    //Seamphore 信号量,信号量一般用在数量有限的资源,每类资源有一个对象的信号量,信号量的值表示资源的可用数量.
    //CountDownLatch 一个线程等待另外的线程完成任务才能继续
    //CyclicBarrier
    //Exchanger
    public static void main(String[] args) {
//        testWorkerVolatile();
//        testWrongUseOfVolatile();
//        testSemap();

        testCountDownLatch();
    }

    private static void testCountDownLatch() {
        List<String> urls = new ArrayList<>();
        urls.add("first");
        urls.add("second");
        urls.add("third");
        urls.add("forth");
        CountDownLatch cdl = new CountDownLatch(urls.size());
        for(String s: urls){
            new Thread(new PageSizeSorter.GetSizeWorker(s,cdl)).start();
        }
        try {
            cdl.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("now completed");
    }

    private static void testSemap() {
        try {
            testSemaphore();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void testSemaphore() throws InterruptedException {
        List<Printer> printers = new ArrayList<>();
        printers.add(new Printer("aaaaa"));
        printers.add(new Printer("bbbbb"));
        printers.add(new Printer("ccccc"));
        final PrinterManager printerManager = new PrinterManager(printers);
        final Printer printer1 = printerManager.acquirePrinter();
        final Printer printer2 = printerManager.acquirePrinter();
        final Printer printer3 = printerManager.acquirePrinter();

        System.out.println("name1:"+printer1.getName());
        System.out.println("name2:"+printer2.getName());
        System.out.println("name3:"+printer3.getName());

        printerManager.releasePrinter(printer1);
        printerManager.releasePrinter(printer2);
        final Printer printer4 = printerManager.acquirePrinter();
        System.out.println("name4:"+printer4.getName());
        final Printer printer5 = printerManager.acquirePrinter();
        System.out.println("name5:"+printer5.getName());
    }

    //每次运行的count结果都不一样
    private static void testWrongUseOfVolatile() {
        final Counter counter = new Counter();
        for(int i=0;i<100;i++){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    counter.inc();
                }
            }).start();
        }
        System.out.println("count = [" + counter.count + "]");
    }

    private static void testWorkerVolatile() {
        final Worker worker = new Worker();

        new Thread(new Runnable() {
            @Override
            public void run() {
                worker.work();
            }
        }).start();


        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(4000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                worker.setDone(true);
            }
        }).start();

        System.out.println("args = [" + 11111 + "]");
    }

    private static class Worker{
        private volatile  boolean done;

        public boolean isDone() {
            return done;
        }

        public void setDone(boolean done) {
            this.done = done;
        }

        public void work(){
            while(!isDone()){
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("I'm Working");
            }
        }
    }


    //错误使用,volatile的修改不能依赖于当前值,当前值可能在其他线程中被修改
    private static class Counter{
        public volatile int count = 0;

        public void inc() {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            count++;
        }
    }


    private static class PrinterManager {
        private final Semaphore semaphore;
        private final List<Printer> printers = new ArrayList<>();

        public PrinterManager(Collection<? extends Printer> printers) {
            this.printers.addAll(printers);
            semaphore = new Semaphore(this.printers.size(), true);
        }

        public Printer acquirePrinter() throws InterruptedException {
            semaphore.acquire();
            return getAvaliablePrinter();
        }

        public void releasePrinter(Printer printer){
            putBackPrinter(printer);
            semaphore.release();
        }

        private synchronized Printer getAvaliablePrinter(){
            final Printer printer = printers.get(0);
            printers.remove(0);
            return printer;
        }

        private synchronized void putBackPrinter(Printer printer) {
            printers.add(printer);
        }
    }

    private static class Printer{

        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Printer(String name) {
            this.name = name;
        }
    }


    private static class PageSizeSorter{

        private static class GetSizeWorker implements Runnable{

            private final String urlString;
            private final CountDownLatch signal;

            public GetSizeWorker(String urlString, CountDownLatch signal) {
                this.urlString = urlString;
                this.signal = signal;
            }

            @Override
            public void run() {
                try {
                    System.out.println("i'm:"+urlString);
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    signal.countDown();
                }
            }
        }
    }

}
