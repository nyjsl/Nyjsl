package org.nyjsl.reflect;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by pc on 2017/3/15.
 */

public class BlockingQueueTest {

    public static void main(String[] args) {
//        testBlockingQueue(new LinkedBlockingDeque<>(10));
        testBlockingQueue(new ArrayBlockingQueue(10));
    }

    private static void testBlockingQueue(BlockingQueue queue) {
        Producer p1 = new Producer(queue);
        Producer p2 = new Producer(queue);
        Producer p3 = new Producer(queue);

        Consumer c = new Consumer(queue);

        final ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.execute(p1);
        executorService.execute(c);
        executorService.execute(p2);
        executorService.execute(p3);

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        p1.stop();
        p2.stop();
        p3.stop();

        executorService.shutdownNow();
    }


    public static class Producer implements Runnable{

        private final BlockingQueue queue;
        private volatile boolean isRunning = true;

        private AtomicInteger count = new AtomicInteger();

        public Producer(BlockingQueue queue) {
            this.queue = queue;
        }

        public void stop(){
            isRunning = false;
        }

        @Override
        public void run() {
            System.out.println("============启动生产者线程===========");
            try {
                while (isRunning){
                    System.out.println("===========正在生产数据=========");
                    Thread.sleep(1000);


                    final int i = count.incrementAndGet();
                    System.out.println("============将数据:"+i+"放入队列===========");

                    if(!queue.offer(i,2, TimeUnit.SECONDS)){
                        System.out.println("===========放入数据:"+i+"失败=============");
                    }else{
                        System.out.println("||||||||队列大小:"+queue.size()+"||||||||");
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }finally{
                System.out.println("========退出生产者线程=======");
            }
        }
    }

    public static class Consumer implements Runnable{

        private final BlockingQueue queue;
        private volatile boolean isRunning = true;

        public Consumer(BlockingQueue queue) {
            this.queue = queue;
        }

        @Override
        public void run() {
            System.out.println("=============启动消费者线程=============");
            try {
                while (isRunning){
                    System.out.println("=========正在从队列获取数据==========");
                    Integer data = (Integer) queue.poll(2,TimeUnit.SECONDS);
                    if(null != data){
                        System.out.println("=================拿到数据:"+data+"================");
                        System.out.println("=================正在消费数据:"+data+"================");

                    }else{
                        isRunning = false;
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            } finally {
                System.out.println("=============退出消费者线程==============");
            }
        }
    }
}
