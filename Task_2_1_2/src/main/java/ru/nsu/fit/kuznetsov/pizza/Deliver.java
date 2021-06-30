package ru.nsu.fit.kuznetsov.pizza;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class Deliver implements Runnable {
    private final BlockingQueue<Integer> store;
    private final int timeToDeliver;
    private final int deliverNum;
    AtomicInteger numOfProducers;


    public Deliver(BlockingQueue<Integer> store, int timeToDeliver, int deliverNum, AtomicInteger numOfProducers) {
        this.store = store;
        this.timeToDeliver = timeToDeliver;
        this.deliverNum = deliverNum;
        this.numOfProducers = numOfProducers;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Integer preparedPizza;
                if (numOfProducers.get() == 0) {
                    preparedPizza = store.poll();
                    if (preparedPizza == null) {
                        System.out.println("Deliver " + deliverNum + " finished his work!");
                        return;
                    }
                } else {
                    preparedPizza = store.take();
                }
                long start = System.currentTimeMillis();
                Thread.sleep(timeToDeliver);
                System.out.println("Deliver " + deliverNum + " delivered pizza " + preparedPizza);
                long finish = System.currentTimeMillis();
                long exTime = finish - start - 100;
                if (exTime > timeToDeliver) {
                    System.out.println("Baker " + deliverNum + " should be kicked!!!!  Time: " + exTime);
                    return;
                }
            } catch (InterruptedException e) {
                System.out.println("Deliver " + deliverNum + " finished his work!");
                return;
            }
        }
    }
}
