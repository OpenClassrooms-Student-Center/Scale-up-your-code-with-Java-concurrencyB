package com.openclassrooms.concurrency.reactivejava.app.flow;

import com.openclassrooms.concurrency.reactivejava.app.flow.reactor.TemperaturePublisher;
import com.openclassrooms.concurrency.reactivejava.app.flow.reactor.TemperatureSubscriber;

import java.util.concurrent.CountDownLatch;
import java.util.stream.IntStream;

public class FlowPlanetaryAveragingApp {

    public static void main(String[] args) throws InterruptedException {

        CountDownLatch doneLatch = new CountDownLatch(200);

        TemperatureSubscriber subscriber = new TemperatureSubscriber(doneLatch);
        TemperaturePublisher publisher = new TemperaturePublisher();

        // subscribe to the publisher
        publisher.subscribe(subscriber);

        IntStream.range(0, 220).forEach( (n)-> {
            publisher.submit(Double.valueOf(n));
        });

        doneLatch.await();
        publisher.close();
    }
}
