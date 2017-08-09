package application.view;

import static application.shared.Constants.CAR_DEFAULT_WIDTH;
import static application.shared.Constants.CAR_MAX_ROTATION;
import static application.shared.Constants.DEFAULT_FRAME_RATE;
import static javafx.animation.Animation.INDEFINITE;

import java.util.ArrayList;
import java.util.List;

import application.ga.PopulationAgent;
import application.ga.model.Network;
import application.map.Map;
import application.map.MapData;
import application.shared.Constants;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class ViewManager {

	private final Map map;
	private final PopulationAgent agent;
	private final Timeline timeline;

	public ViewManager(Map map) {
		this.map = map;
		this.agent = new PopulationAgent();
		this.timeline = new Timeline();
		initTimeline();
	}

	public void run() {
		timeline.playFromStart();
	}

	public void restart() {
		timeline.stop();
		agent.triggerPopulationRefresh();
		map.initMap();
	}

	private void initTimeline() {
		timeline.getKeyFrames().add(new KeyFrame(Duration.millis(DEFAULT_FRAME_RATE), event -> {
			boolean allCrashed = true;
			for (int i = 0; i < Constants.NETWORK_POPULATION_SIZE; i++) {
				List<Double> inputs = new ArrayList<>();
				MapData mapData = map.getMapData(i);
				inputs.add(mapData.getFrontLineDistance());
				inputs.add(mapData.getLeftLineDistance() - CAR_DEFAULT_WIDTH / 2);
				inputs.add(mapData.getRightLineDistance() - CAR_DEFAULT_WIDTH / 2);
				inputs.add(mapData.getLeftFrontLineDistance());
				inputs.add(mapData.getRightFrontLineDistance());

				Network activeNetwork = agent.getNetworkByIndex(i);
				if (activeNetwork.isAlive()) {
					List<Double> activateNetwork = activeNetwork.activateNetwork(inputs);
					double rotationLeft = activateNetwork.get(0) * CAR_MAX_ROTATION;
					double rotationRight = activateNetwork.get(1) * -CAR_MAX_ROTATION;
					double rotation = rotationLeft + rotationRight;
					// PropertiesForBinding.steerRotateProperty.set(rotation);
					boolean currentCarIsAlive = map.updateMap(rotation, i);
					if (currentCarIsAlive) {
						allCrashed = false;
						activeNetwork.increaseFitness();
					} else {
						activeNetwork.setAlive(false);
					}
				}
			}

			if (allCrashed) {
				timeline.stop();
				map.initMap();
				agent.triggerPopulationRefresh();
				// agent.triggerNetworkSwitch();
				System.out.println("Population refresh");
				waitALittle();

				timeline.playFromStart();
			}
		}));
		timeline.setDelay(Duration.millis(100));
		timeline.setCycleCount(INDEFINITE);
	}

	private void waitALittle() {
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
