package ru.nsu.fit.kuznetsov.calculator;

import java.util.EmptyStackException;
import java.util.Stack;

public class Calculator {
  private void apply(Stack<Float> stack, String operation) {
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

  public float evaluate(String[] exprPost) throws IllegalArgumentException {
    Stack<Float> stack = new Stack<>();
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
      exprPost[args.length -1 - i] = args[i];
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
