package application.ga;

import static application.shared.Constants.DEFAULT_BIAS;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class Neuron {

	private String id;

	private List<Connection> inConnections;
	private Connection bias;
	private int numberOfConnections;

	private double output;

	public Neuron(String id, int numberOfConnections) {
		this.id = id;
		this.numberOfConnections = numberOfConnections;
		this.bias = new Connection();
		
		inConnections = new ArrayList<>();
		for(int i=0;i<numberOfConnections;i++) {
			Connection connection = new Connection();
			inConnections.add(connection);
		}
	}

	public double activate(List<Double> inputs) {
		double sumOfInputs = 0;
		for (int i = 0; i < inConnections.size(); i++) {
			double weight = inConnections.get(i).getWeight();
			double input = inputs.get(i);

			sumOfInputs += (weight * input);
		}

		sumOfInputs += bias.getWeight() * (DEFAULT_BIAS);

		output = sigmoid(sumOfInputs);
		return output;
	}
	
	public List<Connection> getWeights(){
		List<Connection> weights = new ArrayList<>();
		weights.addAll(inConnections);
		weights.add(bias);
		return weights;
	}
	
	public static double sigmoid(double x) {
		return (1.0 / (1.0 + (Math.exp(-x))));
	}
	
	public void addConnection(Connection inConnection) {
		inConnections.add(inConnection);
	}


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<Connection> getInConnections() {
		return inConnections;
	}

	public double getOutput() {
		return output;
	}

	public void setOutput(double output) {
		this.output = output;
	}

	public void setInConnections(List<Connection> inConnections) {
		this.inConnections = inConnections;
	}

	public Connection getBias() {
		return bias;
	}

	public void setBias(Connection bias) {
		this.bias = bias;
	}

	public int getNumberOfConnections() {
		return numberOfConnections;
	}

	public void setNumberOfConnections(int numberOfConnections) {
		this.numberOfConnections = numberOfConnections;
	}

	@Override
	public String toString() {
		NumberFormat FORMATTER = new DecimalFormat("#0.000");
		return "Neuron [id=" + id + ", output=" + FORMATTER.format(output) + ", inConnections=" + inConnections
				+ ", bias=" + bias + "]";
	}

}
