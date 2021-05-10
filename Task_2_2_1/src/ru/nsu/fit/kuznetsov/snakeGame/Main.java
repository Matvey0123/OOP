package ru.nsu.fit.kuznetsov.snakeGame;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.Arrays;

public class Main extends Application {

  static final int WIDTH = 400;
  static final int HEIGHT = 400;
  static final int ROWS = 20;
  static final int COLUMNS = ROWS;
  static final int SQUARE_SIZE = WIDTH / ROWS;

  private static final int RIGHT = 0;
  private static final int LEFT = 1;
  private static final int UP = 2;
  private static final int DOWN = 3;

  private GraphicsContext gc;
  private Snake mainSnake;

  private boolean gameOver;
  private int currentDirection;
  private int score = 0;

  @Override
  public void start(Stage primaryStage) {
    primaryStage.setTitle("Snake");
    Group root = new Group();
    Canvas canvas = new Canvas(WIDTH, HEIGHT);
    root.getChildren().add(canvas);
    Scene scene = new Scene(root);
    primaryStage.setScene(scene);
    primaryStage.show();
    gc = canvas.getGraphicsContext2D();

    scene.setOnKeyPressed(
        event -> {
          KeyCode code = event.getCode();
          if (code == KeyCode.RIGHT || code == KeyCode.D) {
            if (currentDirection != LEFT) {
              currentDirection = RIGHT;
            }
          } else if (code == KeyCode.LEFT || code == KeyCode.A) {
            if (currentDirection != RIGHT) {
              currentDirection = LEFT;
            }
          } else if (code == KeyCode.UP || code == KeyCode.W) {
            if (currentDirection != DOWN) {
              currentDirection = UP;
            }
          } else if (code == KeyCode.DOWN || code == KeyCode.S) {
            if (currentDirection != UP) {
              currentDirection = DOWN;
            }
          }
        });

    mainSnake = new Snake(1, 1);
    Arrays.fill(FoodGenerator.walls, 0);
    FoodGenerator.generateFood(mainSnake);

    Timeline timeline = new Timeline(new KeyFrame(Duration.millis(130), e -> run(gc)));
    timeline.setCycleCount(Animation.INDEFINITE);
    timeline.play();
  }

  private void run(GraphicsContext gc) {
    if (gameOver) {
      gc.setFill(Color.RED);
      gc.setFont(new Font("Digital-7", 50));
      gc.fillText("Game Over", WIDTH / 4.2, HEIGHT / 2.0);
      return;
    }
    if (score == 20) {
      gc.setFill(Color.RED);
      gc.setFont(new Font("Digital-7", 50));
      gc.fillText("You win!", WIDTH / 4.2, HEIGHT / 2.0);
      return;
    }
    drawBackground(gc);
    new Wall(1, 2, 1, 7, gc);
    new Wall(5, 2, 10, 2, gc);
    new Wall(17, 2, 17, 14, gc);
    new Wall(9, 6, 9, 10, gc);
    FoodGenerator.drawFood(gc);

    mainSnake.drawSnake(gc);
    drawScore();

    mainSnake.move();

    switch (currentDirection) {
      case RIGHT:
        mainSnake.moveRight();
        break;
      case LEFT:
        mainSnake.moveLeft();
        break;
      case UP:
        mainSnake.moveUp();
        break;
      case DOWN:
        mainSnake.moveDown();
        break;
      default:
        break;
    }

    gameOver = mainSnake.gameOver();
    score += mainSnake.eatFood();
  }

  private void drawBackground(GraphicsContext gc) {
    for (int i = 0; i < ROWS; i++) {
      for (int j = 0; j < COLUMNS; j++) {
        if ((i + j) % 2 == 0) {
          gc.setFill(Color.web("AAD751"));
        } else {
          gc.setFill(Color.web("A2D149"));
        }
        gc.fillRect(i * SQUARE_SIZE, j * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);
      }
    }
  }

  private void drawScore() {
    gc.setFill(Color.WHITE);
    gc.setFont(new Font("Digital-7", 35));
    gc.fillText("Score: " + score + " / 20", 10, 35);
  }

  public static void main(String[] args) {
    launch(args);
  }
}
