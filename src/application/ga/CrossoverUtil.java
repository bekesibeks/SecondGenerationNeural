package application.ga;

import static application.ga.RouletteWheelSelection.rouletteWheelSelection;
import static application.shared.Constants.MUTATION_AMOUNT;
import static application.shared.Constants.NETWORK_POPULATION_SIZE;
import static application.shared.Constants.NETWORK_WEIGHT_RANGE;
import static application.shared.RandomUtil.getRandomInRange;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import application.ga.model.Genome;
import application.shared.Constants;

public class CrossoverUtil {

	/*
	 * Select the top 2 genome. Crossover them twice to make 4 child. Then apply
	 * class roulett wheel selection. Add 1 tottaly random genome to keep up
	 * diversity
	 */
	public static List<Genome> crossoverGenomes(List<Genome> originalPopulation) {
		Collections.sort(originalPopulation);

		List<Genome> newPopulation = new ArrayList<>();
		List<Genome> bestGenomes = selectBestGenomes(originalPopulation);

		for (int i = 0; i < bestGenomes.size() - 1; i++) {
			for (int j = i + 1; j < bestGenomes.size(); j++) {
				List<Genome> firstChilds1 = crossover(bestGenomes.get(i), bestGenomes.get(j));
				List<Genome> firstChilds2 = crossover(bestGenomes.get(i), bestGenomes.get(j));
				newPopulation.addAll(firstChilds1);
				newPopulation.addAll(firstChilds2);
			}
		}
		newPopulation.add(bestGenomes.get(0));

		List<Genome> rouletteWheelSelection = rouletteWheelSelection(originalPopulation,
				NETWORK_POPULATION_SIZE - newPopulation.size() - 1);
		for (int i = 0; i < rouletteWheelSelection.size() - 1; i += 2) {
			List<Genome> firstChilds = crossover(rouletteWheelSelection.get(i), rouletteWheelSelection.get(i + 1));
			newPopulation.addAll(firstChilds);
		}

		Genome randomGenome = new Genome(bestGenomes.get(0).getWeights().size());
		newPopulation.add(randomGenome);
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
			if (Math.random() < Constants.MUTATION_PROBABILITY) {
				Double currentWeight = genome.getWeights().get(i);
				Double mutation = getRandomInRange(MUTATION_AMOUNT * NETWORK_WEIGHT_RANGE);
				Double newWeight = currentWeight + mutation;
				genome.getWeights().set(i, newWeight);
			}
		}
		return genome;
	}

	private static List<Genome> selectBestGenomes(List<Genome> originalPopulation) {
		return originalPopulation.subList(0, 2);
	}

}
