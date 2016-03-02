package test;

import java.util.ArrayList;

import javafx.animation.AnimationTimer;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Game {
	
	private double width;
	private double height;
	private double pillarWidth;
	
	public Game(double width, double height, int amount, Stage stage) {
		this.width = width;
		this.height = height;
		
		pillars = new ArrayList<>();
		pillarWidth = width / amount;
		
		for (int i = 0; i < amount; i++)
			pillars.add(new Pillar(pillarWidth, height + 15));
	}
	
	private Pane root;
	private Scene scene;
	
	private ArrayList<Pillar> pillars;
	
	private Timeline timeline;
	private AnimationTimer timer;
	
	public void initialize(Stage stage) {
		root = new Pane();
		scene = new Scene(root, width, height);
		
		// Pillars
		for (int i = 0; i < pillars.size(); i++) {
			pillars.get(i).setX(i * pillarWidth);
			pillars.get(i).setY(height - pillars.get(i).getHeight() + 15);
		}
		
		System.out.println(pillars);
		
		
		root.getChildren().addAll(pillars);
		
		stage.setScene(scene);
		stage.setTitle("Test");
		stage.setResizable(false);
		stage.show();
		
		timeline = new Timeline();
		timeline.setCycleCount(Timeline.INDEFINITE);
		timeline.setAutoReverse(true);
		
		timer = new AnimationTimer() {
			@Override
			public void handle(long now) {
				
				for (Pillar pillar : pillars) {
					
					pillar.move();
					
					if (pillar.getY() > height) {
						pillar.reverse();
					} else if (pillar.getY() < height - pillar.getHeight() + 10) {
						pillar.reverse();
					}
					
				}
				
			}
		};
		
		timeline.play();
		timer.start();
		
	}
	
	public double getWidth() {
		return width;
	}
	
	public double getHeight() {
		return height;
	}
	
}
