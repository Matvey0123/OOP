package ru.nsu.fit.kuznetsov.model;

import groovy.lang.Closure;
import groovy.lang.GroovyObjectSupport;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class GroupConfig extends GroovyObjectSupport {

  private String name;
  List<StudentConfig> students;
  List<GivenTaskConfig> tasks;
  List<LessonConfig> lessons;
  List<ControlMarkConfig> controlPoints;

  public void student(Closure closure) {
    StudentConfig studentConfig = new StudentConfig();
    closure.setDelegate(studentConfig);
    closure.setResolveStrategy(Closure.DELEGATE_FIRST);
    closure.call();
  }

  public void task(Closure closure) {
    GivenTaskConfig taskConfig = new GivenTaskConfig();
    closure.setDelegate(taskConfig);
    closure.setResolveStrategy(Closure.DELEGATE_FIRST);
    closure.call();
  }

  public void lesson(Closure closure) {
    LessonConfig lessonConfig = new LessonConfig();
    closure.setDelegate(lessonConfig);
    closure.setResolveStrategy(Closure.DELEGATE_FIRST);
    closure.call();
  }

  public void controlPoint(Closure closure) {
    ControlMarkConfig controlMarkConfig = new ControlMarkConfig();
    closure.setDelegate(controlMarkConfig);
    closure.setResolveStrategy(Closure.DELEGATE_FIRST);
    closure.call();
  }
}
