package ru.nsu.fit.kuznetsov.snakeGame;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.awt.*;

public class FoodGenerator {

  static int[] walls = new int[400];

  private static final String[] FOODS_IMAGE =
      new String[] {
        "/img/ic_orange.png",
        "/img/ic_apple.png",
        "/img/ic_cherry.png",
        "/img/ic_berry.png",
        "/img/ic_coconut_.png",
        "/img/ic_peach.png",
        "/img/ic_watermelon.png",
        "/img/ic_orange.png",
        "/img/ic_pomegranate.png"
      };
  static Image foodImage;
  static int foodX;
  static int foodY;

  static void generateFood(Snake snake) {
    start:
    while (true) {
      foodX = (int) (Math.random() * Main.ROWS);
      foodY = (int) (Math.random() * Main.COLUMNS);

      if (walls[foodX * Main.ROWS + foodY] == 1) {
        continue;
      }

      for (Point part : snake.snakeBody) {
        if (part.getX() == foodX && part.getY() == foodY) {
          continue start;
        }
      }
      foodImage = new Image(FOODS_IMAGE[(int) (Math.random() * FOODS_IMAGE.length)]);
      break;
    }
  }

  static void drawFood(GraphicsContext gc) {
    gc.drawImage(
        foodImage,
        foodX * Main.SQUARE_SIZE,
        foodY * Main.SQUARE_SIZE,
        Main.SQUARE_SIZE,
        Main.SQUARE_SIZE);
  }
}
