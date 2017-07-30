package application.view;

import static javafx.animation.Animation.INDEFINITE;

import java.util.ArrayList;
import java.util.List;

import application.ga.Network;
import application.ga.Population;
import application.map.Map;
import application.shared.Constants;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class ViewManager {

	private final Map map;
	private final Population population;
	private int rotation = 0;

	public ViewManager(Map map) {
		this.map = map;
		population = new Population();
	}

	public void run() {
		Timeline timeline = new Timeline();

		timeline.getKeyFrames().add(new KeyFrame(Duration.millis(Constants.DEFAULT_FRAME_RATE), event -> {

			boolean crashed = false;

			List<Double> inputs = new ArrayList<>();
			inputs.add(map.frontLineDistance.get());
			inputs.add(map.leftLineDistance.get());
			inputs.add(map.rightLineDistance.get());
			inputs.add(map.leftFrontLineDistance.get());
			inputs.add(map.rightFrontLineDistance.get());

			crashed = map.updateMap(0);

			if (!crashed) {
				timeline.stop();
				map.initMap();

				waitOneSec();

				timeline.playFromStart();
			}
		}));
		timeline.setDelay(Duration.seconds(1));
		timeline.setCycleCount(INDEFINITE);
		timeline.play();
	}

	private void waitOneSec() {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
