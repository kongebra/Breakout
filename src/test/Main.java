package test;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
	
	
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		Game game = new Game(800, 600, 800, stage);
		game.initialize(stage);
	}
	
}
