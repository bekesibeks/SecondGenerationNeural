package application.ga.model;

import java.util.List;

import application.view.NeuralNetworkView;

public class Network implements Comparable<Network> {

	private InputLayer inputLayer;
	private NetworkLayer hiddenLayer;
	private NetworkLayer outputLayer;

	private double fitness;

	public Network(int inputLayerSize, int hiddenLayerSize, int outputLayerSize) {
		inputLayer = new InputLayer();
		hiddenLayer = new NetworkLayer(hiddenLayerSize, inputLayerSize, "H");
		outputLayer = new NetworkLayer(outputLayerSize, hiddenLayerSize, "O");
	}

	public List<Double> activateNetwork(List<Double> inputs) {
		List<Double> outputFromInputLayer = inputLayer.getNormalisedInput(inputs);
		List<Double> outputFromHiddenLayer = hiddenLayer.activateLayer(outputFromInputLayer);
		List<Double> networkOutput = outputLayer.activateLayer(outputFromHiddenLayer);

		updateViewProperties(outputFromInputLayer, outputFromHiddenLayer, networkOutput);

		return networkOutput;
	}

	private void updateViewProperties(List<Double> outputFromInputLayer, List<Double> outputFromHiddenLayer,
			List<Double> networkOutput) {
		for (int i = 0; i < outputFromInputLayer.size(); i++) {
			NeuralNetworkView.inputLayerOutputs.get(i).set(Math.abs(outputFromInputLayer.get(i)));
		}
		for (int i = 0; i < outputFromHiddenLayer.size(); i++) {
			NeuralNetworkView.hiddenLayerOutputs.get(i).set(Math.abs(outputFromHiddenLayer.get(i)));
		}
		for (int i = 0; i < networkOutput.size(); i++) {
			NeuralNetworkView.outputLayerOutputs.get(i).set(Math.abs(networkOutput.get(i)));
		}
	}

	public double getFitness() {
		return fitness;
	}

	public void setFitness(double fitness) {
		this.fitness = fitness;
	}

	public InputLayer getInputLayer() {
		return inputLayer;
	}

	public void setInputLayer(InputLayer inputLayer) {
		this.inputLayer = inputLayer;
	}

	public NetworkLayer getHiddenLayer() {
		return hiddenLayer;
	}

	public void setHiddenLayer(NetworkLayer hiddenLayer) {
		this.hiddenLayer = hiddenLayer;
	}

	public NetworkLayer getOutputLayer() {
		return outputLayer;
	}

	public void setOutputLayer(NetworkLayer outputLayer) {
		this.outputLayer = outputLayer;
	}

	@Override
	public int compareTo(Network o) {
		return Double.compare(o.fitness, fitness);
	}

	@Override
	public String toString() {
		StringBuilder toString = new StringBuilder("Network: \n");
		toString.append("INPUT LAYER :\n");
		toString.append(inputLayer + "\n");
		toString.append("HIDDEN LAYER :\n");
		toString.append(hiddenLayer + "\n");
		toString.append("OUTPUT LAYER :\n");
		toString.append(outputLayer + "\n");

		return toString.toString();
	}

}
