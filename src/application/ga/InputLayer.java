package application.ga;

import static application.shared.Constants.CAR_RADAR_RANGE;

import java.util.ArrayList;
import java.util.List;

public class InputLayer {

	/*
	 * TODO -> Remove this after test phase
	 */
	private List<Double> inputs;

	public InputLayer() {
		inputs = new ArrayList<>();
	}

	public List<Double> getNormalisedInput(List<Double> inputs) {
		/*
		 * Order!!
		 */
		List<Double> normalisedInput = new ArrayList<>();
		for (int i = 0; i < inputs.size(); i++) {
			normalisedInput.add(normaliseInput(inputs.get(i)));
		}
		this.inputs = normalisedInput;
		return normalisedInput;
	}

	private Double normaliseInput(Double input) {
		double normalised = input / CAR_RADAR_RANGE;
		return 1 - normalised;
	}

	@Override
	public String toString() {
		return inputs.toString();
	}

}
