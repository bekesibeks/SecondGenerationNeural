package application;


import application.car.Car;
import application.map.Map;
import application.view.ViewManager;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
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

			Map map = new Map();
			Group mapGroup = map.getMapGroup();

			ViewManager view = new ViewManager(map);
			view.run();
			
			
			addControlOptions(scene, map);
			
			Text textFront = new Text("Front distance : ");
			textFront.textProperty().bind(map.frontLineDistance.asString().concat(": front"));
			Text textRight = new Text("Right distance : ");
			textRight.setTranslateY(40);
			textRight.textProperty().bind(map.rightLineDistance.asString().concat(": right"));
			Text textLeft = new Text("Left distance : ");
			textLeft.setTranslateY(80);
			textLeft.textProperty().bind(map.leftLineDistance.asString().concat(": left"));
			Text textFrontLeft = new Text("Front left distance : ");
			textFrontLeft.setTranslateY(120);
			textFrontLeft.textProperty().bind(map.leftFrontLineDistance.asString().concat(": left front"));
			Text textFrontRight = new Text("Front right  distance : ");
			textFrontRight.setTranslateY(160);
			textFrontRight.textProperty().bind(map.rightFrontLineDistance.asString().concat(": right front"));
			
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
