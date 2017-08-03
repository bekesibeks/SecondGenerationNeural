package application.shared;

import static java.util.Objects.nonNull;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import application.ga.NetworkGenomeConverter;
import application.ga.model.Genome;
import application.ga.model.Network;
import javafx.scene.shape.Line;

public class NetworkLoader {

	private static final NetworkGenomeConverter converter = new NetworkGenomeConverter();

	public static void saveNetwork(Network network, double fitness) {
		Genome genome = converter.buildGenomeFromNetwork(network);

		try {
			File file = new File("./top-fit-network.txt");
			file.createNewFile();

			FileWriter fw = new FileWriter(file.getAbsoluteFile(), false);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(fitness + "\n");
			bw.write(genome.getWeights().toString());
			bw.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static Network loadNetwork() {
		Genome genome = new Genome();
		try {
			File file = new File("./top-fit-network.txt");
			FileReader reader = new FileReader(file.getAbsolutePath());
			BufferedReader bw = new BufferedReader(reader);
			String fitness = bw.readLine();
			genome.setFitness(Double.valueOf(fitness));
			String weights = bw.readLine();
			weights = weights.replace(" ", "").replace("[", "").replace("]", "");
			String[] split = weights.split(",");
			List<Double> genomeWeights = new ArrayList<>();
			for(int i = 0; i<split.length;i++){
				genomeWeights.add(Double.valueOf(split[i]));
			}
			genome.setWeights(genomeWeights);
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return converter.buildNetworkFromGenome(genome);
	}

}
