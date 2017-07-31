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
//		return no++;
//		return 1.0;
		return (Math.random() * 2) - 1.0;
	}

}
