package application.ga;

import static application.ga.RandomUtil.getRandomWeight;

public class Connection {

	private double weight;
	
	public Connection() {
		this.weight = getRandomWeight();
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
