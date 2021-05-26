package ru.nsu.fit.kuznetsov.managers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;

public class GitManager {

  private static final String reposDir = "repos/";

  public void clone(String gitUrl, String groupName, String studentName) throws IOException {
    String repoName = reposDir + "/" + groupName + "/" + studentName;
    Runtime.getRuntime().exec("git " + "clone " + gitUrl + " " + repoName);
  }

  public void createReposDirectory() throws IOException {
    Files.createDirectories(Paths.get(reposDir));
  }

  public String getLastCommitTime(String taskName) throws IOException {
    String taskPath = "../" + taskName;
    Process process = Runtime.getRuntime().exec("git log -1 --pretty=\"format:%ci\" " + taskPath);

    BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));
    String s;
    String date = null;
    while ((s = stdInput.readLine()) != null) {
      date = s.split(" ")[0];
    }

    return date;
  }
}
