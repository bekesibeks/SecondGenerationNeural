package mapcreator;

import java.util.ArrayList;
import java.util.List;

import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class MapCreator {

	private List<Line> lines;

	private boolean firstPoint;
	private Point2D previousPoint;

	public MapCreator(int width, int height) {
		lines = new ArrayList<>();
		firstPoint = true;
	}

	public Line putPoint(Point2D newPoint) {
		if (firstPoint) {
			previousPoint = newPoint;
			firstPoint = false;
			return null;
		} else {
			Line line = new Line(previousPoint.getX(), previousPoint.getY(), newPoint.getX(), newPoint.getY());
			line.setStroke(Color.YELLOW);
			lines.add(line);
			previousPoint = newPoint;
			return line;
		}
	}

	public void startAgain() {
		firstPoint = true;
	}

	public List<Line> getLines() {
		return lines;
	}

}
