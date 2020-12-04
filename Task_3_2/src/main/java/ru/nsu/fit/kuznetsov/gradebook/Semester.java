package ru.nsu.fit.kuznetsov.gradebook;

import java.util.ArrayList;
import java.util.List;

/** This class describes Semester for the GradeBook */
public class Semester {
  List<Subject> subjects;
  boolean wereRetakes;

  /** Constructs a new empty semester */
  public Semester() {
    this.subjects = new ArrayList<Subject>();
    this.wereRetakes = false;
  }

  /**
   * Constructs a new semester using already known list of Subjects
   *
   * @param subjects the list of known subjects
   */
  public Semester(List<Subject> subjects) {
    this.subjects = subjects;
    this.wereRetakes = false;
  }

  /**
   * Adds Subject to semester
   *
   * @param subject the subject to be added
   */
  public void addSubject(Subject subject) {
    subjects.add(subject);
  }

  /**
   * Sets a retake to semester.
   *
   * @param retake true if there is a retake and false otherwise
   */
  public void addRetake(boolean retake) {
    this.wereRetakes |= retake;
  }
}
