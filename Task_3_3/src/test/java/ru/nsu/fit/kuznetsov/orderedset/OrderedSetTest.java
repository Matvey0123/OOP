package ru.nsu.fit.kuznetsov.orderedset;

import org.junit.Test;

import static org.junit.Assert.*;

public class OrderedSetTest {
  @Test
  public void testFromPDF() {
    String[] content = {"Mary", "Veronica", "Vasiliy", "Tanya", "Dmitriy"};
    OrderedSet<String> set = new OrderedSet<>(content);
    set.addRelation("Mary", "Vasiliy");
    set.addRelation("Veronica", "Vasiliy");
    set.addRelation("Vasiliy", "Tanya");
    String[] correct = {"Mary", "Veronica", "Dmitriy"};
    String[] answer = set.findMaxElements().toArray(new String[0]);
    assertArrayEquals(correct, answer);
  }

  @Test(expected = IllegalStateException.class)
  public void testTransitivity() {
    String[] content = {"Mary", "Veronica", "Vasiliy", "Tanya", "Dmitriy"};
    OrderedSet<String> set = new OrderedSet<>(content);
    set.addRelation("Mary", "Vasiliy");
    set.addRelation("Veronica", "Vasiliy");
    set.addRelation("Vasiliy", "Tanya");
    set.addRelation("Tanya", "Mary");
  }

  @Test
  public void testTopsort() {
    Integer[] content = {1, 2, 3, 4, 5, 6, 7};
    OrderedSet<Integer> set = new OrderedSet<>(content);
    set.addRelation(1, 2);
    set.addRelation(2, 3);
    set.addRelation(3, 7);
    set.addRelation(4, 5);
    set.addRelation(5, 3);
    set.addRelation(6, 7);
    Integer[] correct = new Integer[] {6, 4, 5, 1, 2, 3, 7};
    assertArrayEquals(correct, set.topsort());
  }

  @Test
  public void testMaxElements() {
    Integer[] content = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
    OrderedSet<Integer> set = new OrderedSet<>(content);
    set.addRelation(1, 2);
    set.addRelation(2, 3);
    set.addRelation(3, 7);
    set.addRelation(4, 5);
    set.addRelation(5, 3);
    set.addRelation(6, 7);
    Integer[] correct = new Integer[] {1, 4, 6, 8, 9, 10};
    Integer[] answer = set.findMaxElements().toArray(new Integer[0]);
    assertArrayEquals(correct, answer);
  }
}
