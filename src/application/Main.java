package application;

import static application.shared.Constants.MAP_HEIGHT;
import static application.shared.Constants.MAP_WIDTH;

import java.net.URL;

import application.map.Map;
import application.shared.Constants;
import application.shared.PropertiesForBinding;
import application.view.NeuralNetworkView;
import application.view.ViewManager;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

public class Main extends Application {
	private static int X = 1500;
	private static int Y = (int) MAP_HEIGHT;
	private ViewManager view;

	@Override
	public void start(Stage primaryStage) {
		try {
			BorderPane root = new BorderPane();
			Scene scene = new Scene(root, X, Y);
			root.setId("background");
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			
			NeuralNetworkView networkView = new NeuralNetworkView(); 
			
			Map map = new Map();
			Group mapGroup = map.getMapGroup();
			view = new ViewManager(map);
			// view.run();

			Group buttonGroup = new Group();
			buttonGroup.setTranslateX(Constants.MAP_WIDTH + 10);
			buttonGroup.setTranslateY(10);

			Button startButton = new Button(" start ");
			buttonGroup.getChildren().add(startButton);
			startButton.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent e) {
					view.run();
				}
			});
			

			Button restartButton = new Button(" restart ");
			buttonGroup.getChildren().add(restartButton);
			restartButton.setTranslateX(230);

			restartButton.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent e) {
					view.restart();
				}
			});
			
			Circle steer = new Circle(105);
			URL url = Main.class.getResource("steer.jpg");
			steer.setFill(new ImagePattern(new Image(url.toString(), 210, 210, false, true)));
			steer.setTranslateX(1300);
			steer.setTranslateY(590);
			steer.rotateProperty().bind(PropertiesForBinding.steerRotateProperty.multiply(10));

			addControlOptions(scene, map);

			root.getChildren().add(mapGroup);
			root.getChildren().add(steer);
			root.getChildren().add(buttonGroup);
			
			Group networkViewGroup = networkView.buildView();
			networkViewGroup.setTranslateX(Constants.MAP_WIDTH + 100);
			networkViewGroup.setTranslateY(150);
			root.getChildren().add(networkViewGroup);

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
			if (e.getCode() == KeyCode.LEFT) {
				map.leftPressed.set(true);
			}
			if (e.getCode() == KeyCode.RIGHT) {
				map.rightPressed.set(true);
			}
		});

		scene.setOnKeyReleased(e -> {
			if (e.getCode() == KeyCode.LEFT) {
				map.leftPressed.set(false);
			}
			if (e.getCode() == KeyCode.SPACE) {
				view.run();
			}
			if (e.getCode() == KeyCode.RIGHT) {
				map.rightPressed.set(false);
			}
		});
	}

	public static void main(String[] args) {
		launch(args);
	}
}
