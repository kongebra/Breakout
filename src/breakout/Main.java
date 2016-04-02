/**
 * Gruppe; Svein Are Danielsen, Eirik Stople, Vivi Nygaard
 * ITE1900 Programmering 1, vår 2016
 * Obligatorisk innlevering 3; Breakout
 */

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
