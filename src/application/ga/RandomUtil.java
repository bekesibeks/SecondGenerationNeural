package application.ga;

public class RandomUtil {

	public static double getRandomInRange(int range) {
		return (Math.random() * (range * 2) - range);
	}

	/*
	 * Connection weight range : (-1,1)
	 */
	public static double getRandomWeight() {
		return 1.0;
//		return (Math.random() * 2) - 1.0;
	}

}
