package application.ga;

public class Connection {

	private double weight;
	private Neuron fromNeuron;
	private Neuron toNeuron;

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public Neuron getFromNeuron() {
		return fromNeuron;
	}

	public void setFromNeuron(Neuron fromNeuron) {
		this.fromNeuron = fromNeuron;
	}

	public Neuron getToNeuron() {
		return toNeuron;
	}

	public void setToNeuron(Neuron toNeuron) {
		this.toNeuron = toNeuron;
	}

	@Override
	public String toString() {
		StringBuilder toString = new StringBuilder("");
		toString.append(fromNeuron.getId());
		toString.append(" Weight: ");
		toString.append(weight);

		return toString.toString();
	}

}
