import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import groovy.util.DelegatingScript;
import org.codehaus.groovy.control.CompilerConfiguration;
import ru.nsu.fit.kuznetsov.model.EvaluatingParameters;
import ru.nsu.fit.kuznetsov.model.GroupConfig;
import ru.nsu.fit.kuznetsov.model.MainConfig;

import java.io.File;
import java.io.IOException;

public class Main {
  public static void main(String[] args) throws IOException {
    CompilerConfiguration cc = new CompilerConfiguration();
    cc.setScriptBaseClass(DelegatingScript.class.getName());

    GroovyShell sh = new GroovyShell(Main.class.getClassLoader(), new Binding(), cc);
    DelegatingScript script =
        (DelegatingScript)
            sh.parse(new File("C:/Users/matvey/IdeaProjects/OOP/TaskChecker/src/main/groovy/config.groovy"));

    MainConfig config = new MainConfig();
    script.setDelegate(config);
    script.run();
    System.out.println(config.toString());
    EvaluatingParameters evaluatingParameters1 = new EvaluatingParameters();
    evaluatingParameters1.setDaysPastDeadline(2);
    evaluatingParameters1.setPointsForTask(5);
    evaluatingParameters1.setAllTestsPassed(true);
    System.out.println(config.getGraders().get("deadlinePoints").getEvaluator().apply(evaluatingParameters1));
  }
}
