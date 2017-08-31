package application;

import static application.shared.Constants.MAP_HEIGHT;
import static application.shared.Constants.MAP_WIDTH;
import static application.shared.Constants.SETTINGS_LOAD_PRETRAINED_NETWORK;

import application.map.Map;
import application.shared.PropertiesForBinding;
import application.view.ViewManager;
import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.BlendMode;
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

			Group buttonGroup = createButtons();

			Rectangle colors = new Rectangle(MAP_WIDTH, MAP_HEIGHT,
					new LinearGradient(0f, 1f, 1f, 0f, true, CycleMethod.NO_CYCLE,
							new Stop[] { new Stop(0, Color.web("#f8bd55")), new Stop(0.14, Color.web("#c0fe56")),
									new Stop(0.28, Color.web("#5dfbc1")), new Stop(0.43, Color.web("#64c2f8")),
									new Stop(0.57, Color.web("#be4af7")), new Stop(0.71, Color.web("#ed5fc2")),
									new Stop(0.85, Color.web("#ef504c")), new Stop(1, Color.web("#f2660f")), }));

			colors.setBlendMode(BlendMode.OVERLAY);

			Text text = createText(310, 260);
			text.textProperty()
					.bind(Bindings.format("generation : %s#", PropertiesForBinding.populationProperty.add(1)));

			Text text2 = createText(310, 290);
			text2.textProperty().bind(Bindings.format("   fitness : %s", PropertiesForBinding.topFitnessProperty));

			Group effectedGroup = new Group(new Rectangle(MAP_WIDTH, MAP_HEIGHT, Color.rgb(10, 10, 10)),
					new Group(mapGroup), text2, text, colors);

			root.getChildren().add(effectedGroup);
			root.getChildren().add(buttonGroup);

			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private Group createButtons() {
		Group buttonGroup = new Group();
		buttonGroup.setTranslateX(510);
		buttonGroup.setTranslateY(230);

		Button startButton = new Button("  start  ");
		Button stopButton = new Button("stop");
		Button startPretrained = new Button(" continue");
		Button triggerNextMap = new Button("next");

		buttonGroup.getChildren().add(startButton);
		buttonGroup.getChildren().add(stopButton);
		buttonGroup.getChildren().add(startPretrained);
		buttonGroup.getChildren().add(triggerNextMap);

		stopButton.setTranslateX(150);
		startPretrained.setTranslateY(40);
		triggerNextMap.setTranslateX(150);
		triggerNextMap.setTranslateY(40);

		startButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				SETTINGS_LOAD_PRETRAINED_NETWORK = false;
				view.initTimeline();
				view.run();
			}
		});

		stopButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				view.stop();
				PropertiesForBinding.populationProperty.set(0);
			}
		});

		startPretrained.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				SETTINGS_LOAD_PRETRAINED_NETWORK = true;
				view.initTimeline();
				view.run();
			}
		});

		return buttonGroup;
	}

	private Text createText(int x, int y) {
		Text text = new Text();
		text.setTranslateX(x);
		text.setTranslateY(y);
		text.setFill(Color.WHITE);
		text.setId("text");
		text.setOpacity(0.4);
		return text;
	}

	public static void main(String[] args) {
		launch(args);
	}
}
