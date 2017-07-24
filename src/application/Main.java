package application;

import application.car.CarModel;
import application.map.Map;
import application.view.ViewManager;
import javafx.application.Application;
import javafx.beans.property.DoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.InputEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;

public class Main extends Application {
	private static int X = 1500;
	private static int Y = 900;

	@Override
	public void start(Stage primaryStage) {
		try {
			BorderPane root = new BorderPane();
			Scene scene = new Scene(root, X, Y);
			root.setId("background");
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

			CarModel car = new CarModel();
			Map map = new Map(car);
			Group mapGroup = map.getMapGroup();

			mapGroup.setTranslateX(0);
			mapGroup.setTranslateY(0);

			ViewManager view = new ViewManager(map);
			view.run();
			
			addControlOptions(scene, map);
			
			Text textFront = new Text("Front distance : ");
			Text textRight = new Text("Right distance : ");
			textRight.setTranslateY(40);
			Text textLeft = new Text("Left distance : ");
			textLeft.setTranslateY(80);
			Text textFrontLeft = new Text("Front left distance : ");
			textFrontLeft.setTranslateY(120);
			Text textFrontRight = new Text("Front right  distance : ");
			textFrontRight.setTranslateY(160);
		
			Group textGroup = new Group();
			textGroup.getChildren().add(textFront);
			textGroup.getChildren().add(textRight);
			textGroup.getChildren().add(textLeft);
			textGroup.getChildren().add(textFrontLeft);
			textGroup.getChildren().add(textFrontRight);
			textGroup.setTranslateX(1200);
			textGroup.setTranslateY(200);
			
			root.getChildren().add(mapGroup);
			root.getChildren().add(textGroup);

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
		    if (e.getCode() == KeyCode.RIGHT) {
		    	map.rightPressed.set(false);
		    }
		});
	}

	public static void main(String[] args) {
		launch(args);
	}
}
