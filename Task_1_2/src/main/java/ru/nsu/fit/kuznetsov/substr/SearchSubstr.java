package ru.nsu.fit.kuznetsov.substr;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

/** This class allows us to find all occurrences of a substring in a file. */
public class SearchSubstr {
  /**
   * Reads a text from file using buffered reading and calls KMPSearch method.
   *
   * @param FileName the name of file with text
   * @param sub the substring we want to found
   * @return The ArrayList with all occurrences of substring in file
   * @throws IOException -if an I/O error occurs
   */
  public static ArrayList<Integer> search(String FileName, String sub) throws IOException {
    File file = new File(FileName);
    Reader reader =
        new BufferedReader(
            new InputStreamReader(new FileInputStream("input.txt"), StandardCharsets.UTF_8));
    ArrayList<Integer> found = new ArrayList<>();
    int bufLen = 1000000;
    char[] buf = new char[bufLen];
    int globalIndex = 0;
    int num = reader.read(buf, 0, bufLen);
    found = KMPSearch(String.valueOf(buf), sub, globalIndex);
    int subLen = sub.length();
    num = num - subLen + 1;
    while (num != -1) {
      System.arraycopy(buf, bufLen - subLen + 1, buf, 0, subLen - 1);
      globalIndex = globalIndex + num;
      num = reader.read(buf, subLen - 1, bufLen - subLen + 1);
      found.addAll(KMPSearch(String.valueOf(buf).substring(0, subLen - 1 + num), sub, globalIndex));
    }
    return found;
  }

  /**
   * Count the prefix function from the substring for KMP algorithm.
   *
   * @param sample the substring for count prefix function
   * @return The integer array where the values of prefix function stored
   */
  private static int[] prefixFunction(String sample) {
    int[] values = new int[sample.length()];
    for (int i = 1; i < sample.length(); i++) {
      int j = 0;
      while (i + j < sample.length() && sample.charAt(j) == sample.charAt(i + j)) {
        values[i + j] = Math.max(values[i + j], j + 1);
        j++;
      }
    }
    return values;
  }

  /**
   * Search all occurrences of substring in text using KMP algorithm.
   *
   * @param text the buffer where we will search the substring
   * @param sample the substring to be found
   * @param idx the global index which keeps how many characters in our file have been already
   *     processed
   * @return The ArrayList with all occurrences of substring in the buffer
   */
  private static ArrayList<Integer> KMPSearch(String text, String sample, int idx) {
    ArrayList<Integer> found = new ArrayList<>();
    int[] prefixFunc = prefixFunction(sample);
    int i = 0;
    int j = 0;
    while (i < text.length()) {
      if (sample.charAt(j) == text.charAt(i)) {
        j++;
        i++;
      }
      if (j == sample.length()) {
        found.add(i - j + idx);
        j = prefixFunc[j - 1];
      } else if (i < text.length() && sample.charAt(j) != text.charAt(i)) {
        if (j != 0) {
          j = prefixFunc[j - 1];
        } else {
          i = i + 1;
        }
      }
    }
    return found;
  }
}
