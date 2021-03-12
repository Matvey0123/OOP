package ru.nsu.fit.kuznetsov.primes;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class ConcurrentWithThreadsTest {
  @Test
  public void allPrimes() throws Exception {
    List<Integer> numbers = new ArrayList<>();
    for (int i = 0; i < 1000; i++) {
      numbers.add(i, 2000000089);
    }
    assertFalse(ConcurrentWithThreads.search(4, numbers));
  }
}
