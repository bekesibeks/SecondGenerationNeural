package application.ga;

import static application.shared.Constants.NETWORK_MAX_FITNESS;
import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.List;

import application.ga.model.Genome;

public class RouletteWheelSelection {

	public static List<Genome> rouletteWheelSelection(List<Genome> genomes, int count) {
		List<Double> weight = genomes.stream().map(genome -> genome.getFitness() / NETWORK_MAX_FITNESS)
				.collect(toList());
		List<Genome> selectedGenomes = new ArrayList<>();
		for (int i = 0; i < count; i++) {
			selectedGenomes.add(genomes.get(rouletteSelect(weight)));
		}
		return selectedGenomes;
	}

	private static int rouletteSelect(List<Double> weight) {

		double sumOfWeights = 0;
		for (int i = 0; i < weight.size(); i++) {
			sumOfWeights += weight.get(i);
		}
		double value = randUniformPositive() * sumOfWeights;
		for (int i = 0; i < weight.size(); i++) {
			value -= weight.get(i);
			if (value <= 0) {
				return i;
			}
		}
		return 0;
	}

	private static double randUniformPositive() {
		return Math.random();
	}

}
