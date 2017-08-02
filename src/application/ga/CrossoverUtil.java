package application.ga;

import static application.ga.RouletteWheelSelection.rouletteWheelSelection;
import static application.shared.Constants.AMOUNT_OF_MUTATION;
import static application.shared.Constants.NETWORK_POPULATION_SIZE;
import static application.shared.RandomUtil.getRandomInRange;

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
		/*
		 * 4 db
		 */
		for (int i = 0; i < bestGenomes.size() - 1; i++) {
			for(int j = i+1; j<bestGenomes.size(); j++){
				List<Genome> firstChilds = crossover(bestGenomes.get(i), bestGenomes.get(j));
				List<Genome> firstChilds2 = crossover(bestGenomes.get(i), bestGenomes.get(j));
				newPopulation.addAll(firstChilds);
				newPopulation.addAll(firstChilds2);
			}
			
		}
		List<Genome> rouletteWheelSelection = rouletteWheelSelection(originalPopulation,NETWORK_POPULATION_SIZE-4);
//		System.out.println("Roulette wheel selected genomes : "+rouletteWheelSelection);
		for (int i = 0; i < rouletteWheelSelection.size() - 1; i+=2) {
			List<Genome> firstChilds = crossover(rouletteWheelSelection.get(i), rouletteWheelSelection.get(i + 1));
			newPopulation.addAll(firstChilds);
		}
	
		
//
//		Genome randomGenome1 = originalPopulation.get((int) (Math.random() * originalPopulation.size()));
//		Genome randomGenome2 = new Genome(randomGenome1.getWeights().size());
//
//		newPopulation.addAll(crossover(randomGenome1, randomGenome2));
//		newPopulation.addAll(crossover(bestGenomes.get(0), bestGenomes.get(0)));

		newPopulation.stream().forEach(CrossoverUtil::mutate);
		newPopulation.add(bestGenomes.get(0));	
		newPopulation.add(mutate(bestGenomes.get(0)));
		
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
				Double mutation = getRandomInRange(AMOUNT_OF_MUTATION * Constants.WEIGHT_RANGE);
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
