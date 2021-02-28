package ru.nsu.fit.kuznetsov.primes;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

class ConcurrentWithThreads {
  static boolean search(int numOfThreads, List<Integer> numbers) throws Exception {
    ExecutorService executorService = Executors.newFixedThreadPool(numOfThreads);
    List<Callable<Boolean>> tasks = new ArrayList<>();

    for (Integer number : numbers) {
      tasks.add(new IsNotPrimeTask(number));
    }
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
    int sqrt = (int) Math.sqrt(this.number);
    for (int j = 2; j <= sqrt; j++) {
      if (this.number % j == 0) {
        return true;
      }
    }
    return false;
  }
}
