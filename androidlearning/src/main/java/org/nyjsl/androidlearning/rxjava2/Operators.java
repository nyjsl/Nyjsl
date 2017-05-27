package org.nyjsl.androidlearning.rxjava2;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;

/**
 * Created by weixing on 2017/5/26.
 * E-mail:wei.xing@ucsmy.com
 */

public class Operators {

    public void testFilter() {
        List<String> strs = new ArrayList<>();
        strs.add("我爱洗澡");
        strs.add("皮肤好好");
        strs.add("最爱你的人是我");
        strs.add("你怎么舍得我难过");
        Flowable.fromIterable(strs).doOnNext(new Consumer<String>() {
            @Override
            public void accept(@NonNull String s) throws Exception {
                System.out.println("doOnNext");
            }
        }).doAfterNext(new Consumer<String>() {
            @Override
            public void accept(@NonNull String s) throws Exception {
                System.out.println("doAfterNext");
            }
        }).filter(new Predicate<String>() {
            @Override
            public boolean test(@NonNull String s) throws Exception {
                return s.contains("爱");
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(@NonNull String s) throws Exception {
                System.out.println(s);
            }
        });

    }


    public void testTake() {
        Flowable.create(new FlowableOnSubscribe<Integer>() {

            @Override
            public void subscribe(@NonNull FlowableEmitter<Integer> e) throws Exception {
                for (int i = 0; i < 50; i++) {
                    e.onNext(i);
                }
            }
        }, BackpressureStrategy.ERROR).take(10).takeLast(5).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(@NonNull Integer integer) throws Exception {
                System.out.println(integer);
            }
        });
    }


    public void testCombineLatested() {

        Observable.combineLatest(Observable.just(1, 10, 100), Observable.just(1, 2, 3, 4, 5), new BiFunction<Integer, Integer, Integer>() {
            @Override
            public Integer apply(@NonNull Integer integer, @NonNull Integer integer2) throws Exception {
                return integer + integer2;
            }
        }).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(@NonNull Integer integer) throws Exception {
                System.out.println(integer);
            }
        });

    }


    public void testAll() {
        Observable.fromArray(1, 2, 3, 4).all(new Predicate<Integer>() {
            @Override
            public boolean test(@NonNull Integer integer) throws Exception {
                return integer > 3;
            }
        }).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(@NonNull Boolean aBoolean) throws Exception {
                System.out.println(aBoolean);
            }
        });
        Observable.fromArray(5, 6, 4).all(new Predicate<Integer>() {
            @Override
            public boolean test(@NonNull Integer integer) throws Exception {
                return integer > 3;
            }
        }).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(@NonNull Boolean aBoolean) throws Exception {
                System.out.println(aBoolean);
            }
        });
    }

    public void testContains() {
        Observable.just(1, 3, 4).contains(4).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(@NonNull Boolean aBoolean) throws Exception {
                System.out.println(aBoolean);
            }
        });
    }

    public void testDefaultEmpty() {
        Observable.empty().defaultIfEmpty(11).subscribe(new Consumer<Object>() {
            @Override
            public void accept(@NonNull Object integer) throws Exception {
                System.out.println(integer.toString());
            }
        });
    }

    public void testSkipWhile() {
        Observable.interval(1, TimeUnit.SECONDS).skip(3).subscribe(new Consumer<Long>() {
            @Override
            public void accept(@NonNull Long aLong) throws Exception {
                System.out.println(aLong);
            }
        });
        Observable.interval(1, TimeUnit.SECONDS).skipWhile(new Predicate<Long>() {
            @Override
            public boolean test(@NonNull Long aLong) throws Exception {
                return aLong < 2;
            }
        }).subscribe(new Consumer<Long>() {
            @Override
            public void accept(@NonNull Long aLong) throws Exception {
                System.out.println(aLong);
            }
        });
    }

    public void testTakeUnitl() {
        Observable.interval(1, TimeUnit.SECONDS).takeUntil(Observable.timer(4, TimeUnit.SECONDS)).subscribe(new Consumer<Long>() {
            @Override
            public void accept(@NonNull Long aLong) throws Exception {
                System.out.println(aLong);
            }
        });
    }

}
