package ru.nsu.fit.kuznetsov.calculator;

import org.junit.Test;

import static org.junit.Assert.*;

public class CalculatorTest {
  @Test
  public void testFromPDF() {
    Calculator calculator = new Calculator();
    String[] args = {"1", "2", "1", "-", "+", "sin"};
    assertEquals(0.0f, calculator.evaluate(args), 0.00f);
  }

  @Test
  public void testCos() {
    Calculator calculator = new Calculator();
    String[] args = {"1", "2", "1", "-", "+", "cos"};
    assertEquals(1.0f, calculator.evaluate(args), 0.00f);
  }

  @Test
  public void testLog() {
    Calculator calculator = new Calculator();
    String[] args = {"4", "1", "2", "1", "+", "+", "log"};
    assertEquals(1.0f, calculator.evaluate(args), 0.00f);
  }

  @Test
  public void testPow() {
    Calculator calculator = new Calculator();
    String[] args = {"2", "1", "2", "1", "+", "+", "pow"};
    assertEquals(16.0f, calculator.evaluate(args), 0.00f);
  }

  @Test
  public void testSqrt() {
    Calculator calculator = new Calculator();
    String[] args = {"1", "2", "1", "+", "+", "sqrt"};
    assertEquals(2.0f, calculator.evaluate(args), 0.00f);
  }

  @Test
  public void test1() {
    Calculator calculator = new Calculator();
    String[] args = {"5", "1", "2", "10", "-", "+", "*"};
    assertEquals(45.0f, calculator.evaluate(args), 0.00f);
  }

  @Test
  public void test2() {
    Calculator calculator = new Calculator();
    String[] args = {"9", "5", "1", "2", "10", "-", "+", "*", "/"};
    assertEquals(5.0f, calculator.evaluate(args), 0.00f);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testException1() throws IllegalArgumentException {
    Calculator calculator = new Calculator();
    String[] args = {"9", "5", "1", "2", "10"};
    assertEquals(5.0f, calculator.evaluate(args), 0.00f);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testException2() throws IllegalArgumentException {
    Calculator calculator = new Calculator();
    String[] args = {"9", "-"};
    assertEquals(5.0f, calculator.evaluate(args), 0.00f);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testException3() throws IllegalArgumentException {
    Calculator calculator = new Calculator();
    String[] args = {"trash", "trash", "trash"};
    assertEquals(5.0f, calculator.evaluate(args), 0.00f);
  }
}
