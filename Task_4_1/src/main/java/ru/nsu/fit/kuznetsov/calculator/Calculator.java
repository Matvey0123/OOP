package ru.nsu.fit.kuznetsov.calculator;

import java.util.EmptyStackException;
import java.util.Stack;

/** This class allows us to evaluate expression in prefix and postfix form using stack. */
public class Calculator {
  /**
   * Applies the operation to the specified number of arguments in stack.
   *
   * @param stack the stack that contains arguments
   * @param operation the operation to be applied
   */
  private void apply(MyStack<Float> stack, String operation) {
    Float num1, num2;
    try {
      switch (operation) {
        case "+":
          num1 = stack.pop();
          num2 = stack.pop();
          stack.push(num1 + num2);
          return;
        case "-":
          num1 = stack.pop();
          num2 = stack.pop();
          stack.push(num1 - num2);
          return;
        case "*":
          num1 = stack.pop();
          num2 = stack.pop();
          stack.push(num1 * num2);
          return;
        case "/":
          num1 = stack.pop();
          num2 = stack.pop();
          stack.push(num1 / num2);
          return;
        case "pow":
          num1 = stack.pop();
          num2 = stack.pop();
          stack.push((float) Math.pow(num1, num2));
          return;
        case "log":
          num1 = stack.pop();
          num2 = stack.pop();
          stack.push((float) (Math.log(num1) / Math.log(num2)));
          return;
        case "sqrt":
          num1 = stack.pop();
          stack.push((float) Math.sqrt(num1));
          return;
        case "sin":
          num1 = stack.pop();
          stack.push((float) Math.sin(num1));
          return;
        case "cos":
          num1 = stack.pop();
          stack.push((float) Math.cos(num1));
          return;
        default:
          throw new IllegalArgumentException("Invalid operation: " + operation);
      }
    } catch (EmptyStackException e) {
      throw new IllegalArgumentException("Not enough arguments for operation: " + operation);
    }
  }

  /**
   * Evaluates the expression in postfix form
   *
   * @param exprPost the expression to be evaluated
   * @return the result of evaluation as float
   * @throws IllegalArgumentException if the expression is incorrect
   */
  public float evaluate(String[] exprPost) throws IllegalArgumentException {
    MyStack<Float> stack = new MyStack<>();
    for (String s : exprPost) {
      try {
        float num = Float.parseFloat(s);
        stack.push(num);
      } catch (NumberFormatException e) {
        apply(stack, s);
      }
    }
    if (stack.size() != 1) {
      throw new IllegalArgumentException("Not enough operations");
    }
    return stack.pop();
  }

  public static void main(String[] args) {
    String[] exprPost = new String[args.length];
    for (int i = args.length - 1; i >= 0; i--) {
      exprPost[args.length - 1 - i] = args[i];
    }
    Calculator calculator = new Calculator();
    try {
      float result = calculator.evaluate(exprPost);
      System.out.println(result);
    } catch (IllegalArgumentException e) {
      System.out.println(e.getMessage());
    }
  }
}
