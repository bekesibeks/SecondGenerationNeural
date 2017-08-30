package mapcreator;

import static application.shared.Constants.MAP_HEIGHT;
import static application.shared.Constants.MAP_WIDTH;

import java.util.List;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			BorderPane root = new BorderPane();
			Scene scene = new Scene(root, 1200, 1000);

			MapAgent agent = new MapAgent(root, scene);
			Rectangle background = new Rectangle(MAP_WIDTH, MAP_HEIGHT);
			background.setFill(Color.BLUE);
			// URL url = Main.class.getResource("race-resized.jpg");
			// background.setFill(new ImagePattern(new Image(url.toString(),
			// MAP_WIDTH, MAP_HEIGHT, false, true)));
			List<Line> loadMap = MapLoader.loadMap("map-hard");

			root.getChildren().addAll(background);
			root.getChildren().addAll(loadMap);

			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
