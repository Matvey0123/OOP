package ru.nsu.fit.kuznetsov.substr;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

/** This class allows us to find all occurrences of a substring in a file. */
public class SearchSubstr {
  /**
   * Reads a text from file using buffered reading and calls KMPSearch method.
   *
   * @param input
   * @param sub the substring we want to found
   * @return The ArrayList with all occurrences of substring in file
   * @throws IOException -if an I/O error occurs
   */
  public static ArrayList<Integer> search(InputStream input, String sub) throws IOException {
    try (Reader reader = new BufferedReader(new InputStreamReader(input, StandardCharsets.UTF_8))) {
      ArrayList<Integer> found;
      final int BUFFER_SIZE = 100000;
      char[] buf = new char[BUFFER_SIZE];
      int[] prefixFunc = prefixFunction(sub);
      int mainIndexInFile = 0;
      int numOfReadChars = reader.read(buf, 0, BUFFER_SIZE);
      found = KMPSearch(String.valueOf(buf), sub, prefixFunc, mainIndexInFile);
      int subLen = sub.length();
      numOfReadChars = numOfReadChars - subLen + 1;
      while (numOfReadChars != -1) {
        System.arraycopy(buf, BUFFER_SIZE - subLen + 1, buf, 0, subLen - 1);
        mainIndexInFile = mainIndexInFile + numOfReadChars;
        numOfReadChars = reader.read(buf, subLen - 1, BUFFER_SIZE - subLen + 1);
        found.addAll(
            KMPSearch(
                String.valueOf(buf).substring(0, subLen - 1 + numOfReadChars),
                sub,
                prefixFunc,
                mainIndexInFile));
      }
      return found;
    }
  }

  public static ArrayList<Integer> search(String fileName, String sub) throws IOException {
    try (InputStream in = new FileInputStream(fileName)) {
      return search(in, sub);
    }
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
   * @param prefixFunc the integer array that used by KMP algorithm
   * @param idx the global index which keeps how many characters in our file have been already
   *     processed
   * @return The ArrayList with all occurrences of substring in the buffer
   */
  private static ArrayList<Integer> KMPSearch(
      String text, String sample, int[] prefixFunc, int idx) {
    ArrayList<Integer> found = new ArrayList<>();
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
