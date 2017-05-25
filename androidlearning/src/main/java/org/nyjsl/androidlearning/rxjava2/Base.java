package org.nyjsl.androidlearning.rxjava2;


import android.util.AndroidRuntimeException;
import android.util.Log;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by weixing on 2017/5/25.
 * E-mail:wei.xing@ucsmy.com
 */

public class Base {

    public void testObservable(){
       Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<String> e) throws Exception {

                e.onNext("我");
                e.onNext("怎么");
                e.onNext("失去了你");
                e.onNext("在这拥挤的人潮中");
                e.onComplete();
//                e.onError(new AndroidRuntimeException());
            }
        }).subscribe(new Observer<String>() {
           @Override
           public void onSubscribe(@NonNull Disposable d) {
               System.out.println("onSubscribe");
           }

           @Override
           public void onNext(@NonNull String s) {
               System.out.println(s);
           }

           @Override
           public void onError(@NonNull Throwable e) {
               System.out.println("onError");
           }

           @Override
           public void onComplete() {
               System.out.println("onComplete");
           }
       });


        final ObservableOnSubscribe<Integer> observableOnSubscribe = new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter e) throws Exception {
                e.onNext(5);
                e.onNext(2);
                e.onNext(0);
                e.onComplete();
            }
        };
        Observable.create(observableOnSubscribe).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(@NonNull Integer integer) throws Exception {
                System.out.println(integer);
            }
        });
    }
}
