package application.ga.model;

import static application.shared.RandomUtil.getRandomWeight;

public class Connection {

	private double weight;
	
	public Connection() {
		this.weight = getRandomWeight();
	}
	
	public Connection(double weight) {
		this.weight = weight;
	}
	
	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	@Override
	public String toString() {
		StringBuilder toString = new StringBuilder("");
		toString.append(" Weight: ");
		toString.append(weight);
		return toString.toString();
	}

}
