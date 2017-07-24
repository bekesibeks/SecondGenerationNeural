package application.view;

import static javafx.animation.Animation.INDEFINITE;

import application.map.Map;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class ViewManager {

	private final Map map;
	private int rotation = 0;

	public ViewManager(Map map) {
		this.map = map;
	}

	public void run() {
		Timeline timeline = new Timeline();
		timeline.setCycleCount(INDEFINITE);
		timeline.getKeyFrames().add(new KeyFrame(Duration.millis(30), event -> {
			boolean crashed = map.updateMap(rotation);
			if (!crashed) {
				timeline.stop();
			}
		}));
		timeline.play();
	}

}
