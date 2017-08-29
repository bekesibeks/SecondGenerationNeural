package mapcreator;

import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.shape.Line;

public class MapAgent {

	MapCreator creator = new MapCreator(600, 600);

	public MapAgent(BorderPane root, Scene scene) {

		root.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {

				Line newPoint = creator.putPoint(new Point2D(event.getSceneX(), event.getSceneY()));
				if (newPoint != null) {
					root.getChildren().add(newPoint);
				}

			}
		});

		scene.setOnKeyReleased(e -> {

		});

		scene.setOnKeyReleased(e -> {
			if (e.getCode() == KeyCode.ENTER) {
				System.out.println("save");
				MapLoader.saveMap(creator.getLines(), "race_resized");
			}

			if (e.getCode() == KeyCode.SPACE) {
				System.out.println("new ");
				creator.startAgain();
			}
		});

	}

}
