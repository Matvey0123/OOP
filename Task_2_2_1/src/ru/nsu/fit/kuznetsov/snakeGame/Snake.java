package ru.nsu.fit.kuznetsov.snakeGame;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Snake {

  List<Point> snakeBody = new ArrayList();
  Point snakeHead;

  public Snake(int x, int y) {
    snakeBody.add(new Point(x, y));
    snakeHead = snakeBody.get(0);
  }

  void moveRight() {
    snakeHead.x++;
  }

  void moveLeft() {
    snakeHead.x--;
  }

  void moveUp() {
    snakeHead.y--;
  }

  void moveDown() {
    snakeHead.y++;
  }

  void drawSnake(GraphicsContext gc) {
    gc.setFill(Color.web("4674E9"));
    gc.fillRoundRect(
        snakeHead.getX() * Main.SQUARE_SIZE,
        snakeHead.getY() * Main.SQUARE_SIZE,
        Main.SQUARE_SIZE - 1,
        Main.SQUARE_SIZE - 1,
        35,
        35);

    for (int i = 1; i < snakeBody.size(); i++) {
      gc.fillRoundRect(
          snakeBody.get(i).getX() * Main.SQUARE_SIZE,
          snakeBody.get(i).getY() * Main.SQUARE_SIZE,
          Main.SQUARE_SIZE - 1,
          Main.SQUARE_SIZE - 1,
          20,
          20);
    }
  }

  public boolean gameOver() {
    // out of screen
    if (snakeHead.x < 0
        || snakeHead.y < 0
        || snakeHead.x * Main.SQUARE_SIZE >= Main.WIDTH
        || snakeHead.y * Main.SQUARE_SIZE >= Main.HEIGHT) {
      return true;
    }

    // hit in wall
    if (FoodGenerator.walls[snakeHead.x * Main.ROWS + snakeHead.y] == 1) {
      return true;
    }

    // destroy itself
    for (int i = 1; i < snakeBody.size(); i++) {
      if (snakeHead.x == snakeBody.get(i).getX() && snakeHead.getY() == snakeBody.get(i).getY()) {
        return true;
      }
    }
    return false;
  }

  int eatFood() {
    if (snakeHead.getX() == FoodGenerator.foodX && snakeHead.getY() == FoodGenerator.foodY) {
      snakeBody.add(new Point(-1, -1));
      FoodGenerator.generateFood(this);
      return 1;
    }
    return 0;
  }
}
