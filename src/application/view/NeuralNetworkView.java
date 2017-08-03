package application.view;

import static application.shared.Constants.NETWORK_HIDDEN_LAYER_SIZE;
import static application.shared.Constants.NETWORK_INPUT_LAYER_SIZE;
import static application.shared.Constants.NETWORK_OUTPUT_LAYER_SIZE;

import java.util.ArrayList;
import java.util.List;

import application.ga.model.Network;
import application.shared.Constants;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

public class NeuralNetworkView {

	private static final int CIRCLE_SIZE = 10;
	private static final int GRAP_BETWEEN_CIRCLES = 30;

	public static List<DoubleProperty> inputLayerOutputs;
	public static List<DoubleProperty> hiddenLayerOutputs;
	public static List<DoubleProperty> outputLayerOutputs;

	private static List<Circle> inputLayer;
	private static List<Circle> hiddenLayer;
	private static List<Circle> outputLayer;
	
	private static List<Line> lines;

	public NeuralNetworkView() {
		lines =  new ArrayList<>();
		inputLayer = new ArrayList<>();
		hiddenLayer = new ArrayList<>();
		outputLayer = new ArrayList<>();

		inputLayerOutputs = new ArrayList<>();
		hiddenLayerOutputs = new ArrayList<>();
		outputLayerOutputs = new ArrayList<>();

		for (int i = 0; i < NETWORK_INPUT_LAYER_SIZE; i++) {
			DoubleProperty property = new SimpleDoubleProperty();
			inputLayerOutputs.add(property);

			Circle neuron = new Circle(CIRCLE_SIZE);
			neuron.setFill(Color.RED);
			neuron.setTranslateX(30 + i * GRAP_BETWEEN_CIRCLES);
			inputLayer.add(neuron);

			neuron.opacityProperty().bind(property.multiply(Constants.NETWORK_WEIGHT_RANGE).add(0.2));
		}

		for (int i = 0; i < NETWORK_HIDDEN_LAYER_SIZE; i++) {
			DoubleProperty property = new SimpleDoubleProperty();
			hiddenLayerOutputs.add(property);

			Circle neuron = new Circle(CIRCLE_SIZE);
			neuron.setFill(Color.RED);
			neuron.setTranslateX(-10 + i * GRAP_BETWEEN_CIRCLES);
			neuron.setTranslateY(120);
			hiddenLayer.add(neuron);

			neuron.opacityProperty().bind(property.multiply(Constants.NETWORK_WEIGHT_RANGE).add(0.2));
		}

		for (int i = 0; i < NETWORK_OUTPUT_LAYER_SIZE; i++) {
			DoubleProperty property = new SimpleDoubleProperty();
			outputLayerOutputs.add(property);

			Circle neuron = new Circle(CIRCLE_SIZE);
			neuron.setFill(Color.RED);
			neuron.setTranslateX(80+i * GRAP_BETWEEN_CIRCLES);
			neuron.setTranslateY(240);
			outputLayer.add(neuron);

			neuron.opacityProperty().bind(property.add(0.2));
		}
		
		lines.addAll(buildWeights(inputLayer,hiddenLayer));
		lines.addAll(buildWeights(hiddenLayer,outputLayer));
		

	}
	
	public List<Line> buildWeights(List<Circle> layer1, List<Circle> layer2){
		List<Line> lines = new ArrayList<>();
		for(Circle outerCircle : layer1) {
			for(Circle innerCircle : layer2) {
				Line line= new Line(outerCircle.getTranslateX(),outerCircle.getTranslateY(),innerCircle.getTranslateX(),innerCircle.getTranslateY());
				line.setStroke(Color.YELLOW);
				line.setOpacity(0.4);
				lines.add(line);
			}
		}
		return lines;
	}

	public Group buildView() {
		Group group = new Group();
		group.getChildren().addAll(inputLayer);
		group.getChildren().addAll(hiddenLayer);
		group.getChildren().addAll(outputLayer);
		group.getChildren().addAll(lines);
		return group;
	}

}
