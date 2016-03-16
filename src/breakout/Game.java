package breakout;

import java.util.ArrayList;

import javafx.animation.AnimationTimer;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Game {

	private final int BRICKS_PER_ROW = 15;
	private final int ROW_OF_BRICKS = 10;
	private final int PADDING = 50;
	private final int INITIAL_LIVES = 3;
	
	private static double width;
	private static double height;
	private double brickWidth;
	private double brickHeight;
	private String title;
	private boolean clicked;
	
	private int livesLeft;
	
	public Game(String title, double width, double height, Stage stage) {
		this.title = title;
		this.stage = stage;
		Game.width = width;
		Game.height = height;
		
		brickWidth = (width - PADDING * 2) / BRICKS_PER_ROW;
		brickHeight = brickWidth / 2.5;
	}
	
	private Stage stage;
	private Pane root;
	private Scene scene;
	
	private Racket racket;
	private Ball ball;
	private double ballSpeed;
	private ArrayList<Brick> bricks;
	
	private Timeline timeline;
	private AnimationTimer timer;
	
	private Button newGameButton;
	
	private ArrayList<Circle> lives;
	private Label livesLabel;
	
	private int score;
	private Label scoreLabel;
	
	private int gameStartTimestamp;
	private int seconds;
	private Label secondsLabel;

//	private int fps;
//	private Label fpsLabel;
//	private Label engineMicroSecondsLabel;
//	private Label drawMsLabel;
	private int lastFpsTimer;
//	private long lastEngineStartNs;
//	private long lastEngineEnd;
//	private long lastEngineEndNs;
	
	private ArrayList<Button> levelButtons;
        
	public void init(double ballSpeed) {
		
		// Settings
		clicked = false;
		score = 0;
		livesLeft = INITIAL_LIVES;
		this.ballSpeed = ballSpeed;
		
		// Scene settings
		root = new Pane();
		scene = new Scene(root, width, height);
		
		// Racket
		racket = new Racket(100, 10);
		racket.setX((width / 2) - racket.getHalfWidth());
		racket.setY(height - racket.getHeight() * 6);
		
		// Ball
		ball = new Ball(10, ballSpeed);
		ball.setCenterX(racket.getX() + racket.getHalfWidth());
		ball.setCenterY(racket.getY() - racket.getHeight() * 1.2);
		
		// Bricks
		bricks = new ArrayList<Brick>();
		initBricks();
		Brick.setFastCollDetArea((int)brickWidth, (int)brickHeight, ball);
		
		// Buttons
		newGameButton = new Button("New Game");
		newGameButton.setLayoutY(height - 30);
		newGameButton.setLayoutX(10);
		newGameButton.setOnAction(e -> {
			this.newGame(ballSpeed);
		});
		
		
		levelButtons = new ArrayList<Button>();
		for (int i = 1; i <= 3; i++) {
			levelButtons.add(new Button("Level " + i));
		}
		
		for (int i = 0; i < 3; i++) {
			levelButtons.get(i).setLayoutX(width - (64*3 + 5) + i * 64);
			levelButtons.get(i).setLayoutY(height - 30);
			int speed = i;
			levelButtons.get(i).setOnAction(e -> {
				newGame(7 + 2 * speed);
			});
		}
		
		
		
		// Lives
		lives = new ArrayList<Circle>();
		livesLabel = new Label("Lives:");
		fixLives();
		
		// Score
		scoreLabel = new Label("Score: 0");
		scoreLabel.setTextFill(Color.WHITE);
		scoreLabel.setFont(new Font(24));
		scoreLabel.setLayoutX(width / 2 - 75);
		
		// Seconds
		secondsLabel = new Label("0");
		secondsLabel.setTextFill(Color.WHITE);
		secondsLabel.setFont(new Font(20));
		secondsLabel.setLayoutY(0);
		secondsLabel.layoutXProperty().bind(secondsLabel.widthProperty().negate().add(width).subtract(15));
		gameStartTimestamp = (int)(System.currentTimeMillis() / 1000L);
		seconds = 0;
		
		// Frames pr second
//		fpsLabel = new Label("FPS: 0");
//		fpsLabel.setTextFill(Color.WHITE);
//		fpsLabel.setFont(new Font(24));
//		fpsLabel.setLayoutX(200);
//		fps = 0;
		lastFpsTimer = 0;
//		lastEngineStartNs = 0;
//		lastEngineEnd = 0;
//		lastEngineEndNs = 0;
		
		// Engine ms
//		engineMicroSecondsLabel = new Label("Engine µs: 0");
//		engineMicroSecondsLabel.setTextFill(Color.WHITE);
//		engineMicroSecondsLabel.setFont(new Font(24));
//		engineMicroSecondsLabel.setLayoutX(400);
		
		// Draw ms
//		drawMsLabel = new Label("Draw ms: 0");
//		drawMsLabel.setTextFill(Color.WHITE);
//		drawMsLabel.setFont(new Font(24));
//		drawMsLabel.setLayoutX(600);

		// Add too ROOT
		root.getChildren().add(racket);
		root.getChildren().add(ball);
		root.getChildren().addAll(bricks);
		root.getChildren().add(newGameButton);
		root.getChildren().add(livesLabel);
		root.getChildren().add(scoreLabel);
		root.getChildren().add(secondsLabel);
//		root.getChildren().add(fpsLabel);
//		root.getChildren().add(engineMicroSecondsLabel);
//		root.getChildren().add(drawMsLabel);
		root.getChildren().addAll(levelButtons);
		root.getChildren().addAll(lives);
		
		// ActionEvents
		root.setOnMouseMoved(e -> {
			if (!clicked)
				ball.setCenterX(racket.getX() + racket.getHalfWidth());
			
			racket.setX(e.getX() - racket.getHalfWidth());
			
			if (racket.getX() < 0)
				racket.setX(0);
			if (racket.getX() + racket.getWidth() >= width)
				racket.setX(width - racket.getWidth());
		});
		root.setOnMouseClicked(e -> {
			if (!clicked) {
				clicked = true;
				gameStartTimestamp = (int)(System.currentTimeMillis() / 1000L);
				setButtonsVisible(false);
			}
		});
		
		// Styles
		root.setStyle("-fx-background-color: #000000;");
		
		// Stage setup
		stage.setTitle(title);
		stage.setResizable(false);
		stage.show();
		stage.setScene(scene);
		
		timeline = new Timeline();
		timeline.setCycleCount(Timeline.INDEFINITE);
		timeline.setAutoReverse(true);
		
		// Graphical update/logic
		timer = new AnimationTimer() {
			@Override
			public void handle(long now) {
//				fps++;
//				long mainLoopStart = System.currentTimeMillis();
				int fpsTimer = (int)(System.currentTimeMillis() / 1000L);
				if (clicked && fpsTimer != lastFpsTimer) {
					seconds = fpsTimer - gameStartTimestamp;
					secondsLabel.setText("" + seconds);
//					fpsLabel.setText("FPS: " + fps);
//					fps = 0;
					lastFpsTimer = fpsTimer;
//					engineMicroSecondsLabel.setText("Engine µs: " + ((lastEngineEndNs - lastEngineStartNs) / 1000L));
//					drawMsLabel.setText("Draw ms: " + (mainLoopStart - lastEngineEnd));
				}
//				lastEngineStartNs = System.nanoTime();
				if (clicked) {
					ball.move();
				}
				
				if (ball.intersects(racket.getBoundsInLocal())) {
					racket.bounceBall(ball);
                    ball.setRadius(ball.getRadius());
                    // (Svein): brukt for debug
                    // System.out.println("DX: " + ball.getDX() + " - DY: " + ball.getDY());
				}
				
				if (ball.lost()) {
					looseLife();
				}
				
				for (Brick brick : bricks) {
					if (brick.collision(ball)) {
						root.getChildren().remove(brick);
						brick.setX(-100);
						score++;
						scoreLabel.setText("Score: " + score);
					}
					/*
					if (ball.intersects(brick.getBoundsInLocal())) {
						
						if (ball.getCenterY() - ball.getRadius() > brick.getLayoutY() + brick.getHeight()) {
							ball.reverseDY();
						} else if (ball.getCenterY() + ball.getRadius() < brick.getLayoutY()) {
							ball.reverseDY();
						} else if (ball.getCenterX() + ball.getRadius() < brick.getLayoutX()) {
							ball.reverseDX();
						} else if (ball.getCenterX() - ball.getRadius() >= brick.getLayoutX() + brick.getWidth()) {
							ball.reverseDX();
						}
						
						root.getChildren().remove(brick);
						brick.setX(-100);
						score++;
						scoreLabel.setText("Score: " + score);
					}
					*/
				}
//				lastEngineEnd = System.currentTimeMillis();
//				lastEngineEndNs = System.nanoTime();
			}
		};
		
		timeline.play();
		timer.start();
	}
	
	private void newGame(double ballSpeed) {
		clicked = false;
		this.timer.stop();
		this.timeline.stop();
		this.init(ballSpeed);
	}
	
	private void resumeGame() {
		clicked = false;
		timeline.play();
		timer.start();
		ball.setCenterX(racket.getX() + racket.getHalfWidth());
		ball.setCenterY(racket.getY() - racket.getHeight() * 1.2);
		seconds = 0;
		secondsLabel.setText("" + seconds);
	}
	
	public void looseLife() {
		this.timer.stop();
		this.timeline.stop();
		this.livesLeft--;
		if (this.livesLeft != 0) {
			Button btn = new Button("Resume");
			btn.setLayoutX(width / 2 - 45);
			btn.setLayoutY(height / 2 - btn.getHeight());
			root.getChildren().add(btn);
			btn.setOnAction(e -> {
				this.resumeGame();
				root.getChildren().remove(btn);
			});
		} else {
			Button btn = new Button("Restart");
			btn.setLayoutX(width / 2 - 50);
			btn.setLayoutY(height / 2 - btn.getHeight() / 2);
			root.getChildren().add(btn);
			btn.setOnAction(e -> {
				this.newGame(ballSpeed);
			});
		}
                
        root.getChildren().removeAll(lives);
        lives.clear();
        fixLives();
        root.getChildren().addAll(lives);
        setButtonsVisible(true);
	}
	
	private void initBricks() {
		for (int i = 0; i < BRICKS_PER_ROW * ROW_OF_BRICKS; i++)
			bricks.add(new Brick(brickWidth, brickHeight));
		
		for (int i = 0; i < bricks.size(); i++) {
			int row = i / 15;
			int column = i % 15;
			bricks.get(i).setX(PADDING + column * brickWidth + column);
			bricks.get(i).setY(PADDING + row * brickHeight + row);
			
			switch (row) {
				case 0:
				case 1:
				case 2: bricks.get(i).setFill(Color.YELLOW); break;
				case 3: 
				case 4:
				case 5: bricks.get(i).setFill(Color.GREEN); break;
				case 6:
				case 7:
				case 8: bricks.get(i).setFill(Color.BLUE); break;
				case 9: bricks.get(i).setFill(Color.RED); break;
			}
			
		}
		
		int count = 0;
		while (count < (int)(bricks.size() * 0.2)) {
			int rng = (int) (Math.random() * bricks.size());
			if (bricks.get(rng).getX() > 0) {
				bricks.get(rng).setX(-100);
				count++;
			}
				
		}
	}
	
	private void fixLives() {
		livesLabel.setTextFill(Color.WHITE);
		livesLabel.setLayoutX(5);
		livesLabel.setLayoutY(1);
		
		for (int i = 0; i < livesLeft; i++) {
			lives.add(new Circle(5, Color.WHITE));
			lives.get(i).setCenterX(48 + i * 13);
			lives.get(i).setCenterY(10);
		}
		
	}
	
	public static double getWidth() {
		return width;
	}
	
	public static double getHeight() {
		return height;
	}
	
	private void setButtonsVisible(boolean value) {
		for (int i = 0; i < 3; i++) {
			levelButtons.get(i).setVisible(value);
		}
		newGameButton.setVisible(value);
	}

}