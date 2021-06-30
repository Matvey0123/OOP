package ru.nsu.fit.kuznetsov.pizza;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import org.junit.Test;


import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

public class MainTest {


    @Test
    public void test1() {
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream is = classloader.getResourceAsStream("test.json");

        start(is);
    }

    @Test
    public void testLowCapacity() {
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream is = classloader.getResourceAsStream("testLowCapacity.json");

        start(is);
    }

    @Test
    public void largeTest() {
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream is = classloader.getResourceAsStream("largeTest.json");

        start(is);
    }

    @Test
    public void testOneCarrier() {
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream is = classloader.getResourceAsStream("testOneCarrier.json");

        start(is);
    }

    @Test
    public void testOneBaker() {
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream is = classloader.getResourceAsStream("testOneBaker.json");

        start(is);
    }

    private void start(InputStream is) {
        assert is != null;
        Reader reader = new InputStreamReader(is);

        Gson gson = new Gson();
        JsonReader jsonReader = new JsonReader(reader);
        Data parameters = gson.fromJson(jsonReader, Data.class);
        Main.startProcess(parameters);
    }
}
