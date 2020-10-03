/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package ru.nsu.fit.kuznetsov.heapsort;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.Random;
import java.util.*;

public class HeapSortTests {
  @Test
  public void testEmpty() {
    int[] arr = {};
    HeapSort.sort(arr);
    assertEquals(0, arr.length);
  }

  @Test
  public void testFromTask() {
    int[] arr = {5, 4, 3, 2, 1};
    int[] correct = {1, 2, 3, 4, 5};
    HeapSort.sort(arr);
    assertArrayEquals(arr, correct);
  }

  @Test
  public void mainTest() {
    Random rn = new Random(0);
    for (int i = 0; i < 1; i++) {
      int len = rn.nextInt(10000) + 1;
      int[] mySort = new int[len];
      int[] libSort = new int[len];
      int a;
      for (int j = 0; j < len; j++) {
        a = rn.nextInt();
        mySort[j] = a;
        libSort[j] = a;
      }
      HeapSort.sort(mySort);
      Arrays.sort(libSort);
      assertArrayEquals(mySort, libSort);
    }
  }

  @Test
  public void negNumsTest() {
    int[] arr = {-100, -1523, 0, -1, 23, -10};
    int[] correct = {-1523, -100, -10, -1, 0, 23};
    HeapSort.sort(arr);
    assertArrayEquals(arr, correct);
  }
}
