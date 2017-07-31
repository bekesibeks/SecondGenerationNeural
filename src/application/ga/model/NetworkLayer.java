package application.ga.model;

import java.util.ArrayList;
import java.util.List;

public class NetworkLayer {

	private List<Neuron> neurons;

	public NetworkLayer(int layerSize, int inputsForEachNeuron, String layerName) {
		neurons = new ArrayList<>();
		for (int i = 0; i < layerSize; i++) {
			Neuron neuron = new Neuron("(" + layerName + "-" + (i + 1) + ")", inputsForEachNeuron);
			neurons.add(neuron);
		}
	}

	public List<Double> activateLayer(List<Double> inputs) {
		List<Double> layerOutputs = new ArrayList<>();
		for (int i = 0; i < neurons.size(); i++) {
			Neuron neuron = neurons.get(i);
			double neuronOutput = neuron.activate(inputs);
			layerOutputs.add(neuronOutput);
		}
		return layerOutputs;
	}

	public List<Neuron> getNeurons() {
		return neurons;
	}

	public void setNeurons(List<Neuron> neurons) {
		this.neurons = neurons;
	}
	
	@Override
	public String toString() {
		String result="";
		for(Neuron neuron : neurons ) {
			result +=neuron.toString()+"\n";
		}
		return result;
	}

}
