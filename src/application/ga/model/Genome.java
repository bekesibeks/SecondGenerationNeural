package application.ga.model;

import java.util.ArrayList;
import java.util.List;

import application.shared.RandomUtil;

public class Genome implements Comparable<Genome> {

	/*
	 * Basically the chromosomes
	 */
	private List<Double> weights;
	private double fitness;

	public Genome() {
	}
	
	public Genome(int numberOfWeights){
		weights = new ArrayList<>();
		for(int i=0;i<numberOfWeights;i++){
			weights.add(RandomUtil.getRandomWeight());
		}
	}

	public List<Double> getWeights() {
		return weights;
	}

	public void setWeights(List<Double> weights) {
		this.weights = weights;
	}

	public double getFitness() {
		return fitness;
	}

	public void setFitness(double fitness) {
		this.fitness = fitness;
	}
	@Override
	public String toString() {
		return "\ngenome: "+weights;
	}
	
	@Override
	public int compareTo(Genome o) {
		return Double.compare(o.fitness, fitness);
	}

}
