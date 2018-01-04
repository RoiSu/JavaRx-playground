package com;

import io.reactivex.Flowable;
import io.reactivex.Scheduler;
import io.reactivex.processors.PublishProcessor;
import io.reactivex.schedulers.TestScheduler;
import io.reactivex.subscribers.TestSubscriber;
import org.junit.Test;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * Created by rois on 19/09/2017.
 */
public class PingDebouncer {


    /**
     *  from blog  : <a href="https://www.javacodegeeks.com/2017/09/detecting-testing-stalled-streams-rxjava-faq.html"> here </a>
     * @param events
     * @param clock
     * @param ping
     * @param <T>
     * @return
     */
    public <T> Flowable<T> withPings(Flowable<T> events, Scheduler clock, T ping) {

        //debouncer start sending events if there is no event for 1 sec . and will send only taht event
        //link :  http://reactivex.io/documentation/operators/debounce.html
        Flowable<T> debouncer = events.debounce(1, SECONDS, clock);

        //listen nd to the debouncer  , once the debouncer seevent(flatMap), the pinger interval start sending event every 1 sec until there is any event (takeUntil).
        Flowable<T> pinger = debouncer.flatMap(x1 -> Flowable
                        .interval(0, 1, SECONDS, clock)
                        .map(e -> ping)
                        .takeUntil(events)
                );

        return events.mergeWith(pinger);

    }



    @Test
    public void shouldAddPings() throws Exception {
        PublishProcessor<String> events = PublishProcessor.create();
        final TestScheduler clock = new TestScheduler();
        final Flowable<String> eventsWithPings = withPings(events, clock, "PING");

        final TestSubscriber<String> test = eventsWithPings.test();
        events.onNext("A");
        test.assertValues("A");

        clock.advanceTimeBy(999, MILLISECONDS);
        events.onNext("B");
        test.assertValues("A", "B");
        clock.advanceTimeBy(999, MILLISECONDS);
        test.assertValues("A", "B");

        clock.advanceTimeBy(1, MILLISECONDS);
        test.assertValues("A", "B", "PING");
        clock.advanceTimeBy(999, MILLISECONDS);
        test.assertValues("A", "B", "PING");

        events.onNext("C");
        test.assertValues("A", "B", "PING", "C");

        clock.advanceTimeBy(1_000, MILLISECONDS);
        test.assertValues("A", "B", "PING", "C", "PING");
        clock.advanceTimeBy(999, MILLISECONDS);
        test.assertValues("A", "B", "PING", "C", "PING");

        clock.advanceTimeBy(1, MILLISECONDS);
        test.assertValues("A", "B", "PING", "C", "PING", "PING");
        clock.advanceTimeBy(999, MILLISECONDS);
        test.assertValues("A", "B", "PING", "C", "PING", "PING");

        events.onNext("D");
        test.assertValues("A", "B", "PING", "C", "PING", "PING", "D");

        clock.advanceTimeBy(999, MILLISECONDS);
        events.onNext("E");
        test.assertValues("A", "B", "PING", "C", "PING", "PING", "D", "E");
        clock.advanceTimeBy(999, MILLISECONDS);
        test.assertValues("A", "B", "PING", "C", "PING", "PING", "D", "E");

        clock.advanceTimeBy(1, MILLISECONDS);
        test.assertValues("A", "B", "PING", "C", "PING", "PING", "D", "E", "PING");

        clock.advanceTimeBy(3_000, MILLISECONDS);
        test.assertValues("A", "B", "PING", "C", "PING", "PING", "D", "E", "PING", "PING", "PING", "PING");
    }
}
