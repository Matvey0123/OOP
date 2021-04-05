package ru.nsu.fit.kuznetsov.pizza;

import java.util.concurrent.BlockingQueue;

public class Deliver implements Runnable{
    private final BlockingQueue<Integer> store;
    private final int timeToDeliver;
    private final int deliverNum;

    public Deliver(BlockingQueue<Integer> store, int timeToDeliver, int deliverNum) {
        this.store = store;
        this.timeToDeliver = timeToDeliver;
        this.deliverNum = deliverNum;
    }

    @Override
    public void run() {
        while(true){
            try{
                int preparedPizza = store.take();
                Thread.sleep(timeToDeliver);
                System.out.println("Deliver " + deliverNum + " delivered pizza " + preparedPizza);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
