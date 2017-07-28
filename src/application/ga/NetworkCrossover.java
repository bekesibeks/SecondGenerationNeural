package application.ga;

import java.util.Random;

public class NetworkCrossover {

	private static Random random = new Random();

	public static Network crossoverNetworks(Network parentOne, Network parentTwo) {
		Network child = createChildWithSameArchitecture(parentOne);

		crossoverInputLayer(parentOne, parentTwo, child);
		crossoverHiddenLayer(parentOne, parentTwo, child);
		crossoverOutputLayer(parentOne, parentTwo, child);

		return child;
	}

	private static void crossoverHiddenLayer(Network parentOne, Network parentTwo, Network child) {
		int hiddenLayerSize = parentOne.getHiddenLayer().size();

		for (int i = 0; i < hiddenLayerSize; i++) {
			for (int j = 0; j < child.getHiddenLayer().size(); j++) {
				Neuron childNeuron = child.getHiddenLayer().get(j);
				if (random.nextBoolean()) {
					Neuron parentNeuron = parentOne.getHiddenLayer().get(j);
					double threshold = parentNeuron.getThreshold();
					threshold = mutate(threshold);
					childNeuron.setThreshold(threshold);
				} else {
					Neuron parentNeuron = parentTwo.getHiddenLayer().get(j);
					double threshold = parentNeuron.getThreshold();
					threshold = mutate(threshold);
					childNeuron.setThreshold(threshold);
				}

				for (int k = 0; k < childNeuron.getInConnections().size(); k++) {
					Connection childConnection = childNeuron.getInConnections().get(k);
					if (random.nextBoolean()) {
						Neuron parentNeuron = parentOne.getHiddenLayer().get(j);
						Connection parentConnection = parentNeuron.getInConnections().get(k);
						double weight = parentConnection.getWeight();
						weight = mutate(weight);
						childConnection.setWeight(weight);
					} else {
						Neuron parentNeuron = parentTwo.getHiddenLayer().get(j);
						Connection parentConnection = parentNeuron.getInConnections().get(k);
						double weight = parentConnection.getWeight();
						weight = mutate(weight);
						childConnection.setWeight(weight);
					}
				}
			}
		}
	}

	/*
	 * TODO -> remove code duplications.
	 */
	private static void crossoverOutputLayer(Network parentOne, Network parentTwo, Network child) {
		int outputLayerSize = parentOne.getOutputLayer().size();

		for (int i = 0; i < outputLayerSize; i++) {
			for (int j = 0; j < child.getOutputLayer().size(); j++) {
				Neuron childNeuron = child.getOutputLayer().get(j);
				if (random.nextBoolean()) {
					Neuron parentNeuron = parentOne.getOutputLayer().get(j);
					double threshold = parentNeuron.getThreshold();
					threshold = mutate(threshold);
					childNeuron.setThreshold(threshold);
				} else {
					Neuron parentNeuron = parentTwo.getOutputLayer().get(j);
					double threshold = parentNeuron.getThreshold();
					threshold = mutate(threshold);
					childNeuron.setThreshold(threshold);
				}

				for (int k = 0; k < childNeuron.getInConnections().size(); k++) {
					Connection childConnection = childNeuron.getInConnections().get(k);
					if (random.nextBoolean()) {
						Neuron parentNeuron = parentOne.getOutputLayer().get(j);
						Connection parentConnection = parentNeuron.getInConnections().get(k);
						double weight = parentConnection.getWeight();
						weight = mutate(weight);
						childConnection.setWeight(weight);
					} else {
						Neuron parentNeuron = parentTwo.getOutputLayer().get(j);
						Connection parentConnection = parentNeuron.getInConnections().get(k);
						double weight = parentConnection.getWeight();
						weight = mutate(weight);
						childConnection.setWeight(weight);
					}
				}
			}
		}
	}

	private static void crossoverInputLayer(Network parentOne, Network parentTwo, Network child) {
		int inputLayerSize = parentOne.getInputLayer().size();

		for (int i = 0; i < inputLayerSize; i++) {
			for (int j = 0; j < child.getInputLayer().size(); j++) {
				Neuron childNeuron = child.getInputLayer().get(j);
				if (random.nextBoolean()) {
					Neuron parentNeuron = parentOne.getInputLayer().get(j);
					double threshold = parentNeuron.getThreshold();
					threshold = mutate(threshold);
					childNeuron.setThreshold(threshold);
				} else {
					Neuron parentNeuron = parentTwo.getInputLayer().get(j);
					double threshold = parentNeuron.getThreshold();
					threshold = mutate(threshold);
					childNeuron.setThreshold(threshold);
				}
			}
		}
	}

	private static double mutate(double valueToMutate) {
		double result = valueToMutate;
		if (Math.random() < 0.1) {
			double amount = valueToMutate * 0.2;

			if (Math.random() < 0.5) {
				result += amount;
			} else {
				result -= amount;
			}
		}

		return result;
	}

	private static Network createChildWithSameArchitecture(Network parentOne) {
		return new Network(parentOne.getInputLayer().size(), parentOne.getHiddenLayer().size(),
				parentOne.getOutputLayer().size());
	}

}
