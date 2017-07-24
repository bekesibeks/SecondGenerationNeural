package application.factories;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class TrackFactory {

	public static List<Line> buildTrackLines(int outerSizeX, int outerSizeY, int startX, int startY) {
		List<Line> routes = new ArrayList<>();

		Line up = new Line(startX, startY, startX + outerSizeX, startY);
		Line down = new Line(startX, startY, startX, startY + outerSizeY);
		Line left = new Line(startX + outerSizeX, startY, startX + outerSizeX, startY + outerSizeY);
		Line right = new Line(startX, startY + outerSizeY, startX + outerSizeX, startY + outerSizeY);

		routes.add(up);
		routes.add(down);
		routes.add(left);
		routes.add(right);

		routes.stream().forEach(line -> line.setStroke(Color.YELLOW));

		return routes;
	}

}
