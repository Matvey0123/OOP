package ru.nsu.fit.kuznetsov.orderedset;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * This class describes an Ordered set.
 *
 * @param <T> the type of elements in ordered set
 */
public class OrderedSet<T> {
  Map<T, Integer> verticesID = new HashMap<>();
  List<Elem<T>> elems = new ArrayList<>();
  int time;

  /**
   * The constructor for ordered set.
   *
   * @param content the array of elements to include in the set
   */
  public OrderedSet(T[] content) {
    for (T elem : content) {
      Elem<T> curr = new Elem<T>(elem);
      this.elems.add(curr);
      verticesID.put(elem, this.elems.indexOf(curr));
    }
  }

  /**
   * Defines the order relation on the set and checks the transitivity of its elements Supposed that
   * first element is more then second
   *
   * @param first the name of first element
   * @param second the name of second element
   */
  public void addRelation(T first, T second) {
    int firstID = verticesID.get(first);
    int secondID = verticesID.get(second);
    Elem<T> elemFirst = elems.get(firstID);
    elemFirst.addRelation(secondID);
    checkTransitivity();
  }

  /**
   * Searches for maximal elements of the set i.e elements, that have no parents
   *
   * @return the list of these elements
   */
  public List<T> findMaxElements() {
    return elems.stream()
        .filter(elem -> elem.parent == -1)
        .map(elem -> elem.name)
        .collect(Collectors.toList());
  }

  /**
   * Sorts elements of the set in topological order.
   *
   * @return the array of sorted elements
   */
  public T[] topsort() {
    @SuppressWarnings("unchecked")
    T[] sorted = (T[]) new Object[elems.size()];
    for (Elem<T> elem : elems) {
      sorted[elems.size() - elem.time - 1] = elem.name;
    }
    return sorted;
  }

  /** Checks the elements for transitivity i.e if graph has a cycle then transitivity is not hold */
  private void checkTransitivity() {
    if (prepareDFS()) {
      throw new IllegalStateException("There is a loop in the graph!");
    }
  }

  /**
   * Prepares parameters of the elements and starts a DFS procedure
   *
   * @return true if graph has a cycle and false otherwise
   */
  private boolean prepareDFS() {
    for (Elem<T> elem : elems) {
      elem.color = Color.WHITE;
      elem.parent = -1;
    }
    this.time = 0;
    for (Elem<T> elem : elems) {
      if (elem.color == Color.WHITE) {
        if (dfs(elems.indexOf(elem), -1)) {
          return true;
        }
      }
    }
    return false;
  }

  /**
   * Executes a DFS iteration.
   *
   * @param vertex the current vertex
   * @param parent the parent vertex
   * @return true if graph has a cycle and false otherwise
   */
  private boolean dfs(int vertex, int parent) {
    Elem<T> currElem = elems.get(vertex);
    currElem.color = Color.GRAY;
    currElem.parent = parent;
    for (int childId : currElem.relations) {
      Elem<T> childElem = elems.get(childId);
      if (childElem.color == Color.BLACK) {
        childElem.parent = vertex;
      }
      if ((childElem.color == Color.GRAY)
          || (childElem.color == Color.WHITE && dfs(childId, vertex))) {
        return true;
      }
    }
    currElem.color = Color.BLACK;
    currElem.time = time;
    time++;
    return false;
  }
}
