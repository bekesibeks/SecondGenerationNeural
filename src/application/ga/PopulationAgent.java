package application.ga;

import static application.shared.Constants.CAR_DEFAULT_SPEED;
import static application.shared.Constants.NETWORK_MAX_FITNESS;
import static application.shared.Constants.NETWORK_POPULATION_SIZE;

import application.ga.model.Network;
import application.ga.model.Population;
import application.shared.PropertiesForBinding;

public class PopulationAgent {

	private Population population;
	private int activeNetworkIndex;
	private double activeNetworkFitness = 0;
	private int populationIndex = 1;

	public PopulationAgent() {
		population = new Population(NETWORK_POPULATION_SIZE);
		activeNetworkIndex = 0;
	}

	public Network getActiveNetwork() {
		activeNetworkFitness += CAR_DEFAULT_SPEED;
		return population.getNetworkByIndex(activeNetworkIndex);
	}

	public Network getNetworkByIndex(int index) {
		return population.getNetworkByIndex(index);
	}

	public void triggerNetworkSwitch() {
		System.out.println(activeNetworkIndex + 1 + " Fitness: " + activeNetworkFitness + ", "
				+ activeNetworkFitness / NETWORK_MAX_FITNESS);
		population.getNetworkByIndex(activeNetworkIndex).setFitness(activeNetworkFitness / NETWORK_MAX_FITNESS);
		activeNetworkIndex++;
		activeNetworkFitness = 0;

		if (lastNetwork()) {
			activeNetworkIndex = 0;
			population.buildNewGeneration();
			System.out.println("Population finished : " + populationIndex);
			populationIndex++;
		}
		PropertiesForBinding.populationProperty.set(populationIndex);
	}

	public void triggerPopulationRefresh() {
		// activeNetworkFitness = 0;
		// activeNetworkIndex = 0;
		population.buildNewGeneration();
		PropertiesForBinding.populationProperty.set(populationIndex);
		populationIndex++;
		// population.initPopulation(NETWORK_POPULATION_SIZE);
	}

	private boolean lastNetwork() {
		return activeNetworkIndex == NETWORK_POPULATION_SIZE;
	}

	public double getActiveNetworkFitness() {
		return activeNetworkFitness;
	}

}
