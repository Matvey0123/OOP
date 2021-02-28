package ru.nsu.fit.kuznetsov.primes;

import java.util.List;
import java.util.stream.Stream;

class ConcurrentWithStream {
  static boolean search(List<Integer> numbers) {
    return numbers.stream().parallel().anyMatch(ConcurrentWithStream::isNotPrime);
  }

  private static boolean isNotPrime(int num) {
    int sqrt = (int) Math.sqrt(num);
    for (int j = 2; j <= sqrt; j++) {
      if (num % j == 0) {
        return true;
      }
    }
    return false;
  }
}
