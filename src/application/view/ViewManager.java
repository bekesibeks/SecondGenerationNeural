package application.view;

import static application.shared.Constants.CAR_MAX_ROTATION;
import static javafx.animation.Animation.INDEFINITE;

import java.util.ArrayList;
import java.util.List;

import application.ga.PopulationAgent;
import application.ga.model.Network;
import application.ga.model.Population;
import application.map.Map;
import application.shared.Constants;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class ViewManager {

	private final Map map;
	private final PopulationAgent agent;

	public ViewManager(Map map) {
		this.map = map;
		agent = new PopulationAgent();
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

			Network activeNetwork = agent.getActiveNetwork();
			List<Double> activateNetwork = activeNetwork.activateNetwork(inputs);

			double rotationLeft = activateNetwork.get(0) * CAR_MAX_ROTATION;
			double rotationRight = activateNetwork.get(1) * -CAR_MAX_ROTATION;

			double rotation = rotationLeft + rotationRight;

			crashed = map.updateMap((int) rotation);

			if (!crashed) {
				timeline.stop();
				map.initMap();
				agent.triggerNetworkSwitch();

				waitOneSec();

				timeline.playFromStart();
			}
		}));
		timeline.setDelay(Duration.millis(200));
		timeline.setCycleCount(INDEFINITE);
		timeline.play();
	}

	private void waitOneSec() {
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
