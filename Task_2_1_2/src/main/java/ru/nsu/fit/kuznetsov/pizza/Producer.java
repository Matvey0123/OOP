package ru.nsu.fit.kuznetsov.pizza;

import java.util.concurrent.BlockingQueue;

public class Producer implements Runnable {
    private final BlockingQueue<Integer> store;
    private final BlockingQueue<Integer> orders;
    private int bakerNum;
    final int timePerOnePizza;

    public Producer(BlockingQueue<Integer> store, BlockingQueue<Integer> orders, int bakerNum, int timePerOnePizza) {
        this.store = store;
        this.orders = orders;
        this.bakerNum = bakerNum;
        this.timePerOnePizza = timePerOnePizza;
    }

    @Override
    public void run() {
        while (true) {
            int order;
            try {
                order = orders.take();
                Thread.sleep(timePerOnePizza);
                System.out.println("Baker " + bakerNum + " prepared pizza " + order);
                store.put(order);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
