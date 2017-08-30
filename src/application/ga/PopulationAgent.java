package application.ga;

import static application.shared.Constants.NETWORK_POPULATION_SIZE;

import application.ga.model.Network;
import application.ga.model.Population;
import application.shared.PropertiesForBinding;

public class PopulationAgent {

	private Population population;
	private int populationIndex = 1;

	public PopulationAgent() {
		population = new Population(NETWORK_POPULATION_SIZE);
	}

	public Network getNetworkByIndex(int index) {
		return population.getNetworkByIndex(index);
	}

	public void triggerPopulationRefresh() {
		population.buildNewGeneration();
		PropertiesForBinding.populationProperty.set(populationIndex);
		populationIndex++;
	}

}
