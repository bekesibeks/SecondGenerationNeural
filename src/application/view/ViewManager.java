package application.view;

import static application.shared.Constants.CAR_DEFAULT_WIDTH;
import static application.shared.Constants.CAR_MAX_ROTATION;
import static application.shared.Constants.DEFAULT_FRAME_RATE;
import static application.shared.Constants.MAP_INDEX;
import static application.shared.Constants.NETWORK_MAX_FITNESS;
import static application.shared.Constants.SETTINGS_LOAD_PRETRAINED_NETWORK;
import static javafx.animation.Animation.INDEFINITE;

import java.util.ArrayList;
import java.util.List;

import application.ga.PopulationAgent;
import application.ga.model.Network;
import application.map.Map;
import application.map.MapData;
import application.shared.Constants;
import application.shared.NetworkLoader;
import application.shared.PropertiesForBinding;
import javafx.animation.KeyFrame;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.util.Duration;

public class ViewManager {

	private final Map map;
	private PopulationAgent agent;
	private final Timeline timeline;
	private final SequentialTransition winAnimation;

	public ViewManager(Map map, SequentialTransition winAnimation) {
		this.map = map;
		this.timeline = new Timeline();
		this.winAnimation = winAnimation;
	}

	public void run() {
		timeline.playFromStart();
	}

	public void initTimeline() {
		agent = new PopulationAgent();
		timeline.getKeyFrames().clear();

		timeline.getKeyFrames().add(new KeyFrame(Duration.millis(DEFAULT_FRAME_RATE), event -> {
			boolean allCrashed = true;
			for (int i = 0; i < Constants.NETWORK_POPULATION_SIZE; i++) {

				Network activeNetwork = agent.getNetworkByIndex(i);
				if (activeNetwork.isAlive()) {
					List<Double> inputs = new ArrayList<>();
					MapData mapData = map.getMapData(i);
					inputs.add(mapData.getFrontLineDistance());
					inputs.add(mapData.getLeftLineDistance() - CAR_DEFAULT_WIDTH / 2);
					inputs.add(mapData.getRightLineDistance() - CAR_DEFAULT_WIDTH / 2);
					inputs.add(mapData.getLeftFrontLineDistance());
					inputs.add(mapData.getRightFrontLineDistance());

					List<Double> activateNetwork = activeNetwork.activateNetwork(inputs);
					double rotationLeft = activateNetwork.get(0) * CAR_MAX_ROTATION;
					double rotationRight = activateNetwork.get(1) * -CAR_MAX_ROTATION;
					double rotation = rotationLeft + rotationRight;

					boolean currentCarIsAlive = mapData.isAlive();
					if (currentCarIsAlive) {
						map.updateMap(rotation, i);
						allCrashed = false;
						activeNetwork.increaseFitness();
						PropertiesForBinding.topFitnessProperty.set(round(activeNetwork.getFitness(), 2));
					} else {
						activeNetwork.setAlive(false);
					}

					if (activeNetwork.getFitness() > 1) {
						win(activeNetwork);
					}

				}
			}

			if (allCrashed) {
				timeline.stop();
				map.initMap();
				agent.triggerPopulationRefresh();

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

	public void triggerNextMap() {
		MAP_INDEX++;
		map.initMap();
		map.loadTrack();
	}

	private void win(Network activeNetwork) {
		timeline.stop();
		waitALittle();
		NetworkLoader.saveNetwork(activeNetwork, NETWORK_MAX_FITNESS);
		winAnimation.setOnFinished(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				triggerNextMap();
				initTimeline();
				PropertiesForBinding.populationProperty.set(0);
				waitALittle();
				waitALittle();
				waitALittle();
				SETTINGS_LOAD_PRETRAINED_NETWORK = true;
				initTimeline();
				run();
			}
		});
		winAnimation.play();
	}

	private double round(double value, int places) {
		if (places < 0) {
			throw new IllegalArgumentException();
		}

		long factor = (long) Math.pow(10, places);
		value = value * factor;
		long tmp = Math.round(value);
		return (double) tmp / factor;
	}

	private void waitALittle() {
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
