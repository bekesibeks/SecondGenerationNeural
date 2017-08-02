package application.shared;

public class RandomUtil {

	private static int no = 0;

	public static double getRandomInRange(double range) {
		return (Math.random() * (range * 2) - range);
	}

	/*
	 * Connection weight range : (-1,1)
	 */
	public static double getRandomWeight() {
		// return no++;
		// return 1.0;
		return (Math.random() * Constants.WEIGHT_RANGE) - Constants.WEIGHT_RANGE / 2.0d;
	}

}
