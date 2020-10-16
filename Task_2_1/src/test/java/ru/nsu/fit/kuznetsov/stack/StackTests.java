package ru.nsu.fit.kuznetsov.stack;

import org.junit.Test;

import java.util.EmptyStackException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.GregorianCalendar;
import java.util.Random;

import static org.junit.Assert.*;

public class StackTests {
  @Test
  public void testFromTask() {
    Stack<Integer> stack = new Stack<>();
    stack.push(2);
    stack.push(7);
    if (stack.pop() != 7) {
      fail("Wrong elem returned by Pop!");
    }
    if (stack.count() != 1) {
      fail("Wrong answer given by Count!");
    }
    Iterator<Integer> iterator = stack.iterator();
    int ans = iterator.next();
    if (ans != 2) {
      fail("Wrong Iterator's work!");
    }
  }

  @Test
  public void testStackSize() {
    Stack<Integer> stack = new Stack<>();
    final int numberOfElms = 667;
    int[] correct = new int[numberOfElms];
    for (int i = 0; i <= numberOfElms - 1; i++) {
      stack.push(i);
      correct[i] = numberOfElms - i - 1;
    }
    int[] result = new int[numberOfElms];
    int counter = 0;
    for (Integer i : stack) {
      result[counter] = i;
      counter++;
    }
    assertArrayEquals(correct, result);
  }

  @Test(expected = EmptyStackException.class)
  public void testPopException() {
    Stack<Integer> stack = new Stack<>();
    stack.pop();
  }

  @Test(expected = NoSuchElementException.class)
  public void testIteratorException() {
    Stack<Integer> stack = new Stack<>();
    Iterator<Integer> iterator = stack.iterator();
    iterator.next();
  }

  @Test
  public void testDifferentType() {
    Stack<GregorianCalendar> stack = new Stack<>();
    Random rn = new Random(0);
    int year, month, dayOfMonth, hourOfDay, minute, second;
    int numOfElms = 15;
    GregorianCalendar[] correct = new GregorianCalendar[numOfElms];
    for (int i = 0; i < numOfElms; i++) {
      year = rn.nextInt(2020) + 1;
      month = rn.nextInt(12);
      dayOfMonth = rn.nextInt(31) + 1;
      hourOfDay = rn.nextInt(24);
      minute = rn.nextInt(60);
      second = rn.nextInt(60);
      GregorianCalendar gregorianCalendar =
          new GregorianCalendar(year, month, dayOfMonth, hourOfDay, minute, second);
      stack.push(gregorianCalendar);
      correct[numOfElms - i - 1] = gregorianCalendar;
    }
    GregorianCalendar[] result = new GregorianCalendar[numOfElms];
    int counter = 0;
    for (GregorianCalendar i : stack) {
      result[counter] = i;
      counter++;
    }
    assertArrayEquals(correct, result);
  }
}
