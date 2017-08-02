package application.shared;

/*
 * TODO -> extract this shit to property file
 */
public class Constants {

	/*
	 * Car related properties
	 */
	public static final int CAR_DEFAULT_LENGTH = 80;
	public static final int CAR_DEFAULT_WIDTH = 40;
	public static final int CAR_DEFAULT_X_COORDINATE = 550;
	public static final int CAR_DEFAULT_Y_COORDINATE = 100;
	public static final int CAR_DEFAULT_DIRECTION = 180; // left
	public static final double CAR_DEFAULT_SPEED = 10;
	public static final int CAR_RADAR_RANGE = 600;
	public static final int CAR_RADAR_RANGE_DIAGONAL = (int) Math.sqrt(CAR_RADAR_RANGE * CAR_RADAR_RANGE / 2);
	public static final double CAR_MAX_ROTATION = 5;

	/*
	 * Neural network related stuff
	 */
	public static final double NETWORK_DEFAULT_BIAS = -1;
	public static final int NETWORK_INPUT_LAYER_SIZE = 5;
	public static final int NETWORK_HIDDEN_LAYER_SIZE = 8;
	public static final int NETWORK_OUTPUT_LAYER_SIZE = 2;
	public static final int NETWORK_POPULATION_SIZE = 12;
	public static final double NETWORK_MAX_FITNESS = 3000.0d;
	public static final double NETWORK_WEIGHT_RANGE = 5;
	
	public static final double MUTATION_PROBABILITY = 0.15;
	public static final double MUTATION_AMOUNT = 0.2;
	
	public static final double DEFAULT_FRAME_RATE = 10;
}
