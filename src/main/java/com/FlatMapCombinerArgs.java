package com;

import io.reactivex.Observable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by rois on 19/09/2017.
 */
public class FlatMapCombinerArgs {


    public static void main(String[] args) throws InterruptedException {


        Observable<String> intervalArg = Observable.just("alpha" , "beta" , "Gamma");

        intervalArg.flatMap(s -> Observable.fromArray(s.split("")), (s,r) -> s + "-" + r  ).subscribe(System.out::println);
        System.out.println("-------with Iterable instead of observable --");
        intervalArg.flatMapIterable(s -> Arrays.asList(s.split("")), (s,r) -> s + "-" + r ).subscribe(System.out::println);



    }


}
