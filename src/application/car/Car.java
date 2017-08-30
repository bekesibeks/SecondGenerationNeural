package application.car;

import static application.shared.Constants.CAR_DEFAULT_DIRECTION;
import static application.shared.Constants.CAR_DEFAULT_LENGTH;
import static application.shared.Constants.CAR_DEFAULT_SPEED;
import static application.shared.Constants.CAR_DEFAULT_WIDTH;
import static application.shared.Constants.DEBUG_MODE;

import java.net.URL;

import application.Main;
import javafx.scene.Group;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;

public class Car {

	private static int numberOfCar = 0;

	private double direction = CAR_DEFAULT_DIRECTION;
	private double speed = CAR_DEFAULT_SPEED;

	private Group carView;

	public Car() {
		numberOfCar++;

		Rectangle carBody = buildCarTexture();

		carView = new Group();
		carView.getChildren().add(carBody);
	}

	private Rectangle buildCarTexture() {
		Rectangle carBody = new Rectangle(CAR_DEFAULT_LENGTH, CAR_DEFAULT_WIDTH);
		if (!DEBUG_MODE) {
			URL url = Main.class.getResource("car_40x15.jpg");
			carBody.setFill(
					new ImagePattern(new Image(url.toString(), CAR_DEFAULT_LENGTH, CAR_DEFAULT_WIDTH, false, true)));
		} else {
			carBody.setFill(Color.TRANSPARENT);
			if (numberOfCar <= 4) {
				carBody.setStroke(Color.MEDIUMTURQUOISE);
			} else if (numberOfCar <= 5) {
				carBody.setStroke(Color.RED);
			} else if (numberOfCar <= 6) {
				carBody.setStroke(Color.GREEN);
			} else {
				carBody.setStroke(Color.WHITE);
			}
			carBody.setStrokeType(StrokeType.INSIDE);
			carBody.setStrokeWidth(2);
			GaussianBlur gaussianBlur = new GaussianBlur();
			gaussianBlur.setRadius(3);
			carBody.setEffect(gaussianBlur);
			carBody.setOpacity(0.6);

		}

		return carBody;

	}

	public double getDirection() {
		return direction;
	}

	public void setDirection(double direction) {
		this.direction = direction;
	}

	public double getSpeed() {
		return speed;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}

	public Group getCarView() {
		return carView;
	}

	public void setCarView(Group carView) {
		this.carView = carView;
	}

}
