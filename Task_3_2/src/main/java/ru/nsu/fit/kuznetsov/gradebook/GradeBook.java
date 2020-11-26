package ru.nsu.fit.kuznetsov.gradebook;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GradeBook {
  private List<Semester> semesters;
  private Grade markForDiplomaProject;
  private int currentSemester;//TODO очень подозрительное!!! это примерно semesters.size
//TODO хранить общее число семестров
  public static class Semester {
    List<Subject> subjects;

    public static class Subject {
      String name;
      FinalChallenge challenge;
      Grade mark;

      public Subject(String name, FinalChallenge challenge, Grade mark) {//TODO функцию проверки марк и челендж
        this.name = name;
        this.challenge = challenge;
        this.mark = mark;
      }
    }

    public Semester() {
      this.subjects = new ArrayList<Subject>();
    }

    public Semester(List<Subject> subjects) {
      this.subjects = subjects;
    }

    public void addSubject(Subject subject) {
      subjects.add(subject);
    }
    //TODO добавить пересдачи
  }

  public GradeBook(int count, Grade markForDiplomaProject) {
    this.semesters = new ArrayList<Semester>(count);
    this.markForDiplomaProject = markForDiplomaProject;
    this.currentSemester = count;
  }

  public void addSemester(Semester semester, int numOfSemester) {//TODO нужен ли здесь номер семестра
    semesters.add(numOfSemester - 1, semester);
  }

  private boolean checkDiplomaProjectExc() {

    return markForDiplomaProject == Grade.EXC;
  }

  private boolean checkSatMarks() {//TODO добавить проверки на незачеты и двойки
    boolean isSat = false;
    for (int i = 0; i < currentSemester - 1; i++) {
      isSat |=
          semesters.get(i).subjects.stream()
              .anyMatch(subject -> subject.mark == Grade.SAT);
    }
    return !isSat;
  }

  private boolean checkPercentOfExcGrades() {
    Set<String> checkedSubjects = new HashSet<>();
    float totalCount = 0.00f, countExc = 0.00f;
    for (int i = currentSemester - 1; i >= 0; i--) {
      List<Semester.Subject> subjects = semesters.get(i).subjects;
      for (Semester.Subject subject : subjects) {
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

  public float currentAverageGrade() {
    int cnt = 0;
    float sum = 0.0f;
    for (int i = 0; i < currentSemester - 1; i++) {
      List<Semester.Subject> curr = semesters.get(i).subjects;
      for (Semester.Subject subject : curr) {
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

  public boolean largerStipend() { //TODO ПРОВЕРИТЬ НЕЗАЧЕТЫ
    List<Semester.Subject> last = semesters.get(currentSemester - 2).subjects;
    return last.stream()
        .filter(
            subject ->
                    (subject.challenge == FinalChallenge.DIFF_CREDIT
                    || subject.challenge == FinalChallenge.EXAM))
        .map(subject -> subject.mark)
        .allMatch(mark -> mark == Grade.EXC);
  }

  public boolean redDiploma() {
    if (currentSemester != 9) {
      return false;
    }
    return checkDiplomaProjectExc() && checkSatMarks() && checkPercentOfExcGrades();
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
