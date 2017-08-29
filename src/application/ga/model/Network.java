package application.ga.model;

import static application.shared.Constants.CAR_DEFAULT_SPEED;
import static application.shared.Constants.NETWORK_MAX_FITNESS;

import java.util.List;

public class Network implements Comparable<Network> {

	private InputLayer inputLayer;
	private NetworkLayer firstHiddenLayer;
	private NetworkLayer secondHiddenLayer;
	private NetworkLayer outputLayer;

	private double fitness;
	private boolean isAlive;

	public Network(int inputLayerSize, int firstHiddenLayerSize, int secondHiddenLayerSize, int outputLayerSize) {
		fitness = 0;
		isAlive = true;
		inputLayer = new InputLayer();
		firstHiddenLayer = new NetworkLayer(firstHiddenLayerSize, inputLayerSize, "H1");
		secondHiddenLayer = new NetworkLayer(secondHiddenLayerSize, firstHiddenLayerSize, "H2");
		outputLayer = new NetworkLayer(outputLayerSize, secondHiddenLayerSize, "O ");
	}

	public List<Double> activateNetwork(List<Double> inputs) {
		List<Double> outputFromInputLayer = inputLayer.getNormalisedInput(inputs);
		List<Double> outputFromFirstHiddenLayer = firstHiddenLayer.activateLayer(outputFromInputLayer);
		List<Double> outputFromSecondHiddenLayer = secondHiddenLayer.activateLayer(outputFromFirstHiddenLayer);
		List<Double> networkOutput = outputLayer.activateLayer(outputFromSecondHiddenLayer);

		return networkOutput;
	}

	public void increaseFitness() {
		fitness += CAR_DEFAULT_SPEED;
	}

	public NetworkLayer getFirstHiddenLayer() {
		return firstHiddenLayer;
	}

	public void setFirstHiddenLayer(NetworkLayer firstHiddenLayer) {
		this.firstHiddenLayer = firstHiddenLayer;
	}

	public NetworkLayer getSecondHiddenLayer() {
		return secondHiddenLayer;
	}

	public void setSecondHiddenLayer(NetworkLayer secondHiddenLayer) {
		this.secondHiddenLayer = secondHiddenLayer;
	}

	public double getFitness() {
		return fitness / NETWORK_MAX_FITNESS;
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

	public NetworkLayer getOutputLayer() {
		return outputLayer;
	}

	public void setOutputLayer(NetworkLayer outputLayer) {
		this.outputLayer = outputLayer;
	}

	public boolean isAlive() {
		return isAlive;
	}

	public void setAlive(boolean isAlive) {
		this.isAlive = isAlive;
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
		toString.append(firstHiddenLayer + "\n");
		toString.append("OUTPUT LAYER :\n");
		toString.append(outputLayer + "\n");

		return toString.toString();
	}

}
