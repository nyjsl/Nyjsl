package org.nyjsl.androidlearning.rxjava2;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by weixing on 2017/5/25.
 * E-mail:wei.xing@ucsmy.com
 */

public class Scheduler {

    public void testSwitchThread() {
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<String> e) throws Exception {
                System.out.println(Thread.currentThread().getName());
                e.onNext("能不能");
            }
        }).subscribeOn(Schedulers.newThread()).observeOn(Schedulers.newThread()).subscribe(new Consumer<String>() {
            @Override
            public void accept(@NonNull String s) throws Exception {
                System.out.println(s);
                System.out.println(Thread.currentThread().getName());
            }
        });
    }
}
