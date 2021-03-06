package application.map;

import static application.shared.Constants.CAR_DEFAULT_DIRECTION;
import static application.shared.Constants.CAR_DEFAULT_LENGTH;
import static application.shared.Constants.CAR_DEFAULT_SPEED;
import static application.shared.Constants.CAR_DEFAULT_WIDTH;
import static application.shared.Constants.CAR_DEFAULT_X_COORDINATE;
import static application.shared.Constants.CAR_DEFAULT_Y_COORDINATE;
import static application.shared.Constants.MAPS;
import static application.shared.Constants.MAP_INDEX;
import static java.lang.Math.cos;
import static java.lang.Math.sin;

import java.util.ArrayList;
import java.util.List;

import application.car.Car;
import application.car.Radar;
import application.shared.Constants;
import javafx.scene.Group;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Shape;
import javafx.scene.transform.Rotate;
import mapcreator.MapLoader;

public class Map {
	private final List<Car> cars;
	private final List<Radar> radars;
	private final List<Circle> radarCentralPoints;

	private final Group mapGroup;

	private final List<Line> mapLines;
	private final Group mapLinesGroup;

	public Map() {
		mapLines = new ArrayList<>();
		cars = new ArrayList<>();
		radars = new ArrayList<>();
		radarCentralPoints = new ArrayList<>();
		mapGroup = new Group();
		mapLinesGroup = new Group();

		loadTrack();

		mapGroup.getChildren().addAll(mapLinesGroup);

		for (int i = 0; i < Constants.NETWORK_POPULATION_SIZE; i++) {
			Car car = new Car();
			cars.add(car);

			Radar radar = new Radar();
			radars.add(radar);

			Circle radarCentralPoint = new Circle(1);
			radarCentralPoints.add(radarCentralPoint);

			mapGroup.getChildren().add(radar.getRadarView());
			mapGroup.getChildren().add(radarCentralPoint);
			mapGroup.getChildren().add(car.getCarView());
		}

		initMap();
	}

	public void initMap() {
		for (int i = 0; i < Constants.NETWORK_POPULATION_SIZE; i++) {
			Car car = cars.get(i);
			Radar radar = radars.get(i);
			Circle radarCentralPoint = radarCentralPoints.get(i);

			car.getCarView().setTranslateX(CAR_DEFAULT_X_COORDINATE);
			car.getCarView().setTranslateY(CAR_DEFAULT_Y_COORDINATE);
			car.setDirection(CAR_DEFAULT_DIRECTION);

			car.getCarView().getTransforms().clear();
			radar.getRadarView().getTransforms().clear();
			radarCentralPoint.getTransforms().clear();

			radarCentralPoint.setFill(Color.TRANSPARENT);
			radarCentralPoint.setTranslateX(CAR_DEFAULT_X_COORDINATE);
			radarCentralPoint.setTranslateY(CAR_DEFAULT_Y_COORDINATE + CAR_DEFAULT_WIDTH / 2);

			radar.getRadarView().translateXProperty().bind(car.getCarView().translateXProperty());
			radar.getRadarView().translateYProperty().bind(car.getCarView().translateYProperty());
		}
	}

	public MapData getMapData(int index) {
		MapData currentMapData = calculateRadarValues(index);
		return currentMapData;
	}

	public void updateMap(double rotation, int carIndex) {
		rotateCar(rotation, carIndex);
		moveCar(rotation, carIndex);
	}

	public MapData calculateRadarValues(int index) {
		MapData mapData = new MapData();
		mapData.setAlive(true);
		mapData.setFrontLineDistance(calculateIntersect(radars.get(index).getFrontLine(), index));
		mapData.setLeftLineDistance(calculateIntersect(radars.get(index).getLeftLine(), index));
		mapData.setLeftFrontLineDistance(calculateIntersect(radars.get(index).getLeftFrontLine(), index));
		mapData.setRightLineDistance(calculateIntersect(radars.get(index).getRightLine(), index));
		mapData.setRightFrontLineDistance(calculateIntersect(radars.get(index).getRightFrontLine(), index));

		if (mapData.getFrontLineDistance() < CAR_DEFAULT_SPEED || mapData.getLeftFrontLineDistance() < CAR_DEFAULT_SPEED
				|| mapData.getRightFrontLineDistance() < CAR_DEFAULT_SPEED
				|| mapData.getRightLineDistance() < CAR_DEFAULT_SPEED
				|| mapData.getLeftLineDistance() < CAR_DEFAULT_SPEED) {
			mapData.setAlive(false);
		}

		return mapData;
	}

	public void loadTrack() {
		List<Line> loadMap = MapLoader.loadMap(MAPS[MAP_INDEX % MAPS.length]);
		GaussianBlur gaussianBlur = new GaussianBlur();
		gaussianBlur.setRadius(3);
		loadMap.forEach(line -> {
			line.setStroke(Color.YELLOW);
			line.setEffect(gaussianBlur);
			line.setOpacity(0.9);
		});
		mapLines.clear();
		mapLines.addAll(loadMap);
		mapLinesGroup.getChildren().clear();
		mapLinesGroup.getChildren().addAll(mapLines);
	}

	private void rotateCar(double rotation, int carIndex) {
		Rotate turn = new Rotate(rotation);
		turn.setPivotX(CAR_DEFAULT_LENGTH / 2);
		turn.setPivotY(CAR_DEFAULT_WIDTH / 2);

		Rotate radarTurn = new Rotate(rotation);
		radarTurn.setPivotX(CAR_DEFAULT_LENGTH / 2);
		radarTurn.setPivotY(0);

		radarCentralPoints.get(carIndex).getTransforms().add(radarTurn);
		radars.get(carIndex).getRadarView().getTransforms().add(turn);
		cars.get(carIndex).getCarView().getTransforms().add(turn);

		cars.get(carIndex).setDirection(cars.get(carIndex).getDirection() + rotation);
	}

	private void moveCar(double rotation, int carIndex) {
		double speedLostCausedByRotation = Math.abs(rotation) / 3;

		cars.get(carIndex).getCarView()
				.setTranslateX(cars.get(carIndex).getCarView().getTranslateX()
						+ cos((Math.PI / 180) * cars.get(carIndex).getDirection())
								* (cars.get(carIndex).getSpeed() - speedLostCausedByRotation));
		cars.get(carIndex).getCarView()
				.setTranslateY(cars.get(carIndex).getCarView().getTranslateY()
						+ sin((Math.PI / 180) * cars.get(carIndex).getDirection())
								* (cars.get(carIndex).getSpeed() - speedLostCausedByRotation));

		radarCentralPoints.get(carIndex)
				.setTranslateX(radarCentralPoints.get(carIndex).getTranslateX()
						+ cos((Math.PI / 180) * cars.get(carIndex).getDirection())
								* (cars.get(carIndex).getSpeed() - speedLostCausedByRotation));
		radarCentralPoints.get(carIndex)
				.setTranslateY(radarCentralPoints.get(carIndex).getTranslateY()
						+ sin((Math.PI / 180) * cars.get(carIndex).getDirection())
								* (cars.get(carIndex).getSpeed() - speedLostCausedByRotation));
	}

	private double calculateIntersect(Line radarLine, int carIndex) {
		double minDistance = calculateDistance(radarLine);
		for (Line line : mapLines) {
			Shape intersectPoint = Shape.intersect(line, radarLine);
			if (intersectPoint.getLayoutBounds().getMaxX() > -1) {
				Shape radarCentralCoordinate = Shape.intersect(radarLine, radarCentralPoints.get(carIndex));

				double currentDistance = calculateDistance(intersectPoint.getLayoutBounds().getMaxX(),
						intersectPoint.getLayoutBounds().getMaxY(), radarCentralCoordinate.getLayoutBounds().getMaxX(),
						radarCentralCoordinate.getLayoutBounds().getMaxY());

				if (currentDistance < minDistance) {
					minDistance = currentDistance;
				}
			}
		}
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
