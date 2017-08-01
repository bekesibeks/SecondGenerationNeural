package application.car;

import static application.shared.Constants.CAR_DEFAULT_WIDTH;
import static application.shared.Constants.CAR_RADAR_RANGE;
import static application.shared.Constants.CAR_RADAR_RANGE_DIAGONAL;

import application.shared.Constants;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

public class Radar {

	private static final Color DEFAULT_COLOR = Color.TRANSPARENT;
	private final Group radarGroup;
	private Line leftLine;
	private Line rightLine;
	private Line rightFrontLine;
	private Line leftFrontLine;
	private Line frontLine;

	public Radar() {
		radarGroup = new Group();
		initLines();
	}

	private void initLines() {
		leftLine = new Line(0, 0, 0, CAR_RADAR_RANGE);
		leftLine.setStroke(DEFAULT_COLOR);
		leftLine.setTranslateY(CAR_DEFAULT_WIDTH / 2);

		rightLine = new Line(0, 0, 0, -CAR_RADAR_RANGE);
		rightLine.setStroke(DEFAULT_COLOR);
		rightLine.setTranslateY(CAR_DEFAULT_WIDTH / 2);

		rightFrontLine = new Line(0, 0, -CAR_RADAR_RANGE_DIAGONAL, -CAR_RADAR_RANGE_DIAGONAL);
		rightFrontLine.setStroke(DEFAULT_COLOR);
		rightFrontLine.setTranslateY(CAR_DEFAULT_WIDTH / 2);

		leftFrontLine = new Line(0, 0, -CAR_RADAR_RANGE_DIAGONAL, CAR_RADAR_RANGE_DIAGONAL);
		leftFrontLine.setStroke(DEFAULT_COLOR);
		leftFrontLine.setTranslateY(CAR_DEFAULT_WIDTH / 2);

		frontLine = new Line(0, 0, -CAR_RADAR_RANGE, 0);
		frontLine.setStroke(DEFAULT_COLOR);
		frontLine.setTranslateY(CAR_DEFAULT_WIDTH / 2);

		radarGroup.getChildren().add(leftLine);
		radarGroup.getChildren().add(frontLine);
		radarGroup.getChildren().add(rightLine);
		radarGroup.getChildren().add(rightFrontLine);
		radarGroup.getChildren().add(leftFrontLine);
	}

	public Group getRadarView() {
		return radarGroup;
	}

	public Line getLeftLine() {
		return leftLine;
	}

	public void setLeftLine(Line leftLine) {
		this.leftLine = leftLine;
	}

	public Line getRightLine() {
		return rightLine;
	}

	public void setRightLine(Line rightLine) {
		this.rightLine = rightLine;
	}

	public Line getRightFrontLine() {
		return rightFrontLine;
	}

	public void setRightFrontLine(Line rightFrontLine) {
		this.rightFrontLine = rightFrontLine;
	}

	public Line getLeftFrontLine() {
		return leftFrontLine;
	}

	public void setLeftFrontLine(Line leftFrontLine) {
		this.leftFrontLine = leftFrontLine;
	}

	public Line getFrontLine() {
		return frontLine;
	}

	public void setFrontLine(Line frontLine) {
		this.frontLine = frontLine;
	}

}
