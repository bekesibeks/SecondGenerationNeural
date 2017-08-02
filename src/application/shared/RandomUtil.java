package application.shared;

import static application.shared.Constants.NETWORK_WEIGHT_RANGE;

public class RandomUtil {

	public static double getRandomInRange(double range) {
		return (Math.random() * (range * 2) - range);
	}

	/*
	 * Connection weight range : (-NETWORK_WEIGHT_RANGE,NETWORK_WEIGHT_RANGE)
	 */
	public static double getRandomWeight() {
		return (Math.random() * (NETWORK_WEIGHT_RANGE * 2)) - NETWORK_WEIGHT_RANGE;
	}

}
