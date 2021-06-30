package ru.nsu.fit.kuznetsov.pizza;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class Producer implements Runnable {
    private final BlockingQueue<Integer> store;
    private final BlockingQueue<Integer> orders;
    private int bakerNum;
    final int timePerOnePizza;
    AtomicInteger numOfProducers;


    public Producer(BlockingQueue<Integer> store,
                    BlockingQueue<Integer> orders,
                    int bakerNum,
                    int timePerOnePizza,
                    AtomicInteger numOfProducers) {
        this.store = store;
        this.orders = orders;
        this.bakerNum = bakerNum;
        this.timePerOnePizza = timePerOnePizza;
        this.numOfProducers = numOfProducers;
    }

    @Override
    public void run() {
        while (true) {
            Integer order;
            try {
                order = orders.poll();
                if (order == null) {
                    System.out.println("Baker " + bakerNum + " finished his work!");
                    numOfProducers.decrementAndGet();
                    return;
                }
                long start = System.currentTimeMillis();
                Thread.sleep(timePerOnePizza);
                store.put(order);
                System.out.println("Baker " + bakerNum + " prepared pizza " + order);
                long finish = System.currentTimeMillis();
                long exTime = finish - start - 100;
                if (exTime > timePerOnePizza) {
                    System.out.println("Baker " + bakerNum + " should be kicked!!!!  Time: " + exTime);
                    return;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

