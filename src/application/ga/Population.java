package application.ga;

import static application.shared.Constants.NETWORK_HIDDEN_LAYER_SIZE;
import static application.shared.Constants.NETWORK_INPUT_LAYER_SIZE;
import static application.shared.Constants.NETWORK_OUTPUT_LAYER_SIZE;

import java.util.ArrayList;
import java.util.List;

public class Population {

	private List<Network> networks;

	public Population(int populationSize) {
		networks = new ArrayList<>();

		for (int i = 0; i < populationSize; i++) {
			Network network = new Network(NETWORK_INPUT_LAYER_SIZE, NETWORK_HIDDEN_LAYER_SIZE,
					NETWORK_OUTPUT_LAYER_SIZE);
			networks.add(network);
		}
	}

	public void buildNewGeneration() {

	}

	public Network getNetworkByIndex(int index) {
		return networks.get(index);
	}

	public List<Network> getNetworks() {
		return networks;
	}

	public void setNetworks(List<Network> networks) {
		this.networks = networks;
	}

}
