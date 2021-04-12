package ru.nsu.fit.kuznetsov.pizza;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {

    static void startProcess(Data parameters) {
        BlockingQueue<Integer> store = new LinkedBlockingQueue<>(parameters.storeCapacity);
        BlockingQueue<Integer> orders = new LinkedBlockingQueue<>(parameters.ordersNum);
        AtomicInteger numOfProducers = new AtomicInteger(parameters.bakers.size());
        for (int i = 1; i <= parameters.ordersNum; i++) {
            orders.add(i);
        }
        List<Thread> bakers = new ArrayList<>();
        List<Thread> carriers = new ArrayList<>();
        for (int i = 0; i < parameters.bakers.size(); i++) {
            Data.Baker baker = parameters.bakers.get(i);
            bakers.add(new Thread(new Producer(store, orders, baker.id, baker.speed, numOfProducers)));
        }
        for (int i = 0; i < parameters.delivers.size(); i++) {
            Data.Deliver deliver = parameters.delivers.get(i);
            carriers.add(new Thread(new Deliver(store, deliver.speed, deliver.id, numOfProducers)));
        }
        bakers.forEach(Thread::start);
        carriers.forEach(Thread::start);
        try {
            for (Thread thread : bakers) {
                thread.join();
            }
            for (Thread thread : carriers) {
                thread.join(200);
            }
            for (Thread thread : carriers) {
                if (thread.isAlive()) {
                    thread.interrupt();
                }
            }
        } catch (InterruptedException e) {
            System.out.println("Interrupted exception from Main!");
        }
    }

    public static void main(String[] args) throws FileNotFoundException {

        Gson gson = new Gson();
        JsonReader reader = new JsonReader(new FileReader(args[0]));
        Data parameters = gson.fromJson(reader, Data.class);
        startProcess(parameters);

    }
}
