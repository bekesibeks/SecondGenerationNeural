package application.ga.model;

import static application.shared.Constants.NETWORK_HIDDEN_LAYER_SIZE;
import static application.shared.Constants.NETWORK_INPUT_LAYER_SIZE;
import static application.shared.Constants.NETWORK_OUTPUT_LAYER_SIZE;
import static application.shared.Constants.SETTINGS_LOAD_PRETRAINED_NETWORK;

import java.util.ArrayList;
import java.util.List;

import application.ga.CrossoverUtil;
import application.ga.NetworkGenomeConverter;
import application.shared.NetworkLoader;

public class Population {

	private List<Network> networks;
	private NetworkGenomeConverter converter;

	public Population(int populationSize) {
		networks = new ArrayList<>();
		converter = new NetworkGenomeConverter();

		initPopulation(populationSize);
	}

	public void initPopulation(int populationSize) {
		networks.clear();
		for (int i = 0; i < populationSize; i++) {
			Network network;
			if (SETTINGS_LOAD_PRETRAINED_NETWORK) {
				network = NetworkLoader.loadNetwork();
				NetworkGenomeConverter converter = new NetworkGenomeConverter();
				Genome genome = converter.buildGenomeFromNetwork(network);
				Genome mutatedGenome = CrossoverUtil.mutate(genome);
				network = converter.buildNetworkFromGenome(mutatedGenome);
			} else {
				network = new Network(NETWORK_INPUT_LAYER_SIZE, NETWORK_HIDDEN_LAYER_SIZE, NETWORK_OUTPUT_LAYER_SIZE);
			}
			networks.add(network);
		}
	}

	public void buildNewGeneration() {
		List<Genome> genomes = new ArrayList<>();
		for (int i = 0; i < networks.size(); i++) {
			Genome genome = converter.buildGenomeFromNetwork(networks.get(i));
			genomes.add(genome);
		}
		List<Genome> newGenomes = CrossoverUtil.crossoverGenomes(genomes);
		networks.clear();
		for (int i = 0; i < newGenomes.size(); i++) {
			Network network = converter.buildNetworkFromGenome(newGenomes.get(i));
			network.setFitness(0);
			networks.add(network);
		}

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
