package application.ga;

import static application.shared.Constants.NETWORK_FIRST_HIDDEN_LAYER_SIZE;
import static application.shared.Constants.NETWORK_INPUT_LAYER_SIZE;
import static application.shared.Constants.NETWORK_OUTPUT_LAYER_SIZE;
import static application.shared.Constants.NETWORK_SECOND_HIDDEN_LAYER_SIZE;

import java.util.ArrayList;
import java.util.List;

import application.ga.model.Connection;
import application.ga.model.Genome;
import application.ga.model.Network;
import application.ga.model.Neuron;

public class NetworkGenomeConverter {

	/*
	 * Tested, works. TODO -> refactor it
	 *
	 */


	public Network buildNetworkFromGenome(Genome genome) {
		Network network = new Network(NETWORK_INPUT_LAYER_SIZE, NETWORK_FIRST_HIDDEN_LAYER_SIZE,
				NETWORK_SECOND_HIDDEN_LAYER_SIZE, NETWORK_OUTPUT_LAYER_SIZE);

		int index = 0;
		List<Double> weights = genome.getWeights();

		List<Neuron> hiddenLayer1Neurons = network.getFirstHiddenLayer().getNeurons();
		List<Neuron> hiddenLayer2Neurons = network.getSecondHiddenLayer().getNeurons();
		List<Neuron> outputLayerNeurons = network.getOutputLayer().getNeurons();

		for (int i = 0; i < hiddenLayer1Neurons.size(); i++) {
			Neuron actualNeuron = hiddenLayer1Neurons.get(i);
			List<Connection> inConnections = actualNeuron.getInConnections();
			for (int j = 0; j < inConnections.size(); j++) {
				Connection actualConnection = inConnections.get(j);
				actualConnection.setWeight(weights.get(index));
				index++;
			}
			actualNeuron.setBias(new Connection(weights.get(index)));
			index++;
		}

		for (int i = 0; i < hiddenLayer2Neurons.size(); i++) {
			Neuron actualNeuron = hiddenLayer2Neurons.get(i);
			List<Connection> inConnections = actualNeuron.getInConnections();
			for (int j = 0; j < inConnections.size(); j++) {
				Connection actualConnection = inConnections.get(j);
				actualConnection.setWeight(weights.get(index));
				index++;
			}
			actualNeuron.setBias(new Connection(weights.get(index)));
			index++;
		}
		for (int i = 0; i < outputLayerNeurons.size(); i++) {
			Neuron actualNeuron = outputLayerNeurons.get(i);
			List<Connection> inConnections = actualNeuron.getInConnections();
			for (int j = 0; j < inConnections.size(); j++) {
				Connection actualConnection = inConnections.get(j);
				actualConnection.setWeight(weights.get(index));
				index++;
			}
			actualNeuron.setBias(new Connection(weights.get(index)));
			index++;
		}

		network.setFitness(genome.getFitness());
		return network;
	}

	public Genome buildGenomeFromNetwork(Network network) {
		List<Neuron> neuronsInFirstHiddenLayer = network.getFirstHiddenLayer().getNeurons();
		List<Neuron> neuronsInSecondHiddenLayer = network.getSecondHiddenLayer().getNeurons();
		List<Neuron> neuronsInOutputLayer = network.getOutputLayer().getNeurons();

		List<Double> weights = new ArrayList<>();
		weights.addAll(extractWeightsFromNeurons(neuronsInFirstHiddenLayer));
		weights.addAll(extractWeightsFromNeurons(neuronsInSecondHiddenLayer));
		weights.addAll(extractWeightsFromNeurons(neuronsInOutputLayer));

		Genome genome = new Genome();
		genome.setWeights(weights);
		genome.setFitness(network.getFitness());

		return genome;
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
	
//	public Network buildNetworkFromGenome(Genome genome) {
//		Network network = new Network(NETWORK_INPUT_LAYER_SIZE, NETWORK_FIRST_HIDDEN_LAYER_SIZE,
//				NETWORK_SECOND_HIDDEN_LAYER_SIZE, NETWORK_OUTPUT_LAYER_SIZE);
//
//		int index = 0;
//		List<Double> weights = genome.getWeights();
//
//		List<Neuron> hiddenLayer1Neurons = network.getFirstHiddenLayer().getNeurons();
//		List<Neuron> hiddenLayer2Neurons = network.getSecondHiddenLayer().getNeurons();
//		List<Neuron> outputLayerNeurons = network.getOutputLayer().getNeurons();
//
//		for (int i = 0; i < hiddenLayer1Neurons.size(); i++) {
//			Neuron actualNeuron = hiddenLayer1Neurons.get(i);
//			List<Connection> inConnections = actualNeuron.getInConnections();
//			for (int j = 0; j < inConnections.size(); j++) {
//				Connection actualConnection = inConnections.get(j);
//				actualConnection.setWeight(weights.get(index));
//				index++;
//			}
//			actualNeuron.setBias(new Connection(weights.get(index)));
//			index++;
//		}
//
//		for (int i = 0; i < hiddenLayer2Neurons.size(); i++) {
//			Neuron actualNeuron = hiddenLayer2Neurons.get(i);
//			List<Connection> inConnections = actualNeuron.getInConnections();
//			for (int j = 0; j < inConnections.size(); j++) {
//				Connection actualConnection = inConnections.get(j);
//				actualConnection.setWeight(weights.get(index));
//				index++;
//			}
//			actualNeuron.setBias(new Connection(weights.get(index)));
//			index++;
//		}
//		for (int i = 0; i < outputLayerNeurons.size(); i++) {
//			Neuron actualNeuron = outputLayerNeurons.get(i);
//			List<Connection> inConnections = actualNeuron.getInConnections();
//			for (int j = 0; j < inConnections.size(); j++) {
//				Connection actualConnection = inConnections.get(j);
//				actualConnection.setWeight(weights.get(index));
//				index++;
//			}
//			actualNeuron.setBias(new Connection(weights.get(index)));
//			index++;
//		}
//
//		network.setFitness(genome.getFitness());
//		return network;
//	}

}
