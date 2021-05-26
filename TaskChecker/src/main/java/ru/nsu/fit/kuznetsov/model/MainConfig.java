package ru.nsu.fit.kuznetsov.model;

import groovy.lang.Closure;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class MainConfig {

  private final List<GroupConfig> groups = new ArrayList<>();
  private final Map<String, GradingConfig> graders = new HashMap<>();

  public void group(Closure closure) {
    GroupConfig config = new GroupConfig();
    closure.setDelegate(config);
    closure.setResolveStrategy(Closure.DELEGATE_FIRST);
    closure.call();

    groups.add(config);
  }

  public void grader(Closure closure) {
    GradingConfig gradingConfig = new GradingConfig();
    closure.setDelegate(gradingConfig);
    closure.setResolveStrategy(Closure.DELEGATE_FIRST);
    closure.call();
    graders.put(gradingConfig.getId(), gradingConfig);
  }

  public void postProcess() {
    for (int i = 0; i < groups.size(); i++) {
      GroupConfig groupConfig = groups.get(i);
      List<StudentConfig> students = new ArrayList<>();
      List<GivenTaskConfig> tasks = new ArrayList<>();
      List<LessonConfig> lessons = new ArrayList<>();
      List<ControlMarkConfig> controlPoints = new ArrayList<>();
      for (Object studentConfig : groupConfig.students) {
        StudentConfig item = new StudentConfig();
        ((Closure) studentConfig).setDelegate(item);
        ((Closure) studentConfig).setResolveStrategy(Closure.DELEGATE_FIRST);
        ((Closure) studentConfig).call();

        students.add(item);
      }

      for (Object taskConfig : groupConfig.tasks) {
        GivenTaskConfig item = new GivenTaskConfig();
        ((Closure) taskConfig).setDelegate(item);
        ((Closure) taskConfig).setResolveStrategy(Closure.DELEGATE_FIRST);
        ((Closure) taskConfig).call();

        tasks.add(item);
      }

      for (Object lessonConfig : groupConfig.lessons) {
        LessonConfig item = new LessonConfig();
        ((Closure) lessonConfig).setDelegate(item);
        ((Closure) lessonConfig).setResolveStrategy(Closure.DELEGATE_FIRST);
        ((Closure) lessonConfig).call();

        lessons.add(item);
      }

      for (Object controlConfig : groupConfig.controlPoints) {
        ControlMarkConfig item = new ControlMarkConfig();
        ((Closure) controlConfig).setDelegate(item);
        ((Closure) controlConfig).setResolveStrategy(Closure.DELEGATE_FIRST);
        ((Closure) controlConfig).call();

        controlPoints.add(item);
      }

      GroupConfig groupConfig1 = new GroupConfig();
      groupConfig1.setName(groupConfig.getName());
      groupConfig1.setStudents(students);
      groupConfig1.setTasks(tasks);
      groupConfig1.setLessons(lessons);
      groupConfig1.setControlPoints(controlPoints);
      groups.set(i, groupConfig1);
    }
  }
}
