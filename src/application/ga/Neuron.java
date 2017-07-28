package application.ga;

import static application.ga.RandomUtil.getRandomInRange;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class Neuron {

	private String id;

	private double output;
	private double threshold;
	private List<Connection> inConnections;

	public Neuron(String id) {
		this.id = id;
		inConnections = new ArrayList<>();
		threshold = RandomUtil.getRandomInRange(250);
	}

	public void activate() {
		double sumOfInputs = 0;
		for (Connection connection : inConnections) {
			Neuron connectedNeuron = connection.getFromNeuron();

			double weight = connection.getWeight();
			double connectedNeuronOutput = connectedNeuron.getOutput();

			sumOfInputs += (weight * connectedNeuronOutput);
		}

		sumOfInputs += threshold;

		output = sigmoid(sumOfInputs);
	}

	public void addNeuronsAsConnection(List<Neuron> neurons) {
		neurons.stream().forEach(this::buildConnectionToNeuron);
	}

	public void addConnection(Connection inConnection) {
		inConnections.add(inConnection);
	}

	public static double sigmoid(double x) {
		return (1.0 / (1.0 + (Math.exp(-x))));
	}

	private void buildConnectionToNeuron(Neuron neuron) {
		Connection connection = new Connection();
		connection.setFromNeuron(neuron);
		connection.setToNeuron(this);
		connection.setWeight(getRandomInRange(1));
		inConnections.add(connection);
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

	public double getThreshold() {
		return threshold;
	}

	public void setThreshold(double threshold) {
		this.threshold = threshold;
	}

	@Override
	public String toString() {
		NumberFormat FORMATTER = new DecimalFormat("#0.000");
		return "Neuron [id=" + id + ", output=" + FORMATTER.format(output) + ", inConnections=" + inConnections + ", threshold="
				+ threshold + "]";
	}

}
