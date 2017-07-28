package application.ga;

import static application.ga.Neuron.sigmoid;

import java.util.ArrayList;
import java.util.List;

public class Network {

	private List<Neuron> inputLayer;
	private List<Neuron> hiddenLayer;
	private List<Neuron> outputLayer;

	private int fitness;

	public Network(int inputLayerSize, int hiddenLayerSize, int outputLayerSize) {
		initNetwork(inputLayerSize, hiddenLayerSize, outputLayerSize);
	}

	public List<Double> activateNetwork(List<Double> inputs) {
		if (inputs.size() != inputLayer.size()) {
			throw new IllegalStateException("Input is not acceptable to network.");
		}

		feedInputData(inputs);

		hiddenLayer.forEach(Neuron::activate);
		outputLayer.forEach(Neuron::activate);

		List<Double> outputs = new ArrayList<>();
		for (int i = 0; i < outputLayer.size(); i++) {
			double output = outputLayer.get(i).getOutput();
			outputs.add(output);
		}

		return outputs;
	}

	private void feedInputData(List<Double> inputs) {
		for (int i = 0; i < inputs.size(); i++) {
			Neuron neuron = inputLayer.get(i);
			Double input = inputs.get(i);

			neuron.setOutput(sigmoid(neuron.getThreshold() + input));
		}
	}

	private void initNetwork(int inputLayerSize, int hiddenLayerSize, int outputLayerSize) {
		inputLayer = createLayer(inputLayerSize, inputLayer, "I");
		hiddenLayer = createLayer(hiddenLayerSize, hiddenLayer, "H");
		outputLayer = createLayer(outputLayerSize, outputLayer, "O");

		hiddenLayer.stream().forEach(neuron -> neuron.addNeuronsAsConnection(inputLayer));
		outputLayer.stream().forEach(neuron -> neuron.addNeuronsAsConnection(hiddenLayer));
	}

	private List<Neuron> createLayer(int layerSize, List<Neuron> layer, String layerName) {
		layer = new ArrayList<>();
		for (int i = 0; i < layerSize; i++) {
			Neuron neuron = new Neuron("(" + layerName + "-" + (i + 1) + ")");
			layer.add(neuron);
		}
		return layer;
	}

	@Override
	public String toString() {
		StringBuilder toString = new StringBuilder("Network: \n");
		toString.append("INPUT LAYER :\n");
		inputLayer.forEach(neuron -> toString.append(neuron.toString() + "\n"));
		toString.append("HIDDEN LAYER :\n");
		hiddenLayer.forEach(neuron -> toString.append(neuron.toString() + "\n"));
		toString.append("OUTPUT LAYER :\n");
		outputLayer.forEach(neuron -> toString.append(neuron.toString() + "\n"));

		return toString.toString();
	}

	public List<Neuron> getInputLayer() {
		return inputLayer;
	}

	public void setInputLayer(List<Neuron> inputLayer) {
		this.inputLayer = inputLayer;
	}

	public List<Neuron> getHiddenLayer() {
		return hiddenLayer;
	}

	public void setHiddenLayer(List<Neuron> hiddenLayer) {
		this.hiddenLayer = hiddenLayer;
	}

	public List<Neuron> getOutputLayer() {
		return outputLayer;
	}

	public void setOutputLayer(List<Neuron> outputLayer) {
		this.outputLayer = outputLayer;
	}

	public int getFitness() {
		return fitness;
	}

	public void setFitness(int fitness) {
		this.fitness = fitness;
	}

}
