package mapcreator;

import static java.util.Objects.nonNull;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.shape.Line;

public class MapLoader {

	public static final void saveMap(List<Line> lines, String mapName) {
		try {
			File file = new File("./" + mapName + ".txt");
			if (!file.exists()) {
				file.createNewFile();
			}

			FileWriter fw = new FileWriter(file.getAbsoluteFile(), true);
			BufferedWriter bw = new BufferedWriter(fw);
			for (Line line : lines) {
				bw.write(
						line.getStartX() + "," + line.getStartY() + "," + line.getEndX() + "," + line.getEndY() + "\n");
			}
			bw.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static List<Line> loadMap(String mapName) {
		List<Line> lines = new ArrayList<>();
		try {
			File file = new File("./" + mapName + ".txt");
			FileReader reader = new FileReader(file.getAbsolutePath());
			BufferedReader bw = new BufferedReader(reader);
			String line = bw.readLine();
			while (nonNull(line) && !line.isEmpty()) {
				String[] pointsString = line.split(",");
				double[] points = new double[pointsString.length];
				for (int i = 0; i < pointsString.length; i++) {
					points[i] = Double.parseDouble(pointsString[i]);
				}
				Line newline = new Line(points[0], points[1], points[2], points[3]);
				lines.add(newline);

				line = bw.readLine();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lines;
	}

}
