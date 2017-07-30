package application.map;

import static application.ga.NetworkCrossover.crossoverNetworks;
import static application.shared.Constants.CAR_DEFAULT_DIRECTION;
import static application.shared.Constants.CAR_DEFAULT_LENGTH;
import static application.shared.Constants.CAR_DEFAULT_WIDTH;
import static application.shared.Constants.CAR_DEFAULT_X_COORDINATE;
import static application.shared.Constants.CAR_DEFAULT_Y_COORDINATE;
import static java.lang.Math.cos;
import static java.lang.Math.sin;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import application.car.Car;
import application.car.Radar;
import application.factories.TrackFactory;
import application.ga.Network;
import application.ga.NetworkCrossover;
import application.ga.Population;
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

	private static int index = 0;
	/*
	 * TODO -> remove this shit after neural network wired in
	 */
	public DoubleProperty leftLineDistance = new SimpleDoubleProperty();
	public DoubleProperty rightLineDistance = new SimpleDoubleProperty();
	public DoubleProperty rightFrontLineDistance = new SimpleDoubleProperty();
	public DoubleProperty leftFrontLineDistance = new SimpleDoubleProperty();
	public DoubleProperty frontLineDistance = new SimpleDoubleProperty();
	public BooleanProperty leftPressed = new SimpleBooleanProperty();
	public BooleanProperty rightPressed = new SimpleBooleanProperty();

	private final Car car;
	private final Radar radar;
	private final Population population;

	private Circle radarCentralPoint;

	private final Group mapGroup;
	private final List<Line> trackLines;

	public Map(Car car) {
		this.car = car;
		trackLines = new ArrayList<>();
		mapGroup = new Group();
		radar = new Radar();
		radarCentralPoint = new Circle(2);
		population = new Population();

		trackLines.addAll(TrackFactory.buildTrackLines(1000, 700, 50, 50));
		trackLines.addAll(TrackFactory.buildTrackLines(600, 300, 250, 250));

		Rectangle background = new Rectangle(1100, 800);
		background.setId("mapBackground");

		initMap();

		mapGroup.getChildren().add(background);
		mapGroup.getChildren().add(radar.getRadarView());
		mapGroup.getChildren().add(car.getCarView());
		mapGroup.getChildren().add(radarCentralPoint);
		mapGroup.getChildren().addAll(trackLines);

	}

	public void initMap() {

		car.getCarView().setTranslateX(CAR_DEFAULT_X_COORDINATE);
		car.getCarView().setTranslateY(CAR_DEFAULT_Y_COORDINATE);
		car.setDirection(CAR_DEFAULT_DIRECTION);

		car.getCarView().getTransforms().clear();
		radar.getRadarView().getTransforms().clear();
		radarCentralPoint.getTransforms().clear();

		radarCentralPoint.setFill(Color.RED);
		radarCentralPoint.setTranslateX(CAR_DEFAULT_X_COORDINATE);
		radarCentralPoint.setTranslateY(CAR_DEFAULT_Y_COORDINATE + CAR_DEFAULT_WIDTH / 2);

		radar.getRadarView().translateXProperty().bind(car.getCarView().translateXProperty());
		radar.getRadarView().translateYProperty().bind(car.getCarView().translateYProperty());
	}

	public boolean updateMap(int rotation) {
		calculateRadarValues();

		List<Double> inputs = new ArrayList<>();
		inputs.add(frontLineDistance.get());
		inputs.add(leftLineDistance.get());
		inputs.add(rightLineDistance.get());
		inputs.add(leftFrontLineDistance.get());
		inputs.add(rightFrontLineDistance.get());

		Network network = population.getNetworks().get(index % 4);
		network.setFitness(network.getFitness() + 5);
		List<Double> output = network.activateNetwork(inputs);
		rotation += output.get(0) * -5;
		rotation += output.get(1) * 5;
		System.out.println(network);

		/*
		 * Remove this shit after neural network wired in
		 */
		// if (leftPressed.getValue() == true) {
		// }
		//
		// if (rightPressed.getValue() == true) {
		// rotation = 5;
		// }

		rotateCar(rotation);
		moveCar();


		return isAlive();
	}

	public void calculateRadarValues() {
		calculateIntersect(radar.getFrontLine(), frontLineDistance);
		calculateIntersect(radar.getLeftLine(), leftLineDistance);
		calculateIntersect(radar.getLeftFrontLine(), leftFrontLineDistance);
		calculateIntersect(radar.getRightLine(), rightLineDistance);
		calculateIntersect(radar.getRightFrontLine(), rightFrontLineDistance);
	}

	private void rotateCar(int rotation) {
		Rotate turn = new Rotate(rotation);
		turn.setPivotX(CAR_DEFAULT_LENGTH / 2);
		turn.setPivotY(CAR_DEFAULT_WIDTH / 2);

		Rotate radarTurn = new Rotate(rotation);
		radarTurn.setPivotX(CAR_DEFAULT_LENGTH / 2);
		radarTurn.setPivotY(0);

		radarCentralPoint.getTransforms().add(radarTurn);
		radar.getRadarView().getTransforms().add(turn);
		car.getCarView().getTransforms().add(turn);

		car.setDirection(car.getDirection() + rotation);
	}

	private void moveCar() {
		car.getCarView().setTranslateX(
				car.getCarView().getTranslateX() + cos((Math.PI / 180) * car.getDirection()) * car.getSpeed());
		car.getCarView().setTranslateY(
				car.getCarView().getTranslateY() + sin((Math.PI / 180) * car.getDirection()) * car.getSpeed());

		radarCentralPoint.setTranslateX(
				radarCentralPoint.getTranslateX() + cos((Math.PI / 180) * car.getDirection()) * car.getSpeed());
		radarCentralPoint.setTranslateY(
				radarCentralPoint.getTranslateY() + sin((Math.PI / 180) * car.getDirection()) * car.getSpeed());
	}

	private boolean isAlive() {
		for (Line line : trackLines) {
			Shape intersect = Shape.intersect((Shape) car.getCarView().getChildren().get(0), line);
			if (intersect.getLayoutBounds().getMaxX() > -1) {
				if (index % 4 == 3) {
					List<Network> networks = population.getNetworks();
					Collections.sort(networks);
					System.out.println(networks.get(0).getFitness()+" and "+networks.get(3).getFitness());
					Network child2 = crossoverNetworks(networks.get(0), networks.get(1));
					Network child3 = crossoverNetworks(networks.get(0), networks.get(2));
					Network child4 = crossoverNetworks(networks.get(0), networks.get(3));
					Network child1 = crossoverNetworks(networks.get(1), networks.get(2));
					population.getNetworks().clear();
					population.getNetworks().add(child1);
					population.getNetworks().add(child2);
					population.getNetworks().add(child3);
					population.getNetworks().add(child4);
					population.getNetworks().forEach(net->net.setFitness(0));
				}
				index++;
				return false;
			}
		}

		return true;
	}

	public double calculateIntersect(Line radarLine, DoubleProperty propertyToUpdate) {
		double minDistance = calculateDistance(radarLine);
		for (Line line : trackLines) {
			Shape intersectPoint = Shape.intersect(line, radarLine);
			if (intersectPoint.getLayoutBounds().getMaxX() > -1) {
				Shape radarCentralCoordinate = Shape.intersect(radarLine, radarCentralPoint);

				double currentDistance = calculateDistance(intersectPoint.getLayoutBounds().getMaxX(),
						intersectPoint.getLayoutBounds().getMaxY(), radarCentralCoordinate.getLayoutBounds().getMaxX(),
						radarCentralCoordinate.getLayoutBounds().getMaxY());

				if (currentDistance < minDistance) {
					minDistance = currentDistance;
				}
			}
		}
		propertyToUpdate.set(minDistance);
		return minDistance;
	}

	private double calculateDistance(Line line) {
		return calculateDistance(line.getStartX(), line.getStartY(), line.getEndX(), line.getEndY());
	}

	private double calculateDistance(double fromX, double fromY, double toX, double toY) {
		double distanceX = Math.abs(fromX - toX);
		double distanceY = Math.abs(fromY - toY);
		double currentDistance = Math.sqrt(Math.pow(distanceX, 2) + Math.pow(distanceY, 2));
		return currentDistance;
	}

	public Group getMapGroup() {
		return mapGroup;
	}

}