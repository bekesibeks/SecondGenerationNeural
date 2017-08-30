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
import javafx.scene.effect.BlendMode;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
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

			Button stopButton = new Button("stop");
			buttonGroup.getChildren().add(stopButton);
			stopButton.setTranslateX(130);

			stopButton.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent e) {
					view.stop();
					PropertiesForBinding.populationProperty.set(0);
				}
			});

			Button startPretrained = new Button("continue");
			buttonGroup.getChildren().add(startPretrained);
			startPretrained.setTranslateY(60);
			startPretrained.setTranslateX(40);

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

			Rectangle colors = new Rectangle(MAP_WIDTH, MAP_HEIGHT,
					new LinearGradient(0f, 1f, 1f, 0f, true, CycleMethod.NO_CYCLE,
							new Stop[] { new Stop(0, Color.web("#f8bd55")), new Stop(0.14, Color.web("#c0fe56")),
									new Stop(0.28, Color.web("#5dfbc1")), new Stop(0.43, Color.web("#64c2f8")),
									new Stop(0.57, Color.web("#be4af7")), new Stop(0.71, Color.web("#ed5fc2")),
									new Stop(0.85, Color.web("#ef504c")), new Stop(1, Color.web("#f2660f")), }));

			colors.setBlendMode(BlendMode.OVERLAY);

			Text text = new Text();
			text.setTranslateX(310);
			text.setTranslateY(300);
			text.setFill(Color.WHITE);
			text.setId("text");
			text.setOpacity(0.4);
			text.textProperty().bind(PropertiesForBinding.populationProperty.add(1).asString().concat(". generation "));

			Text text2 = new Text();
			text2.setTranslateX(310);
			text2.setTranslateY(330);
			text2.setFill(Color.WHITE);
			text2.setId("text");
			text2.setOpacity(0.4);
			text2.textProperty()
					.bind(PropertiesForBinding.topFitnessProperty.asString().concat("  fitness "));

			Group effectedGroup = new Group(new Rectangle(MAP_WIDTH, MAP_HEIGHT, Color.BLACK), new Group(mapGroup),
					text2, text, colors);
			root.getChildren().add(effectedGroup);
			root.getChildren().add(buttonGroup);

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
