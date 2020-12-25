package ru.nsu.fit.kuznetsov.orderedset;

import java.util.ArrayList;
import java.util.List;

/**
 * This class describes an element of ordered set.
 *
 * @param <T> the type of element
 */
public class Elem<T> {
  T name;
  List<Integer> relations;
  Color color;
  int time;
  int parent;

  /**
   * The constructor for the element
   *
   * @param name the name of element
   */
  public Elem(T name) {
    this.name = name;
    this.relations = new ArrayList<>();
    this.color = Color.WHITE;
    this.time = 0;
    this.parent = -1;
  }

  /**
   * Adds a relation between the element and some other element
   *
   * @param id the id of other element
   */
  public void addRelation(int id) {
    this.relations.add(id);
  }
}

enum Color {
  BLACK,
  WHITE,
  GRAY
}
