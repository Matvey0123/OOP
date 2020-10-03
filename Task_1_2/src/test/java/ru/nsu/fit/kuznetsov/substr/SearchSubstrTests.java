package ru.nsu.fit.kuznetsov.substr;

import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.*;
import static ru.nsu.fit.kuznetsov.substr.SearchSubstr.search;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class SearchSubstrTests {

  @Test
  public void testFromTask1() throws IOException {
    ClassLoader classloader = Thread.currentThread().getContextClassLoader();
    InputStream is = classloader.getResourceAsStream("input1.txt");
    String s = "пирог";
    ArrayList<Integer> res;
    res = search(is,s);
    ArrayList<Integer> correct = new ArrayList<>();
    correct.add(7);
    Assert.assertEquals(correct, res);
  }

  @Test
  public void testFromTask2() throws IOException {
    ClassLoader classloader = Thread.currentThread().getContextClassLoader();
    InputStream is = classloader.getResourceAsStream("input2.txt");
    String s = "пирог";
    ArrayList<Integer> res;
    res = search(is, s);
    ArrayList<Integer> correct = new ArrayList<>();
    Assert.assertEquals(correct, res);
  }

  @Test
  public void myTest() throws IOException {
    ClassLoader classloader = Thread.currentThread().getContextClassLoader();
    InputStream is = classloader.getResourceAsStream("input3.txt");
    String s = "aba";
    ArrayList<Integer> res;
    res = search(is, s);
    ArrayList<Integer> correct = new ArrayList<>();
    correct.add(3);
    correct.add(9);
    correct.add(11);
    correct.add(36);
    Assert.assertEquals(correct, res);
  }

  @Test
  public void largeTest() throws IOException {
    File file = new File("input.txt");
    if (file.isFile()) {
      try {
        file.delete();
      } catch (SecurityException e) {
        e.printStackTrace();
        fail();
      }
    }
    boolean isCreate = file.createNewFile();
    if (isCreate) {
      Writer writer =
          new BufferedWriter(
              new OutputStreamWriter(new FileOutputStream("input.txt"), StandardCharsets.UTF_8));
      String text = "Я наконец-то починил дурацкую кодировку!";
      int subIndex = 0;
      ArrayList<Integer> answer = new ArrayList<>();
      for (int i = 0; i < 268435456; i++) {
        writer.write(text);
        subIndex += 40;
        if (i % 1000000 == 0) {
          writer.write("Ураааааааа");
          answer.add(subIndex);
          subIndex += 10;
        }
      }
      String sub = "Ураааааааа";
      ArrayList<Integer> found;
      found = search("input.txt", sub);
      Assert.assertEquals(answer, found);
      file.delete();
    }
  }
}
