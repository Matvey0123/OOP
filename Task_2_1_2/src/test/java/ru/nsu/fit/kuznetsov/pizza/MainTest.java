package ru.nsu.fit.kuznetsov.pizza;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import org.junit.Test;


import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;


import static org.junit.Assert.*;

public class MainTest {


    @Test
    public void test1(){
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream is = classloader.getResourceAsStream("test.json");
        assert is != null;
        Reader reader = new InputStreamReader(is);

        Gson gson = new Gson();
        JsonReader jsonReader = new JsonReader(reader);
        Data parameters = gson.fromJson(jsonReader, Data.class);
        Main.startProcess(parameters);

    }
    @Test
    public void testLowCapacity(){
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream is = classloader.getResourceAsStream("testLowCapacity.json");
        assert is != null;
        Reader reader = new InputStreamReader(is);

        Gson gson = new Gson();
        JsonReader jsonReader = new JsonReader(reader);
        Data parameters = gson.fromJson(jsonReader, Data.class);
        Main.startProcess(parameters);
    }
}