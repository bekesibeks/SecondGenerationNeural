package application.ga;

import java.util.ArrayList;
import java.util.List;

public class Population {

	private List<Network> networks;
	private int index;

	public Population() {
		Network network1 = new Network(5, 8, 2);
		Network network2 = new Network(5, 8, 2);
		Network network3 = new Network(5, 8, 2);
		Network network4 = new Network(5, 8, 2);

		networks = new ArrayList<>();
		networks.add(network1);
		networks.add(network2);
		networks.add(network3);
		networks.add(network4);
	}

	public List<Network> getNetworks() {
		return networks;
	}

	public void setNetworks(List<Network> networks) {
		this.networks = networks;
	}
	
	public Network getNextNetwork() {
		int i = index++ % networks.size();
		System.out.println(i);
		return networks.get(i);
	}

}
