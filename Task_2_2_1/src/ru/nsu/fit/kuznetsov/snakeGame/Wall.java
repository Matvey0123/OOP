package ru.nsu.fit.kuznetsov.snakeGame;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Wall {

  int startX;
  int startY;
  int finishX;
  int finishY;

  public Wall(int startX, int startY, int finishX, int finishY, GraphicsContext gc) {
    this.startX = startX;
    this.startY = startY;
    this.finishX = finishX;
    this.finishY = finishY;

    drawBackground(gc);
  }

  private void drawBackground(GraphicsContext gc) {
    if (startX == finishX) {
      for (int i = startY; i <= finishY; i++) {
        FoodGenerator.walls[startX * Main.ROWS + i] = 1;
        gc.setFill(Color.GREY);
        gc.fillRect(
            startX * Main.SQUARE_SIZE, i * Main.SQUARE_SIZE, Main.SQUARE_SIZE, Main.SQUARE_SIZE);
      }
    }
    if (startY == finishY) {
      for (int i = startX; i <= finishX; i++) {
        FoodGenerator.walls[i * Main.ROWS + startY] = 1;
        gc.setFill(Color.GREY);
        gc.fillRect(
            i * Main.SQUARE_SIZE, startY * Main.SQUARE_SIZE, Main.SQUARE_SIZE, Main.SQUARE_SIZE);
      }
    }
  }
}
