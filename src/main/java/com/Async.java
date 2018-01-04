package com;

import io.reactivex.*;
import io.reactivex.processors.PublishProcessor;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.schedulers.TestScheduler;
import io.reactivex.subscribers.TestSubscriber;
import org.junit.Test;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * Created by rois on 19/09/2017.
 */
public class Async {


    public static void main(String[] args) throws InterruptedException {
        simpleAsync();
    }

    public static void simpleAsync() throws InterruptedException {
        System.out.println("Starting simple async");


        FlowableOnSubscribe<?> source = (FlowableOnSubscribe<Object>) e -> {
            System.out.println("starting");
            Thread.sleep(1_000);
            e.onComplete();
            System.out.println("finished");

        };
        Flowable.create(source, BackpressureStrategy.BUFFER).subscribeOn(Schedulers.newThread()).subscribe();

        System.out.println("Finished simple async");
        Thread.sleep(2_000);
    }
}
