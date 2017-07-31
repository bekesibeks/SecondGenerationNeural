package application.ga;

import static application.shared.Constants.NETWORK_POPULATION_SIZE;


public class PopulationAgent {
	
	private Population population;
	private int activeNetworkIndex;
	
	public PopulationAgent() {
		population = new Population(NETWORK_POPULATION_SIZE);
		activeNetworkIndex=0;
	}
	
	public Network getActiveNetwork() {
		return population.getNetworkByIndex(activeNetworkIndex);
	}
	
	public void triggerNetworkSwitch() {
		activeNetworkIndex++;
		if(lastNetwork()) {
			activeNetworkIndex = 0;
			population.buildNewGeneration();
		}
	}

	private boolean lastNetwork() {
		return activeNetworkIndex == NETWORK_POPULATION_SIZE;
	}
	
}
