package com.throt;

import java.util.Observable;

public class Consumer {



    public static Consumer INSTANCE = new Consumer();

    public static Observable obser;

    private Consumer(){
        obser = new Observable();
    }







}
