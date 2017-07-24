package application.map;

import static application.shared.Constants.CAR_DEFAULT_LENGTH;
import static application.shared.Constants.CAR_DEFAULT_WIDTH;
import static application.shared.Constants.CAR_DEFAULT_X_COORDINATE;
import static application.shared.Constants.CAR_DEFAULT_Y_COORDINATE;
import static java.lang.Math.cos;
import static java.lang.Math.sin;

import java.util.ArrayList;
import java.util.List;

import application.car.CarModel;
import application.factories.RadarFactory;
import application.factories.TrackFactory;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.transform.Rotate;

public class Map {

	/*
	 * TODO -> remove this shit after neural network wired in
	 */
	public DoubleProperty leftLineDistance = new SimpleDoubleProperty();
	public DoubleProperty rightLineDistance = new SimpleDoubleProperty();
	public DoubleProperty rightFrontLineDistance = new SimpleDoubleProperty();
	public DoubleProperty leftFrontLineDistance = new SimpleDoubleProperty();
	public DoubleProperty frontLineDistance = new SimpleDoubleProperty();

	private final CarModel car;
	private final Group mapGroup;

	private final List<Line> trackLines;
	private final RadarFactory radarModel;

	public BooleanProperty leftPressed = new SimpleBooleanProperty();
	public BooleanProperty rightPressed = new SimpleBooleanProperty();

	private Circle radarCentralPoint;

	public Map(CarModel car) {
		this.car = car;
		trackLines = new ArrayList<>();
		mapGroup = new Group();
		radarModel = new RadarFactory();

		trackLines.addAll(TrackFactory.buildTrackLines(1000, 700, 50, 50));
		trackLines.addAll(TrackFactory.buildTrackLines(700, 400, 200, 200));

		car.getCarView().setTranslateX(800);
		car.getCarView().setTranslateY(100);

		Rectangle background = new Rectangle(1100, 800);
		background.setId("mapBackground");

		radarModel.getRadarView().translateXProperty().bind(car.getCarView().translateXProperty());
		radarModel.getRadarView().translateYProperty().bind(car.getCarView().translateYProperty());

		radarCentralPoint = new Circle(1);
		radarCentralPoint.setFill(Color.RED);
		radarCentralPoint.setTranslateX(CAR_DEFAULT_X_COORDINATE);
		radarCentralPoint.setTranslateY(CAR_DEFAULT_Y_COORDINATE + CAR_DEFAULT_WIDTH / 2);

		mapGroup.getChildren().add(background);
		mapGroup.getChildren().add(radarModel.getRadarView());
		mapGroup.getChildren().add(car.getCarView());
		mapGroup.getChildren().addAll(trackLines);
		mapGroup.getChildren().add(radarCentralPoint);

	}

	public boolean updateMap(int rotation) {

		/*
		 * Remove this shit after neural network wired in
		 */
		if (leftPressed.getValue() == true) {
			rotation = -4;
		}

		if (rightPressed.getValue() == true) {
			rotation = 4;
		}

		Rotate turn = new Rotate(rotation);
		turn.setPivotX(CAR_DEFAULT_LENGTH / 2);
		turn.setPivotY(CAR_DEFAULT_WIDTH / 2);

		Rotate radarTurn = new Rotate(rotation);
		radarTurn.setPivotX(CAR_DEFAULT_LENGTH / 2);
		radarTurn.setPivotY(0);

		radarCentralPoint.getTransforms().add(radarTurn);
		radarModel.getRadarView().getTransforms().add(turn);
		car.getCarView().getTransforms().add(turn);

		car.setDirection(car.getDirection() + rotation);

		car.getCarView().setTranslateX(car.getCarView().getTranslateX() + cos((Math.PI / 180) * car.getDirection()) * car.getSpeed());
		car.getCarView().setTranslateY(car.getCarView().getTranslateY() + sin((Math.PI / 180) * car.getDirection()) * car.getSpeed());

		radarCentralPoint.setTranslateX(
				radarCentralPoint.getTranslateX() + cos((Math.PI / 180) * car.getDirection()) * car.getSpeed());
		radarCentralPoint.setTranslateY(
				radarCentralPoint.getTranslateY() + sin((Math.PI / 180) * car.getDirection()) * car.getSpeed());

		calculateIntersect(radarModel.getFrontLine(), frontLineDistance);
		calculateIntersect(radarModel.getLeftLine(), leftLineDistance);
		calculateIntersect(radarModel.getLeftFrontLine(), leftFrontLineDistance);
		calculateIntersect(radarModel.getRightLine(), rightLineDistance);
		calculateIntersect(radarModel.getRightFrontLine(), rightFrontLineDistance);

		return isAlive();
	}

	private boolean isAlive() {
		for (Line line : trackLines) {
			Shape intersect = Shape.intersect((Shape) car.getCarView().getChildren().get(0), line);
			if (intersect.getLayoutBounds().getMaxX() > -1) {
				return false;
			}
		}

		return true;
	}

	public double calculateIntersect(Line radarLine, DoubleProperty propertyToUpdate) {
		double min = 300; // todo-line max lenght
		for (Line line : trackLines) {
			Shape intersectPoint = Shape.intersect(line, radarLine);
			if (intersectPoint.getLayoutBounds().getMaxX() > -1) {
				double distanceX = Math
						.abs(intersectPoint.getLayoutBounds().getMaxX() - radarCentralPoint.getTranslateX());
				double distanceY = Math
						.abs(intersectPoint.getLayoutBounds().getMaxY() - radarCentralPoint.getTranslateY());
				double distance = Math.sqrt(Math.pow(distanceX, 2) + Math.pow(distanceY, 2));

				if (distance < min) {
					min = distance;
				}
			}
		}
		propertyToUpdate.set(min);
		return min;
	}

	public Group getMapGroup() {
		return mapGroup;
	}

}
