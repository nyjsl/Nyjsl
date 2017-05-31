package org.nyjsl.androidlearning.rxjava2;

/**
 * Created by weixing on 2017/5/25.
 * E-mail:wei.xing@ucsmy.com
 */

public class Test {

    public static void main(String[] args) {
//        new Base().testObservable();
//        new Scheduler().testSwitchThread();
//        try {
//            Thread.sleep(3000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

//        new Transform().testMap();
//        new Transform().testFlatMap();
//        new Transform().testZip();
//        new Operators().testFilter();
//        new Operators().testTake();
//        new Operators().testAll();
        new Operators().testDebounce();
//        new Operators().testContains();
//        new Operators().testDefaultEmpty();
//        new Operators().testSkipWhile();
//        new Operators().testTakeUnitl();
//        new Operators().testSequenceEqual();
//        new Creation().testCreate();
//        new Creation().testDefer();
        try {
            Thread.sleep(6000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
