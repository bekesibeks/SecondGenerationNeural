package application;

import static application.shared.Constants.MAP_HEIGHT;
import static application.shared.Constants.MAP_WIDTH;

import application.map.Map;
import application.shared.Constants;
import application.shared.PropertiesForBinding;
import application.view.ViewManager;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Main extends Application {
	private static int X = (int) MAP_WIDTH;
	private static int Y = (int) MAP_HEIGHT;
	private ViewManager view;

	@Override
	public void start(Stage primaryStage) {
		try {
			BorderPane root = new BorderPane();
			Scene scene = new Scene(root, X, Y);
			root.setId("background");
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

			Map map = new Map();
			Group mapGroup = map.getMapGroup();
			view = new ViewManager(map);

			Group buttonGroup = new Group();
			buttonGroup.setTranslateX(510);
			buttonGroup.setTranslateY(230);

			Button startButton = new Button("start");
			buttonGroup.getChildren().add(startButton);

			startButton.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent e) {
					Constants.SETTINGS_LOAD_PRETRAINED_NETWORK = false;
					view.initTimeline();
					view.run();
				}
			});

			Button stopButton = new Button("stop ");
			buttonGroup.getChildren().add(stopButton);
			stopButton.setTranslateX(130);

			stopButton.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent e) {
					view.stop();
					PropertiesForBinding.populationProperty.set(0);
				}
			});

			Button startPretrained = new Button("start pretrained");
			buttonGroup.getChildren().add(startPretrained);
			startPretrained.setTranslateY(60);

			startPretrained.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent e) {
					Constants.SETTINGS_LOAD_PRETRAINED_NETWORK = true;
					// view.restart();
					view.initTimeline();
					view.run();
				}
			});

			addControlOptions(scene, map);
			root.getChildren().add(mapGroup);
			root.getChildren().add(buttonGroup);

			Text text = new Text("Pop : ");
			text.setTranslateX(310);
			text.setTranslateY(300);
			text.setFill(Color.WHITE);
			text.setId("text");
			text.textProperty().bind(PropertiesForBinding.populationProperty.add(1).asString().concat(". generation "));
			root.getChildren().add(text);

			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * For test phase only
	 */
	private void addControlOptions(Scene scene, Map map) {
		scene.setOnKeyPressed(e -> {
			// if (e.getCode() == KeyCode.LEFT) {
			// map.leftPressed.set(true);
			// }
			// if (e.getCode() == KeyCode.RIGHT) {
			// map.rightPressed.set(true);
			// }
		});

		scene.setOnKeyReleased(e -> {
			// if (e.getCode() == KeyCode.LEFT) {
			// map.leftPressed.set(false);
			// }
			if (e.getCode() == KeyCode.SPACE) {
				view.run();
			}
			// if (e.getCode() == KeyCode.RIGHT) {
			// map.rightPressed.set(false);
			// }
		});
	}

	public static void main(String[] args) {
		launch(args);
	}
}
