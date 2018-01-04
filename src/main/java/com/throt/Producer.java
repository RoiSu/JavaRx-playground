package com.throt;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;
import org.reactivestreams.Subscriber;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Producer  {

    public static void main(String[] args) {





        Observable<Event> events = Observable.just(new Event(1,"1"), new Event(2,"2"),new Event(3,"3"),new Event(1,"1-1"));

        events.groupBy(x->x.groupId).map(y -> y.subscribe(z -> System.out.println(y.getKey() + " " + z.groupId + " " + z.data))).subscribe();

        /*
        Scheduler scheduler = Schedulers.from(Executors.newFixedThreadPool(2));
        events.flatMap(x ->
                Observable.fromCallable(() -> process(x))
                        .subscribeOn(scheduler))
                .subscribe(subscriber);


        Executor ex = Executors.newFixedThreadPool(5);

*/


    }


    static class Event {
        private Integer groupId;
        private String data;

        public Event(Integer groupId, String data) {
            this.groupId = groupId;
            this.data = data;
        }

        public Integer getGroupId() {
            return groupId;
        }

        public void setGroupId(Integer groupId) {
            this.groupId = groupId;
        }

        public String getData() {
            return data;
        }

        public void setData(String data) {
            this.data = data;
        }
    }



    private static <T> String process(String x) {
        return x+x;
    }


    private class emitter  extends Flowable<Integer>{


        @Override
        protected void subscribeActual(Subscriber<? super Integer> s) {

        }


    }
}
