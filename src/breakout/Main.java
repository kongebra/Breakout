package breakout;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

	
private Game game;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		game = new Game("Breakout", 800, 600, primaryStage);
		game.init(7);
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}
