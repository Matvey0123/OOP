package ru.nsu.fit.kuznetsov.gradebook;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

public class Tests {
  @Test
  public void testFromPDF() {
    GradeBook gradeBook = new GradeBook(3,Grade.NONE);
    List<GradeBook.Semester.Subject> firstSemesterSubjects =
        Arrays.asList(
            new GradeBook.Semester.Subject("English", FinalChallenge.CREDIT, Grade.PASS),
            new GradeBook.Semester.Subject(
                "Imperative Programming", FinalChallenge.DIFF_CREDIT, Grade.EXC),
            new GradeBook.Semester.Subject(
                "Declarative Programming", FinalChallenge.DIFF_CREDIT, Grade.GOOD),
            new GradeBook.Semester.Subject(
                "Introduction to Algebra and Analysis", FinalChallenge.EXAM, Grade.GOOD),
            new GradeBook.Semester.Subject(
                "Introduction to DM and ML", FinalChallenge.EXAM, Grade.GOOD),
            new GradeBook.Semester.Subject(
                "History", FinalChallenge.DIFF_CREDIT, Grade.EXC),
            new GradeBook.Semester.Subject(
                "Computing Platforms", FinalChallenge.CREDIT, Grade.PASS),
            new GradeBook.Semester.Subject("PE", FinalChallenge.CREDIT, Grade.PASS),
            new GradeBook.Semester.Subject("BCL", FinalChallenge.DIFF_CREDIT, Grade.EXC));
    GradeBook.Semester firstSemester = new GradeBook.Semester(firstSemesterSubjects);
    gradeBook.addSemester(firstSemester,1);
    List<GradeBook.Semester.Subject> secondSemesterSubjects =
            Arrays.asList(
                    new GradeBook.Semester.Subject("English", FinalChallenge.DIFF_CREDIT, Grade.GOOD),
                    new GradeBook.Semester.Subject(
                            "Imperative Programming", FinalChallenge.EXAM, Grade.EXC),
                    new GradeBook.Semester.Subject(
                            "Declarative Programming", FinalChallenge.DIFF_CREDIT, Grade.EXC),
                    new GradeBook.Semester.Subject(
                            "Introduction to Algebra and Analysis", FinalChallenge.EXAM, Grade.EXC),
                    new GradeBook.Semester.Subject(
                            "Introduction to DM and ML", FinalChallenge.EXAM, Grade.EXC),
                    new GradeBook.Semester.Subject(
                            "Computing Platforms", FinalChallenge.DIFF_CREDIT, Grade.EXC),
                    new GradeBook.Semester.Subject("PE", FinalChallenge.CREDIT, Grade.PASS));
    GradeBook.Semester secondSemester = new GradeBook.Semester(secondSemesterSubjects);
    gradeBook.addSemester(secondSemester,2);
    assertFalse("Not larger stipend!",gradeBook.largerStipend());
    assertEquals(4.66f,gradeBook.currentAveragePoint(),0.01f);
    assertFalse("Not red Diploma yet!",gradeBook.redDiploma());
  }

}
