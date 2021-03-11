package ru.nsu.fit.kuznetsov.primes;

import java.util.List;
import java.util.stream.IntStream;

public class Sequential {

  static boolean isNonPrime(List<Integer> arr) {
    return arr.stream().anyMatch(Sequential::checkForNonPrime);
  }

  public static boolean checkForNonPrime(int num) {
    return IntStream.rangeClosed(2, (int) Math.sqrt(num))
        .filter(divider -> (num % divider == 0))
        .findAny()
        .isPresent();
  }
}
