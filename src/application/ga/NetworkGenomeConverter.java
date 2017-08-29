package application.ga;

import static application.shared.Constants.NETWORK_HIDDEN_LAYER_SIZE;
import static application.shared.Constants.NETWORK_INPUT_LAYER_SIZE;
import static application.shared.Constants.NETWORK_OUTPUT_LAYER_SIZE;

import java.util.ArrayList;
import java.util.List;

import application.ga.model.Connection;
import application.ga.model.Genome;
import application.ga.model.Network;
import application.ga.model.NetworkLayer;
import application.ga.model.Neuron;

public class NetworkGenomeConverter {

	/*
	 * Tested, works. TODO -> refactor it
	 *
	 */
	public Network buildNetworkFromGenome(Genome genome) {
		Network network = new Network(NETWORK_INPUT_LAYER_SIZE, NETWORK_HIDDEN_LAYER_SIZE, NETWORK_OUTPUT_LAYER_SIZE);
		int index = 0;

		for (index = 0; index < hiddenLayerNumberOfWeight(); index += NETWORK_INPUT_LAYER_SIZE + 1) {
			NetworkLayer hiddenLayer = network.getHiddenLayer();
			List<Neuron> neurons = hiddenLayer.getNeurons();
			Neuron neuron = neurons.get(index / (NETWORK_INPUT_LAYER_SIZE + 1));

			for (int j = 0; j < NETWORK_INPUT_LAYER_SIZE + 1; j++) {
				Double currentWeight = genome.getWeights().get(index + j);
				Connection connection = new Connection(currentWeight);

				if (j == NETWORK_INPUT_LAYER_SIZE) {
					neuron.setBias(connection);
				} else {
					neuron.getInConnections().set(j, connection);
				}
			}

		}

		for (int i = index; i < genome.getWeights().size() - 1; i += NETWORK_HIDDEN_LAYER_SIZE + 1) {
			NetworkLayer outputLayer = network.getOutputLayer();
			List<Neuron> neurons = outputLayer.getNeurons();
			Neuron neuron = neurons.get((i - hiddenLayerNumberOfWeight()) / (NETWORK_HIDDEN_LAYER_SIZE + 1));

			for (int j = 0; j < NETWORK_HIDDEN_LAYER_SIZE + 1; j++) {
				Double currentWeight = genome.getWeights().get(i + j);
				Connection connection = new Connection(currentWeight);

				if (j == NETWORK_HIDDEN_LAYER_SIZE) {
					neuron.setBias(connection);
				} else {
					neuron.getInConnections().set(j, connection);
				}
			}
		}

		network.setFitness(genome.getFitness());
		return network;
	}

	public Genome buildGenomeFromNetwork(Network network) {
		List<Neuron> neuronsInHiddenLayer = network.getHiddenLayer().getNeurons();
		List<Neuron> neuronsInOutputLayer = network.getOutputLayer().getNeurons();

		List<Double> weights = new ArrayList<>();
		weights.addAll(extractWeightsFromNeurons(neuronsInHiddenLayer));
		weights.addAll(extractWeightsFromNeurons(neuronsInOutputLayer));

		Genome genome = new Genome();
		genome.setWeights(weights);
		genome.setFitness(network.getFitness());

		return genome;
	}

	private int hiddenLayerNumberOfWeight() {
		return NETWORK_INPUT_LAYER_SIZE * NETWORK_HIDDEN_LAYER_SIZE + NETWORK_HIDDEN_LAYER_SIZE;
	}

	private List<Double> extractWeightsFromNeurons(List<Neuron> neuronsInLayer) {
		List<Double> weights = new ArrayList<>();
		neuronsInLayer.stream().forEach(neuron -> {
			List<Connection> connections = neuron.getWeights();
			for (int i = 0; i < connections.size(); i++) {
				weights.add(connections.get(i).getWeight());
			}
		});

		return weights;
	}
}