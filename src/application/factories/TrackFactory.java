package application.factories;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class TrackFactory {

	public static List<Line> buildTrackLines(int width, int length, int transitionX, int transitionY) {
		List<Line> routes = new ArrayList<>();

		Line up = new Line(transitionX, transitionY, transitionX + width, transitionY);
		Line down = new Line(transitionX, transitionY, transitionX, transitionY + length);
		Line left = new Line(transitionX + width, transitionY, transitionX + width, transitionY + length);
		Line right = new Line(transitionX, transitionY + length, transitionX + width, transitionY + length);

		routes.add(up);
		routes.add(down);
		routes.add(left);
		routes.add(right);

		routes.stream().forEach(line -> line.setStroke(Color.YELLOW));

		return routes;
	}

}
