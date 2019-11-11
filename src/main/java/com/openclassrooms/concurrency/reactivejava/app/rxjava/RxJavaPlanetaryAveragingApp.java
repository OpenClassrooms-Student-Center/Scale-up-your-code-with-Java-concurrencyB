package com.openclassrooms.concurrency.reactivejava.app.rxjava;

import com.openclassrooms.concurrency.reactivejava.app.rxjava.reactor.RxJavaTemperatureSubscriber;
import io.reactivex.Flowable;

import java.util.concurrent.CountDownLatch;
import java.util.stream.IntStream;

public class RxJavaPlanetaryAveragingApp {

    public static void main(String[] args) {
        RxJavaTemperatureSubscriber temperatureSubscriber = new RxJavaTemperatureSubscriber();

        // from a stream
        IntStream stream = IntStream.range(0, 100);
        Flowable.fromIterable(stream::iterator).onBackpressureBuffer().
                map((stringValue) -> Double.valueOf(stringValue)).
                subscribe( temperatureSubscriber );

        // from Rx
        Flowable.range(300, 400).onBackpressureBuffer().
                map((stringValue) -> Double.valueOf(stringValue)).
                subscribe( temperatureSubscriber );

    }
}
