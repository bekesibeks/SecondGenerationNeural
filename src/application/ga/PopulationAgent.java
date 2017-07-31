package application.ga;

import static application.shared.Constants.CAR_DEFAULT_SPEED;
import static application.shared.Constants.NETWORK_POPULATION_SIZE;

import application.ga.model.Network;
import application.ga.model.Population;

public class PopulationAgent {

	private Population population;
	private int activeNetworkIndex;
	private double activeNetworkFitness = 0;
	private int populationIndex=1;

	public PopulationAgent() {
		population = new Population(NETWORK_POPULATION_SIZE);
		activeNetworkIndex = 0;
	}

	public Network getActiveNetwork() {
		activeNetworkFitness += CAR_DEFAULT_SPEED;
		return population.getNetworkByIndex(activeNetworkIndex);
	}

	public void triggerNetworkSwitch() {
		System.out.println(
				activeNetworkIndex+1 + " Fitness: " + activeNetworkFitness + ", " + activeNetworkFitness / 3000.0);
		population.getNetworkByIndex(activeNetworkIndex).setFitness(activeNetworkFitness / 3000.0);
		activeNetworkIndex++;
		activeNetworkFitness = 0;

		if (lastNetwork()) {
			activeNetworkIndex = 0;
			population.buildNewGeneration();
			System.out.println("Population finished : "+populationIndex);
			populationIndex++;
		}
	}

	private boolean lastNetwork() {
		return activeNetworkIndex == NETWORK_POPULATION_SIZE;
	}

}
