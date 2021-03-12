package ru.nsu.fit.kuznetsov.primes;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class Main {

  public static void main(String[] args) {

    List<Integer> numbers = new ArrayList<>();
    for (int i = 0; i < 1000; i++) {
      numbers.add(i, 2000000089);
    }
    numbers.add(899, 10);
    Instant before = Instant.now();
    boolean resultSequential = Sequential.isNonPrime(numbers);
    System.out.println(
        Duration.between(before, Instant.now()).toMillis() + " ms" + " - sequentially");
    before = Instant.now();
    try {
      boolean resultThreads = ConcurrentWithThreads.search(16, numbers);
    } catch (Exception e) {
      e.printStackTrace();
    }

    System.out.println(Duration.between(before, Instant.now()).toMillis() + " ms" + " - threads");
    before = Instant.now();
    boolean resultStreams = ConcurrentWithStream.search(numbers);
    System.out.println(Duration.between(before, Instant.now()).toMillis() + " ms" + " - stream");
  }
}
