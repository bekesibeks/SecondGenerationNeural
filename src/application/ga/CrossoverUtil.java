package application.ga;

import static application.shared.Constants.AMOUNT_OF_MUTATION;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import application.ga.model.Genome;
import application.shared.Constants;
import application.shared.RandomUtil;

public class CrossoverUtil {

	/*
	 * Select the top 4 genome. Crossover them twice to make 12 child. Create 4 more
	 * totally random child to keep up the diversity. Desired result : 12 well
	 * fitness individual, 4 random
	 */
	public static List<Genome> crossoverGenomes(List<Genome> originalPopulation) {
		Collections.sort(originalPopulation);

		List<Genome> newPopulation = new ArrayList<>();

		List<Genome> bestGenomes = selectBestGenomes(originalPopulation);

		for (int i = 0; i < bestGenomes.size() - 1; i++) {
			List<Genome> firstChilds = crossover(bestGenomes.get(i), bestGenomes.get(i + 1));
			List<Genome> secondChilds = crossover(bestGenomes.get(i), bestGenomes.get(i + 1));
			newPopulation.addAll(firstChilds);
			newPopulation.addAll(secondChilds);
		}

		Genome randomGenome1 = originalPopulation.get((int) (Math.random() * originalPopulation.size()));
		Genome randomGenome2 = originalPopulation.get((int) (Math.random() * originalPopulation.size()));
		Genome randomGenome3 = originalPopulation.get((int) (Math.random() * originalPopulation.size()));
		Genome randomGenome4 = originalPopulation.get((int) (Math.random() * originalPopulation.size()));

		newPopulation.addAll(crossover(randomGenome1, randomGenome2));
		newPopulation.addAll(crossover(randomGenome3, randomGenome4));

		newPopulation.stream().forEach(CrossoverUtil::mutate);
		return newPopulation;
	}

	/*
	 * TODO -> ugly, rewrote it, after test phase
	 */
	private static List<Genome> crossover(Genome parent1, Genome parent2) {
		List<Genome> childs = new ArrayList<>();
		Genome child1 = new Genome();
		Genome child2 = new Genome();
		List<Double> child1Weights = new ArrayList<>();
		List<Double> child2Weights = new ArrayList<>();

		int crossoverPoint = (int) (Math.random() * parent1.getWeights().size());
		for (int i = 0; i < parent1.getWeights().size(); i++) {
			if (i <= crossoverPoint) {
				child1Weights.add(new Double(parent1.getWeights().get(i)));
				child2Weights.add(new Double(parent2.getWeights().get(i)));
			} else {
				child1Weights.add(new Double(parent2.getWeights().get(i)));
				child2Weights.add(new Double(parent1.getWeights().get(i)));
			}
		}
		child1.setWeights(child1Weights);
		child2.setWeights(child2Weights);
		childs.add(child1);
		childs.add(child2);

		return childs;
	}

	private static Genome mutate(Genome genome) {
		for (int i = 0; i < genome.getWeights().size(); i++) {
			if (Math.random() < Constants.PROBABILITY_OF_MUTATION) {
				Double currentWeight = genome.getWeights().get(i);
				Double newWeight = currentWeight + RandomUtil.getRandomInRange(AMOUNT_OF_MUTATION);
				if (newWeight.doubleValue() < -1) {
					newWeight = -1.0;
				} else if (newWeight.doubleValue() > 1) {
					newWeight = 1.0;
				}
				genome.getWeights().set(i, newWeight);
			}
		}
		return genome;
	}

	private static List<Genome> selectBestGenomes(List<Genome> originalPopulation) {
		return originalPopulation.subList(0, 4);
	}

}
