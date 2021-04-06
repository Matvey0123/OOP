package ru.nsu.fit.kuznetsov.pizza;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {

        Gson gson = new Gson();
        JsonReader reader = new JsonReader(new FileReader(args[0]));
        Data parameters = gson.fromJson(reader, Data.class);

        BlockingQueue<Integer> store = new LinkedBlockingQueue<>(parameters.storeCapacity);
        BlockingQueue<Integer> orders = new LinkedBlockingQueue<>(parameters.ordersNum);

        for (int i = 1; i <= parameters.ordersNum; i++) {
            orders.add(i);
        }

        for (int i = 0; i < parameters.bakers.size(); i++) {
            Data.Baker baker = parameters.bakers.get(i);
            new Thread(new Producer(store, orders, baker.id, baker.speed)).start();
        }
        for (int i = 0; i < parameters.delivers.size(); i++) {
            Data.Deliver deliver = parameters.delivers.get(i);
            new Thread(new Deliver(store, deliver.speed, deliver.id)).start();
        }

    }
}
