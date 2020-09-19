package ru.nsu.fit.kuznetsov.substr;

import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.*;
import java.io.File;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class SearchSubstrTests {

  @Test
  public void testFromTask1() throws IOException {
    SearchSubstr test = new SearchSubstr();
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
      writer.write("Я хочу пирог!");
      writer.close();
      String s = "пирог";
      ArrayList<Integer> res = new ArrayList<>();
      res = test.search("input.txt", s);
      ArrayList<Integer> correct = new ArrayList<>();
      correct.add(7);
      Assert.assertEquals(correct, res);
      file.delete();
    }
  }

  @Test
  public void testFromTask2() throws IOException {
    SearchSubstr test = new SearchSubstr();
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
      writer.write("Я хочу сок!");
      writer.close();
      String s = "пирог";
      ArrayList<Integer> res = new ArrayList<>();
      res = test.search("input.txt", s);
      ArrayList<Integer> correct = new ArrayList<>();
      Assert.assertEquals(correct, res);
      file.delete();
    }
  }

  @Test
  public void myTest() throws IOException {
    SearchSubstr test = new SearchSubstr();
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
      writer.write("acbabacjkababaceicnepcpewmcpewcmpoewaba");
      writer.close();
      String s = "aba";
      ArrayList<Integer> res = new ArrayList<>();
      res = test.search("input.txt", s);
      ArrayList<Integer> correct = new ArrayList<>();
      correct.add(3);
      correct.add(9);
      correct.add(11);
      correct.add(36);
      Assert.assertEquals(correct, res);
      file.delete();
    }
  }

  @Test
  public void largeTest() throws IOException {
    SearchSubstr test = new SearchSubstr();
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
      ArrayList<Integer> found = new ArrayList<>();
      found = test.search("input.txt", sub);
      Assert.assertEquals(answer, found);
      file.delete();
    }
  }
}
