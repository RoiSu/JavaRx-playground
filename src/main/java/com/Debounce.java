package com;

import io.reactivex.Flowable;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
 * Created by rois on 19/09/2017.
 */
public class Debounce {

    public static void main(String[] args) throws InterruptedException {


        ArrayList<String> arr = new ArrayList<String>();
        for (int i = 0 ; i < 100 ; i++){
            arr.add(String.valueOf(i));
        }




        Flowable<String> events = Flowable.just("123");
        Flowable<String> delayed = events.debounce(1, TimeUnit.SECONDS);
        Flowable<String> pings = delayed.map(ev -> "PING");
        Flowable<String> eventsWithPings = Flowable.merge(events, pings);


        Thread.sleep(100000);
    }
}
