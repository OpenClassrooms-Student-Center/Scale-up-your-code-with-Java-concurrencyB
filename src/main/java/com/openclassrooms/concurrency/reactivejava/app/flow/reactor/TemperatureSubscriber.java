package com.openclassrooms.concurrency.reactivejava.app.flow.reactor;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Flow;
import java.util.concurrent.Flow.Subscription;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Logger;

public class TemperatureSubscriber implements Flow.Subscriber<Double> {

    private final static Logger LOGGER = Logger.getLogger(TemperatureSubscriber.class.getName());
    private final CountDownLatch doneLatch;
    private AtomicReference<Double> temperaturesTotal = new AtomicReference<>(0.0);
    private static final long REQUEST_VOLUME = 1;
    private Subscription subscription;

    public TemperatureSubscriber(CountDownLatch doneLatch) {
        this.doneLatch = doneLatch;
    }

    @Override
    public void onSubscribe(Subscription subscription) {
        LOGGER.info("onSubscription: Subscribing");
        this.subscription = subscription;
        subscription.request(REQUEST_VOLUME);
    }

    @Override
    public void onNext(Double temperature) {
        try {
            LOGGER.info("onNext: Consuming an item from the publisher - " + temperature);
            temperaturesTotal.getAndSet(temperature);

            // Get the next item
            subscription.request(REQUEST_VOLUME);
        } finally {
            // count down
            doneLatch.countDown();
        }

    }

    @Override
    public void onError(Throwable throwable) {
        LOGGER.info("onError: Handling an error" + throwable);
        throwable.printStackTrace();
    }

    @Override
    public void onComplete() {
        LOGGER.info("Completed processing our subscription");
    }
}
