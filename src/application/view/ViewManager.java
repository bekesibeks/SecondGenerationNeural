package application.view;

import static application.shared.Constants.CAR_DEFAULT_WIDTH;
import static application.shared.Constants.CAR_MAX_ROTATION;
import static javafx.animation.Animation.INDEFINITE;

import java.util.ArrayList;
import java.util.List;

import application.ga.PopulationAgent;
import application.ga.model.Network;
import application.ga.model.Population;
import application.map.Map;
import application.shared.Constants;
import application.shared.NetworkLoader;
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
			inputs.add(map.leftLineDistance.get() - CAR_DEFAULT_WIDTH / 2);
			inputs.add(map.rightLineDistance.get() - CAR_DEFAULT_WIDTH / 2);
			inputs.add(map.leftFrontLineDistance.get());
			inputs.add(map.rightFrontLineDistance.get());

			Network activeNetwork = agent.getActiveNetwork();
			// if(agent.getActiveNetworkFitness() >
			// Constants.NETWORK_MAX_FITNESS){
			if (agent.getActiveNetworkFitness() > Constants.NETWORK_MAX_FITNESS) {
//				NetworkLoader.saveNetwork(activeNetwork,agent.getActiveNetworkFitness());
			}

			List<Double> activateNetwork = activeNetwork.activateNetwork(inputs);
			double rotationLeft = activateNetwork.get(0) * CAR_MAX_ROTATION;
			double rotationRight = activateNetwork.get(1) * -CAR_MAX_ROTATION;

			double rotation = rotationLeft + rotationRight;

			crashed = map.updateMap(rotation);

			if (!crashed) {
				timeline.stop();
				map.initMap();
				agent.triggerNetworkSwitch();

				waitOneSec();

				timeline.playFromStart();
			}
		}));
		timeline.setDelay(Duration.millis(100));
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
