package ru.nsu.fit.kuznetsov.pizza;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

class Data {
    @SerializedName("capacity")
    int storeCapacity;
    @SerializedName("orders_num")
    int ordersNum;
    List<Baker> bakers = new ArrayList<>();
    List<Deliver> delivers = new ArrayList<>();

    static class Baker {
        int id;
        int speed;
    }

    static class Deliver {
        int id;
        int speed;
    }
}
