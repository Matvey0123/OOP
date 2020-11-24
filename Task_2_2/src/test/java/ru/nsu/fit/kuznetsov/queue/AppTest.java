/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package ru.nsu.fit.kuznetsov.queue;

import org.junit.Test;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

public class AppTest {
  @Test
  public void testFromPDF() {
    MyPriorityQueue<Integer, String> queue = new MyPriorityQueue<>();
    queue.insert(200, "dog");
    queue.insert(10, "people");
    String extract1 = queue.extractMax();
    assertEquals("dog", extract1);
    queue.insert(5, "penguin");
    queue.insert(500, "parrot");
    queue.extractMax();
    assertEquals(Optional.of(10), queue.stream().map(Map.Entry::getKey).max(Integer::compareTo));
    assertEquals(Optional.of(5), queue.stream().map(Map.Entry::getKey).min(Integer::compareTo));
    String[] res = queue.stream().map(Map.Entry::getValue).toArray(String[]::new);
    String[] correct = {"people", "penguin"};
    assertArrayEquals(correct, res);
  }

  @Test
  public void streamTest() {
    MyPriorityQueue<Integer, String> queue = new MyPriorityQueue<>();
    Random random = new Random();
    for (int i = 0; i < 20; i++) {
      queue.insert(random.nextInt(), "" + random.nextInt());
    }
    Integer[] arr = queue.stream().map(Map.Entry::getKey).sorted().toArray(Integer[]::new);
    Integer[] correct = Arrays.copyOf(arr, arr.length);
    Arrays.sort(correct);
    assertArrayEquals(correct, arr);
    assertEquals(20, arr.length);
  }

  @Test
  public void streamTest2() {
    MyPriorityQueue<Integer, Integer> queue = new MyPriorityQueue<>();
    Random random = new Random();
    for (int i = 0; i < 100; i++) {
      queue.insert(random.nextInt(), random.nextInt());
    }
    Integer[] arr =
        queue.stream().map(Map.Entry::getValue).filter(i -> i % 2 == 0).toArray(Integer[]::new);
    Integer[] copy = queue.stream().map(Map.Entry::getValue).toArray(Integer[]::new);
    Integer[] correct = new Integer[arr.length];
    int counter = 0;
    for (Integer integer : copy) {
      if (integer % 2 == 0) {
        correct[counter] = integer;
        counter++;
      }
    }
    assertArrayEquals(correct, arr);
  }

  @Test
  public void streamTest3() {
    MyPriorityQueue<Integer, String> queue = new MyPriorityQueue<>();
    List<String> correct = new LinkedList<>();
    Random random = new Random();
    for (int i = 0; i < 10; i++) {
      queue.insert(random.nextInt(), "" + random.nextInt());
    }
    List<String> list = queue.stream().map(Map.Entry::getValue).collect(Collectors.toList());
    for (int i = 0; i < 10; i++) {
      correct.add(queue.extractMax());
    }
    for (int i = 0; i < 10; i++) {
      assertEquals(correct.get(i), list.get(i));
    }
  }

  @Test
  public void streamTest4() {
    MyPriorityQueue<Integer, Integer> queue = new MyPriorityQueue<>();
    for (int i = 0; i < 20; i++) {
      queue.insert(19 - i, i % 10);
    }
    Integer[] result = queue.stream().map(Map.Entry::getValue).distinct().toArray(Integer[]::new);
    Integer[] correct = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
    assertArrayEquals(correct, result);
  }

  @Test
  public void streamTest5() {
    MyPriorityQueue<Integer, Integer> queue = new MyPriorityQueue<>();
    for (int i = 0; i < 20; i++) {
      queue.insert(19 - i, i);
    }
    Object[] result = queue.stream().map(Map.Entry::getValue).skip(5).limit(5).toArray();
    Object[] correct = {5, 6, 7, 8, 9};
    assertArrayEquals(correct, result);
  }

  @Test
  public void streamTest6() {
    MyPriorityQueue<Integer, String> queue = new MyPriorityQueue<>();
    for (int i = 1; i <= 20; i++) {
      queue.insert(i, "" + i);
    }
    Optional<Integer> max = queue.stream().map(Map.Entry::getKey).max(Integer::compareTo);
    assertEquals(Optional.of(20), max);
    Optional<Integer> findFirst = queue.stream().map(Map.Entry::getKey).findFirst();
    assertEquals(max, findFirst);
    Optional<Integer> min = queue.stream().map(Map.Entry::getKey).min(Integer::compareTo);
    assertEquals(Optional.of(1), min);
    Optional<Integer> reduce = queue.stream().map(Map.Entry::getKey).reduce(Integer::min);
    assertEquals(min, reduce);
    assertTrue(queue.stream().map(Map.Entry::getKey).anyMatch(key -> key * 4 == 20));
  }
}
