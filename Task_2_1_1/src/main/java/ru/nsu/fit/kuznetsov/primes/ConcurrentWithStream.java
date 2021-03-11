package ru.nsu.fit.kuznetsov.primes;

import java.util.List;

class ConcurrentWithStream {
  static boolean search(List<Integer> numbers) {
    return numbers.stream().parallel().anyMatch(Sequential::checkForNonPrime);
  }
}
