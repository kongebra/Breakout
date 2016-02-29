package breakout;

import java.util.ArrayList;

import javafx.animation.AnimationTimer;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
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
	private boolean clicked = false;
	
	public Game(String title, double width, double height) {
		this.title = title;
		Game.width = width;
		Game.height = height;
		
		brickWidth = (width - PADDING * 2) / BRICKS_PER_ROW;
		brickHeight = brickWidth / 2.5;
	}
	
	private Pane root;
	private Scene scene;
	
	private Racket racket;
	private Ball ball;
	private ArrayList<Brick> bricks;
	
	private Timeline timeline;
	private AnimationTimer timer;
	
	private Button newGameButton;
	
	public void init(Stage stage) {
		
		root = new Pane();
		scene = new Scene(root, width, height);
		
		// Racket
		racket = new Racket(100, 10);
		racket.setX((width / 2) - racket.getHalfWidth());
		racket.setY(height - racket.getHeight() * 5);
		
		// Ball
		ball = new Ball(10);
		ball.setCenterX(racket.getX() + racket.getHalfWidth());
		ball.setCenterY(racket.getY() - racket.getHeight() * 2);
		
		// Bricks
		bricks = new ArrayList<Brick>();
		initBricks();
		
		// Buttons
		newGameButton = new Button("New Game");
		newGameButton.setLayoutY(height - 30);
		newGameButton.setLayoutX(5);
		newGameButton.setOnAction(e -> {
			clicked = false;
			timeline.stop();
			timer.stop();
			this.init(stage);
		});
		
		// Add too ROOT
		root.getChildren().add(racket);
		root.getChildren().add(ball);
		root.getChildren().addAll(bricks);
		root.getChildren().add(newGameButton);
		
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
				if (clicked) {
					ball.move();
				}
				
				if (ball.intersects(racket.getBoundsInLocal())) {
					racket.bounceBall(ball);
				}
				
//				for (Brick brick : bricks) {
//					if (brick.collision(ball)) {
//						bricks.remove(brick);
//					}
//					break;
//				}
			}
		};
		
		timeline.play();
		timer.start();
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
	}
	
	public static double getWidth() {
		return width;
	}
	
	public static double getHeight() {
		return height;
	}
}
