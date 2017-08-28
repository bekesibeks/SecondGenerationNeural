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
	private PopulationAgent agent;
	private final Timeline timeline;

	public ViewManager(Map map) {
		this.map = map;
		this.timeline = new Timeline();
		// initTimeline();
	}

	public void run() {
		timeline.playFromStart();
	}

	public void initTimeline() {
		agent = new PopulationAgent();
		timeline.getKeyFrames().add(new KeyFrame(Duration.millis(DEFAULT_FRAME_RATE), event -> {
			boolean allCrashed = true;
			long allCost = 0;
			for (int i = 0; i < Constants.NETWORK_POPULATION_SIZE; i++) {
				long currentTimeMillis = System.currentTimeMillis();

				Network activeNetwork = agent.getNetworkByIndex(i);
				if (activeNetwork.isAlive()) {
					List<Double> inputs = new ArrayList<>();
					MapData mapData = map.getMapData(i);
					inputs.add(mapData.getFrontLineDistance());
					inputs.add(mapData.getLeftLineDistance() - CAR_DEFAULT_WIDTH / 2);
					inputs.add(mapData.getRightLineDistance() - CAR_DEFAULT_WIDTH / 2);
					inputs.add(mapData.getLeftFrontLineDistance());
					inputs.add(mapData.getRightFrontLineDistance());
					long currentTimeMillis2 = System.currentTimeMillis();
					System.out.println(i + ". part time " + (currentTimeMillis2 - currentTimeMillis));

					List<Double> activateNetwork = activeNetwork.activateNetwork(inputs);
					double rotationLeft = activateNetwork.get(0) * CAR_MAX_ROTATION;
					double rotationRight = activateNetwork.get(1) * -CAR_MAX_ROTATION;
					double rotation = rotationLeft + rotationRight;
					// PropertiesForBinding.steerRotateProperty.set(rotation);
					boolean currentCarIsAlive = mapData.isAlive();
					if (currentCarIsAlive) {
						map.updateMap(rotation, i);
						allCrashed = false;
						activeNetwork.increaseFitness();
					} else {
						activeNetwork.setAlive(false);
					}
				}
				long popCost = currentTimeMillis - System.currentTimeMillis();
				allCost += popCost;
				// System.out.println(i + 1 + ". population cost: " + popCost);
			}
			System.out.println("ALL cost : " + allCost);

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

	public void stop() {
		timeline.stop();
		map.initMap();

	}

	private void waitALittle() {
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
