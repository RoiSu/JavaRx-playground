package com;

import io.reactivex.Observable;
import io.reactivex.observables.GroupedObservable;

public class GroupBy {

    public static void main(String[] args) {

        Observable<String> source = Observable.just("Alpha" , "Beta" , "Gamma" , "Delta" , "Eplsilon");

        Observable<GroupedObservable<Integer, String>> byLength = source.groupBy(s -> s.length());

        byLength.flatMapSingle(grp-> grp.toList()).subscribe(System.out::println);

    }
}
