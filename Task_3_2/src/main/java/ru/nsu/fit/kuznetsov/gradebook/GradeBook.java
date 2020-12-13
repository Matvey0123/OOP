package ru.nsu.fit.kuznetsov.gradebook;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/** This class describes the GradeBook. */
public class GradeBook {
  private final List<Semester> semesters;
  private Grade markForDiplomaProject;
  private final int totalCountSemesters;

  /**
   * Constructs new gradeBook
   *
   * @param totalCountSemesters how many semesters in total
   */
  public GradeBook(int totalCountSemesters) {
    this.semesters = new ArrayList<>(totalCountSemesters);
    this.markForDiplomaProject = Grade.NONE;
    this.totalCountSemesters = totalCountSemesters;
  }

  /**
   * Sets the mark for Diploma project
   *
   * @param markForDiplomaProject the mark to be set
   */
  public void setMarkForDiplomaProject(Grade markForDiplomaProject) {
    if (markForDiplomaProject == Grade.FAIL || markForDiplomaProject == Grade.PASS) {
      throw new IllegalArgumentException("The Grade for a diploma project mustn't be PASS or FAIL");
    }
    this.markForDiplomaProject = markForDiplomaProject;
  }

  /**
   * Adds a semester to gradeBook
   *
   * @param semester the semester to be added
   */
  public void addSemester(Semester semester) {
    semesters.add(semester);
  }

  /**
   * Adds Subject to current Semester in gradeBook
   *
   * @param subject the subject to be added
   * @param numOfSemester the number of Semester where the Subject must be added
   */
  public void addSubject(Subject subject, int numOfSemester) {
    if (semesters.size() < numOfSemester) {
      semesters.add(new Semester());
    }
    Semester semester = semesters.get(numOfSemester - 1);
    semester.subjects.add(subject);
    semesters.set(numOfSemester - 1, semester);
  }

  /**
   * Checks the grade for diploma project
   *
   * @return true if this grade is EXC and false otherwise
   */
  private boolean checkDiplomaProjectExc() {

    return markForDiplomaProject == Grade.EXC;
  }

  /**
   * Checks if there are any SAT marks for the whole time of education
   *
   * @return true if there are no SAT and FAIL marks and false otherwise
   */
  private boolean checkSatMarks() {
    boolean isSat = false;
    for (Semester semester : semesters) {
      isSat |=
          semester.subjects.stream()
              .anyMatch(
                  subject ->
                      subject.mark == Grade.SAT
                          || subject.mark == Grade.POOR
                          || subject.mark == Grade.FAIL);
    }
    return !isSat;
  }

  /**
   * Counts the percent of EXC marks and checks if it is more then 75%
   *
   * @return true if the percent more or equal then 0.75 and false otherwise
   */
  private boolean checkPercentOfExcGrades() {
    Set<String> checkedSubjects = new HashSet<>();
    float totalCount = 0.00f, countExc = 0.00f;
    for (int i = totalCountSemesters - 1; i >= 0; i--) {
      List<Subject> subjects = semesters.get(i).subjects;
      for (Subject subject : subjects) {
        if (checkedSubjects.contains(subject.name)) {
          continue;
        }
        if (subject.challenge == FinalChallenge.DIFF_CREDIT
            || subject.challenge == FinalChallenge.EXAM) {
          totalCount += 1.00f;
          checkedSubjects.add(subject.name);
          if (subject.mark == Grade.EXC) {
            countExc += 1.00f;
          }
        }
      }
    }
    return countExc / totalCount >= 0.75f;
  }

  /**
   * Counts the current average grade
   *
   * @return the current grade for the current time of education
   */
  public float currentAverageGrade() {
    int cnt = 0;
    float sum = 0.0f;
    for (Semester semester : semesters) {
      List<Subject> curr = semester.subjects;
      for (Subject subject : curr) {
        if (subject.challenge == FinalChallenge.DIFF_CREDIT
            || subject.challenge == FinalChallenge.EXAM) {
          cnt++;
          sum += (float) subject.mark.ordinal();
        }
      }
    }
    if (cnt == 0) {
      return sum;
    }
    return sum / cnt;
  }

  /**
   * Checks whether student can get larger stipend. It is possible if only all his marks are EXC and
   * all credits are passed
   *
   * @return true if larger stipend is possible and false otherwise
   */
  public boolean largerStipend() {
    if (semesters.size() - 1 < 0) {
      return false;
    }
    if (semesters.get(semesters.size() - 1).wereRetakes) {
      return false;
    }
    List<Subject> last = semesters.get(semesters.size() - 1).subjects;
    return last.stream()
        .map(subject -> subject.mark)
        .allMatch(mark -> mark == Grade.EXC || mark == Grade.PASS);
  }

  /**
   * Checks whether student can get red diploma. Considered that student, who haven't finished his
   * studying yet CAN get red diploma
   *
   * @return true if student can get red diploma and false otherwise
   */
  public boolean redDiploma() {
    if (semesters.size() == totalCountSemesters) {
      return checkDiplomaProjectExc() && checkSatMarks() && checkPercentOfExcGrades();
    }
    return checkSatMarks();
  }
}

enum FinalChallenge {
  CREDIT,
  DIFF_CREDIT,
  EXAM
}

enum Grade {
  FAIL,
  PASS,
  POOR,
  SAT,
  GOOD,
  EXC,
  NONE
}
