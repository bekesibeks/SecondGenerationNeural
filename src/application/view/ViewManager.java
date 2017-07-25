package application.view;

import static javafx.animation.Animation.INDEFINITE;

import application.map.Map;
import application.shared.Constants;
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
		timeline.getKeyFrames().add(new KeyFrame(Duration.millis(Constants.DEFAULT_FRAME_RATE), event -> {
			boolean crashed = map.updateMap(rotation);
			if (!crashed) {
				timeline.stop();
				map.initMap();
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				timeline.playFromStart();
			}
		}));
		timeline.setDelay(Duration.seconds(1));
		timeline.play();
	}

}
