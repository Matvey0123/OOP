package ru.nsu.fit.kuznetsov.primes;

import java.util.List;

public class Sequential {

  static boolean isNonPrime(List<Integer> arr) {
    for (int value : arr) {
      int sqrt = (int) Math.sqrt(value);
      for (int j = 2; j <= sqrt; j++) {
        if (value % j == 0) {
          return true;
        }
      }
    }
    return false;
  }
}
