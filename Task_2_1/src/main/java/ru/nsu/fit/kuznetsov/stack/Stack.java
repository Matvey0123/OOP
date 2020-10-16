/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package ru.nsu.fit.kuznetsov.stack;

import java.util.Arrays;
import java.util.EmptyStackException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * This class allows us to use stack.
 *
 * @param <T> the type of stack elements
 */
public class Stack<T> implements Iterable<T> {
  T[] arr;
  int counter;
  final int DEFAULT_SIZE = 10;

  /** This class provides the Iterator for our stack. */
  private class StackIterator implements Iterator<T> {
    Stack<T> stack;
    int curr;

    private StackIterator() {
      stack = Stack.this;
      curr = stack.counter - 1;
    }

    /**
     * Checks whether the stack has next element.
     *
     * @return True if the stack has more then one element
     */
    @Override
    public boolean hasNext() {
      return curr >= 0;
    }

    /**
     * Finds the next element in the stack.
     *
     * @return The next element of type T
     */
    @Override
    public T next() {
      if (hasNext()) {
        return stack.arr[curr--];
      }
      throw new NoSuchElementException();
    }
  }

  @SuppressWarnings("unchecked")
  public Stack() {
    arr = (T[]) new Object[DEFAULT_SIZE];
    counter = 0;
  }

  /**
   * Adds the new element in the stack.
   *
   * @param elem the element to be added
   */
  public void push(T elem) {
    if (counter == arr.length) {
      arr = Arrays.copyOf(arr, arr.length * 2);
    }
    arr[counter++] = elem;
  }

  /**
   * Removes element from the stack.
   *
   * @return The element in the top of the stack
   */
  public T pop() {
    if (counter == 0) {
      throw new EmptyStackException();
    }
    return arr[--counter];
  }

  /**
   * Count the number of elements in the stack.
   *
   * @return The number of elements
   */
  public int count() {
    return counter;
  }

  /**
   * Creates Iterator for the stack.
   *
   * @return The Iterator for our stack
   */
  @Override
  public Iterator<T> iterator() {
    return new StackIterator();
  }
}
