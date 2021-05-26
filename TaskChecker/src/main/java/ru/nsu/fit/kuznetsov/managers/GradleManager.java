package ru.nsu.fit.kuznetsov.managers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class GradleManager {

  private static final String reposDir = "repos/";

  public boolean build(String groupName, String userName, String taskName) throws IOException {
    String projectDir = reposDir + groupName + "/" + userName + "/" + taskName;

    String command = projectDir + "/gradlew.bat" + " " + "build";
    Process process = Runtime.getRuntime().exec(command);

    BufferedReader stdError = new BufferedReader(new InputStreamReader(process.getErrorStream()));

    while (stdError.readLine() != null) {
      return false;
    }
    return true;
  }

  public int[] run(String groupName, String userName, String taskName) throws IOException {
    int[] result = new int[] {0, 0, 0};

    String projectDir = reposDir + groupName + "/" + userName + "/" + taskName;

    String command = projectDir + "/gradlew.bat" + " " + "run";
    Process process = Runtime.getRuntime().exec(command);

    BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));
    String s;
    // Read the output from the command

    while ((s = stdInput.readLine()) != null) {
      if (!s.equals("") && Character.isDigit(s.charAt(0))) {
        String[] split = s.split(" ");
        int idx = 0;
        for (String str : split) {
          try {
            int num = Integer.parseInt(str);
            result[idx] = num;
            idx++;
          } catch (NumberFormatException ignored) {
          }
        }
      }
    }
    return result;
  }
}
