package application.ga;

public class RandomUtil {
	
	public static double getRandomInRange(int range) {
		return (Math.random()*(range*2) -range);
	}
	
}
