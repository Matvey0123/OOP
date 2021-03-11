package ru.nsu.fit.kuznetsov.primes;

import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;

class ConcurrentWithThreads {
  static boolean search(int numOfThreads, List<Integer> numbers) throws Exception {
    ExecutorService executorService = Executors.newFixedThreadPool(numOfThreads);
    List<Callable<Boolean>> tasks =
        numbers.stream().map(IsNotPrimeTask::new).collect(Collectors.toList());
    List<Future<Boolean>> result = executorService.invokeAll(tasks);
    executorService.shutdown();

    return result.stream()
        .anyMatch(
            n -> {
              try {
                return n.get();
              } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
              }
              return false;
            });
  }
}

class IsNotPrimeTask implements Callable<Boolean> {

  final int number;

  IsNotPrimeTask(int num) {
    this.number = num;
  }

  @Override
  public Boolean call() throws Exception {
    return Sequential.checkForNonPrime(this.number);
  }
}
