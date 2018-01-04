package com;

import io.reactivex.Flowable;
import io.reactivex.Scheduler;
import io.reactivex.functions.Function;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * Created by rois on 19/09/2017.
 */
public class Throtling {

    public static void main(String[] args) throws InterruptedException {


    }

    public <T> Flowable<Event> withPings(Flowable<Event> events, Scheduler clock, T ping) {


        events.groupBy(new Function<Event, Event>() {
            @Override
            public Event apply(Event event) throws Exception {
                return null;
            }
        });


       return null;

    }



    private class Event{

        private String accountId;


    }
}
