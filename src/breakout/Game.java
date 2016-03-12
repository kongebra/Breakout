package breakout;

import java.util.ArrayList;
import java.util.Date;

import javafx.animation.AnimationTimer;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Game {

	private final int BRICKS_PER_ROW = 15;
	private final int ROW_OF_BRICKS = 10;
	private final int PADDING = 50;
	
	private static double width;
	private static double height;
	private double brickWidth;
	private double brickHeight;
	private String title;
	private boolean clicked;
	
	private int livesLeft = 3;
	
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
	private ArrayList<Brick> bricks;
	
	private Timeline timeline;
	private AnimationTimer timer;
	
	private Button newGameButton;
	
	private ArrayList<Circle> lives;
	private Label livesLabel;
	
	private int score;
	private Label scoreLabel;
	
	private int fps;
	private Label fpsLabel;
	private Label engineMicroSecondsLabel;
	private Label drawMsLabel;
	private int lastFpsTimer;
	private long lastEngineStartNs;
	private long lastEngineEnd;
	private long lastEngineEndNs;
        
	public void init() {
		
		// Settings
		clicked = false;
		score = 0;
		
		// Scene settings
		root = new Pane();
		scene = new Scene(root, width, height);
		
		// Racket
		racket = new Racket(100, 10);
		racket.setX((width / 2) - racket.getHalfWidth());
		racket.setY(height - racket.getHeight() * 5);
		
		// Ball
		ball = new Ball(10);
		ball.setCenterX(racket.getX() + racket.getHalfWidth());
		ball.setCenterY(racket.getY() - racket.getHeight() * 1.2);
		
		// Bricks
		bricks = new ArrayList<Brick>();
		initBricks();
		Brick.setFastCollDetArea((int)brickWidth, (int)brickHeight, ball);
		
		// Buttons
		newGameButton = new Button("New Game");
		newGameButton.setLayoutY(3);
		newGameButton.setLayoutX(width / 2 - 50);
		newGameButton.setOnAction(e -> {
			this.newGame();
		});
		
		// Lives
		lives = new ArrayList<Circle>();
		livesLabel = new Label("Lives:");
		fixLives();
		
		// Score
		scoreLabel = new Label("Score: 0");
		scoreLabel.setTextFill(Color.WHITE);
		scoreLabel.setFont(new Font(24));
		scoreLabel.setLayoutX(5);
		
		// Frames pr second
		fpsLabel = new Label("FPS: 0");
		fpsLabel.setTextFill(Color.WHITE);
		fpsLabel.setFont(new Font(24));
		fpsLabel.setLayoutX(200);
		fps = 0;
		lastFpsTimer = 0;
		lastEngineStartNs = 0;
		lastEngineEnd = 0;
		lastEngineEndNs = 0;
		
		// Engine ms
		engineMicroSecondsLabel = new Label("Engine Âµs: 0");
		engineMicroSecondsLabel.setTextFill(Color.WHITE);
		engineMicroSecondsLabel.setFont(new Font(24));
		engineMicroSecondsLabel.setLayoutX(400);
		
		// Draw ms
		drawMsLabel = new Label("Draw ms: 0");
		drawMsLabel.setTextFill(Color.WHITE);
		drawMsLabel.setFont(new Font(24));
		drawMsLabel.setLayoutX(600);
                
                
                

		// Add too ROOT
		root.getChildren().add(racket);
		root.getChildren().add(ball);
		root.getChildren().addAll(bricks);
		root.getChildren().add(newGameButton);
		root.getChildren().add(livesLabel);
		root.getChildren().add(scoreLabel);
		root.getChildren().add(fpsLabel);
		root.getChildren().add(engineMicroSecondsLabel);
		root.getChildren().add(drawMsLabel);
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
			clicked = true;
		});
		
		// Styles
		root.setStyle("-fx-background-color: #000000;");
		
		// Stage setup
		stage.setScene(scene);
		stage.setTitle(title);
		stage.setResizable(false);
		stage.show();
		
		timeline = new Timeline();
		timeline.setCycleCount(Timeline.INDEFINITE);
		timeline.setAutoReverse(true);
		
		// Graphical update/logic
		timer = new AnimationTimer() {
			@Override
			public void handle(long now) {
				fps++;
				long mainLoopStart = System.currentTimeMillis();
				int fpsTimer = (int)(System.currentTimeMillis() / 1000L);
				if (fpsTimer != lastFpsTimer)
				{
					fpsLabel.setText("FPS: " + fps);
					fps = 0;
					lastFpsTimer = fpsTimer;
					engineMicroSecondsLabel.setText("Engine Âµs: " + ((lastEngineEndNs - lastEngineStartNs) / 1000L));
					drawMsLabel.setText("Draw ms: " + (mainLoopStart - lastEngineEnd));
				}
				lastEngineStartNs = System.nanoTime();
				if (clicked) {
					ball.move();
				}
				
				if (ball.intersects(racket.getBoundsInLocal())) {
					racket.bounceBall(ball);
                                        ball.setRadius(ball.getRadius());
                                      
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
						break;
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
				lastEngineEnd = System.currentTimeMillis();
				lastEngineEndNs = System.nanoTime();
			}
		};
		
		timeline.play();
		timer.start();
	}
	
	private void newGame() {
		clicked = false;
		this.timer.stop();
		this.timeline.stop();
		this.init();
	}
	
	private void resumeGame() {
		clicked = false;
		timeline.play();
		timer.start();
		ball.setCenterX(racket.getX() + racket.getHalfWidth());
		ball.setCenterY(racket.getY() - racket.getHeight() * 1.2);
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
				this.livesLeft = 3;
				this.newGame();
			});
		}
                
                root.getChildren().removeAll(lives);
                lives.clear();
                fixLives();
                root.getChildren().addAll(lives);
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
		livesLabel.setLayoutY(height - 19);
		
		for (int i = 0; i < livesLeft; i++) {
			lives.add(new Circle(5, Color.WHITE));
			lives.get(i).setCenterX(48 + i * 13);
			lives.get(i).setCenterY(height - 10);
		}
		
	}
	
	public static double getWidth() {
		return width;
	}
	
	public static double getHeight() {
		return height;
	}
}