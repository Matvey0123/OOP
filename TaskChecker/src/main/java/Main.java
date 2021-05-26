import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import groovy.util.DelegatingScript;
import org.codehaus.groovy.control.CompilerConfiguration;
import ru.nsu.fit.kuznetsov.managers.GitManager;
import ru.nsu.fit.kuznetsov.managers.GradleManager;
import ru.nsu.fit.kuznetsov.model.*;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.function.Function;

public class Main {
  public static void main(String[] args) throws IOException, ParseException, InterruptedException {
    CompilerConfiguration cc = new CompilerConfiguration();
    cc.setScriptBaseClass(DelegatingScript.class.getName());

    GroovyShell sh = new GroovyShell(Main.class.getClassLoader(), new Binding(), cc);
    DelegatingScript script =
        (DelegatingScript) sh.parse(new File("src/main/groovy/config.groovy"));

    MainConfig config = new MainConfig();
    script.setDelegate(config);
    script.run();
    config.postProcess();

    cloneAllRepos(config);
    Thread.sleep(10000);

    String studentName = config.getGroups().get(0).getStudents().get(0).getName();
    Function<EvaluatingParameters, Integer> evaluator =
        config.getGraders().get("deadlinePoints").getEvaluator();
    for (GroupConfig group : config.getGroups()) {
      checkTasks(group.getTasks(), group.getName(), studentName, evaluator);
    }
  }

  public static void cloneAllRepos(MainConfig config) throws IOException {
    GitManager gitManager = new GitManager();
    gitManager.createReposDirectory();

    for (GroupConfig groupConfig : config.getGroups()) {
      String groupName = groupConfig.getName();
      List<StudentConfig> studentConfigList = groupConfig.getStudents();
      for (StudentConfig studentConfig : studentConfigList) {
        String studentName = studentConfig.getName();
        String studentGitUrl = studentConfig.getGitURL();

        gitManager.clone(studentGitUrl, groupName, studentName);
      }
    }
  }

  public static void checkTasks(
      List<GivenTaskConfig> tasks,
      String groupName,
      String userName,
      Function<EvaluatingParameters, Integer> evaluator)
      throws IOException, ParseException {
    GradleManager gradleManager = new GradleManager();
    for (GivenTaskConfig task : tasks) {
      String taskName = task.getName();
      boolean isBuild = gradleManager.build(groupName, userName, taskName);
      EvaluatingParameters parameters = new EvaluatingParameters();
      String built = "-";
      int[] tests = new int[] {0, 0, 0};
      if (isBuild) {
        built = "+";
        tests = gradleManager.run(groupName, userName, taskName);
        parameters.setAllTestsPassed(true);
      }
      String passedDeadline = "-";
      parameters.setDaysPastDeadline(4);
      if (checkDeadline(taskName, task.getDeadline())) {
        passedDeadline = "+";
        parameters.setDaysPastDeadline(0);
      }
      parameters.setPointsForTask(task.getPoints());
      int points = evaluator.apply(parameters);
      System.out.println("\n-----------------------------------------------------------------");
      System.out.println(
          "       | Lab " + taskName + "                                           |\n");
      System.out.println("       | Build | Tests | Passed | Up-to-date | Deadline | Total |\n");
      System.out.println(
          userName
              + " | "
              + built
              + "     | "
              + tests[0]
              + "     | "
              + tests[1]
              + "      | "
              + tests[2]
              + "          | "
              + passedDeadline
              + "        | "
              + points
              + "     |\n");
    }
  }

  public static boolean checkDeadline(String taskName, String taskDeadline)
      throws IOException, ParseException {
    GitManager gitManager = new GitManager();
    String lastCommit = gitManager.getLastCommitTime(taskName);
    Date dateLastCommit = new SimpleDateFormat("yyyy-MM-dd").parse(lastCommit);
    Date dateTaskDeadline = new SimpleDateFormat("dd.MM.yyyy").parse(taskDeadline);
    int cmp = dateTaskDeadline.compareTo(dateLastCommit);
    return cmp >= 0;
  }
}
