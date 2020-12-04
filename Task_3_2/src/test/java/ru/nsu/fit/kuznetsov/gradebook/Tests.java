package ru.nsu.fit.kuznetsov.gradebook;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

public class Tests {
  @Test
  public void myGradeBook() {
    GradeBook gradeBook = new GradeBook(8);
    List<Subject> firstSemesterSubjects =
        Arrays.asList(
            new Subject("English", FinalChallenge.CREDIT, Grade.PASS),
            new Subject("Imperative Programming", FinalChallenge.DIFF_CREDIT, Grade.EXC),
            new Subject("Declarative Programming", FinalChallenge.DIFF_CREDIT, Grade.GOOD),
            new Subject("Introduction to Algebra and Analysis", FinalChallenge.EXAM, Grade.GOOD),
            new Subject("Introduction to DM and ML", FinalChallenge.EXAM, Grade.GOOD),
            new Subject("History", FinalChallenge.DIFF_CREDIT, Grade.EXC),
            new Subject("Computing Platforms", FinalChallenge.CREDIT, Grade.PASS),
            new Subject("PE", FinalChallenge.CREDIT, Grade.PASS),
            new Subject("BCL", FinalChallenge.DIFF_CREDIT, Grade.EXC));
    Semester firstSemester = new Semester(firstSemesterSubjects);
    gradeBook.addSemester(firstSemester);
    List<Subject> secondSemesterSubjects =
        Arrays.asList(
            new Subject("English", FinalChallenge.DIFF_CREDIT, Grade.GOOD),
            new Subject("Imperative Programming", FinalChallenge.EXAM, Grade.EXC),
            new Subject("Declarative Programming", FinalChallenge.DIFF_CREDIT, Grade.EXC),
            new Subject("Introduction to Algebra and Analysis", FinalChallenge.EXAM, Grade.EXC),
            new Subject("Introduction to DM and ML", FinalChallenge.EXAM, Grade.EXC),
            new Subject("Computing Platforms", FinalChallenge.DIFF_CREDIT, Grade.EXC),
            new Subject("PE", FinalChallenge.CREDIT, Grade.PASS));
    Semester secondSemester = new Semester(secondSemesterSubjects);
    gradeBook.addSemester(secondSemester);
    assertFalse("Not larger stipend!", gradeBook.largerStipend());
    assertEquals(4.66f, gradeBook.currentAverageGrade(), 0.01f);
    assertTrue("Not red Diploma yet!", gradeBook.redDiploma());
  }

  @Test(expected = IllegalArgumentException.class)
  public void illegalArguments() throws IllegalArgumentException {
    Subject subject = new Subject("Not my case", FinalChallenge.EXAM, Grade.FAIL);
    Subject subject1 = new Subject("", FinalChallenge.DIFF_CREDIT, Grade.PASS);
    Subject subject2 = new Subject("", FinalChallenge.CREDIT, Grade.EXC);
  }

  @Test(expected = IllegalArgumentException.class)
  public void addSemesterTest() throws IllegalArgumentException {
    Subject subject = new Subject("", FinalChallenge.CREDIT, Grade.EXC);
    Semester semester = new Semester();
    semester.addSubject(subject);
    assertEquals(0, semester.subjects.size());
    assertFalse("no retakes", semester.wereRetakes);
    semester.addRetake(true);
    semester.addRetake(false);
    assertTrue("now it is a retake", semester.wereRetakes);
  }

  @Test
  public void test2() {
    GradeBook gradeBook = new GradeBook(2);
    assertFalse(gradeBook.largerStipend());
    gradeBook.addSubject(new Subject("Maths", FinalChallenge.EXAM, Grade.EXC), 1);
    gradeBook.addSubject(new Subject("PE", FinalChallenge.CREDIT, Grade.PASS), 1);
    gradeBook.addSubject(new Subject("Programming", FinalChallenge.DIFF_CREDIT, Grade.EXC), 1);
    assertEquals(5.0f, gradeBook.currentAverageGrade(), 0.001f);
    assertTrue(gradeBook.largerStipend());

    gradeBook.addSubject(new Subject("Maths", FinalChallenge.EXAM, Grade.EXC), 2);
    gradeBook.addSubject(new Subject("PE", FinalChallenge.CREDIT, Grade.PASS), 2);
    gradeBook.addSubject(new Subject("Programming", FinalChallenge.DIFF_CREDIT, Grade.EXC), 2);
    gradeBook.setMarkForDiplomaProject(Grade.EXC);
    assertTrue("red diploma!", gradeBook.redDiploma());
  }
}
