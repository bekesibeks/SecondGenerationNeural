package mapcreator;
	
import static application.shared.Constants.MAP_HEIGHT;
import static application.shared.Constants.MAP_WIDTH;

import java.net.URL;
import java.util.List;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			BorderPane root = new BorderPane();
			Scene scene = new Scene(root,1200,1000);
			
			MapAgent agent = new MapAgent(root,scene);
			Rectangle background = new Rectangle(MAP_WIDTH, MAP_HEIGHT);
			URL url = Main.class.getResource("race_track.jpg");
			background.setFill(new ImagePattern(new Image(url.toString(), MAP_WIDTH, MAP_HEIGHT, false, true)));
//			List<Line> loadMap = MapLoader.loadMap("test");
			
			root.getChildren().addAll(background);
//			root.getChildren().addAll(loadMap);
			
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
