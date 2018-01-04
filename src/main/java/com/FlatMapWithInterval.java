package com;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import javafx.collections.ObservableArray;

import java.util.concurrent.TimeUnit;

/**
 * Created by rois on 19/09/2017.
 */
public class FlatMapWithInterval {


    public static void main(String[] args) throws InterruptedException {


        Observable<Integer> intervalArg = Observable.just(2,3,10,7);

        intervalArg.flatMap(i -> Observable.interval(i , TimeUnit.SECONDS)
                .map( i2 -> i + "s interval: " + ((i2+1) * i) + " seconds elapsed"))
                .subscribe(System.out::println);


        Thread.sleep(20000);

    }


}
