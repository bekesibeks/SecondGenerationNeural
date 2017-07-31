package application.car;

import static application.shared.Constants.CAR_DEFAULT_DIRECTION;
import static application.shared.Constants.CAR_DEFAULT_LENGTH;
import static application.shared.Constants.CAR_DEFAULT_SPEED;
import static application.shared.Constants.CAR_DEFAULT_WIDTH;

import javafx.scene.Group;
import javafx.scene.shape.Rectangle;

public class Car {

	private double direction = CAR_DEFAULT_DIRECTION;
	private double speed = CAR_DEFAULT_SPEED;

	private Group carView;

	public Car() {
		Rectangle carBody = new Rectangle(CAR_DEFAULT_LENGTH, CAR_DEFAULT_WIDTH);
		carBody.setId("carBody");

		carView = new Group();
		carView.getChildren().add(carBody);
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
