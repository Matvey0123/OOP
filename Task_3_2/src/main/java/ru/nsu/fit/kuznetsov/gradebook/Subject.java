package ru.nsu.fit.kuznetsov.gradebook;

/** This class describes Subject for the GradeBook. */
public class Subject {
  String name;
  FinalChallenge challenge;
  Grade mark;

  /**
   * Constructs a new subject. If the final challenge is Exam or Diff_credit then your grade must be
   * only EXC or GOOD or SAT or POOR If the final challenge is Credit then your grade must be PASS
   * or FAIL
   *
   * @param name the name of subject
   * @param challenge the variant of final challenge
   * @param mark the grade for final challenge
   */
  public Subject(String name, FinalChallenge challenge, Grade mark) {
    if (challenge == FinalChallenge.CREDIT && mark != Grade.FAIL && mark != Grade.PASS) {
      throw new IllegalArgumentException();
    }
    if ((challenge == FinalChallenge.DIFF_CREDIT || challenge == FinalChallenge.EXAM)
        && (mark == Grade.PASS || mark == Grade.FAIL)) {
      throw new IllegalArgumentException();
    }
    this.name = name;
    this.challenge = challenge;
    this.mark = mark;
  }
}
