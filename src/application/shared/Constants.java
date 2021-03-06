package application.shared;

/*
 * TODO -> extract this shit to property file
 */
public class Constants {

	/*
	 * Car related properties
	 */
	public static final int CAR_DEFAULT_LENGTH = 40;
	public static final int CAR_DEFAULT_WIDTH = 20;

	public static final int CAR_DEFAULT_X_COORDINATE = 950;
	public static final int CAR_DEFAULT_Y_COORDINATE = 40;

	// public static final int CAR_DEFAULT_X_COORDINATE = 980;
	// public static final int CAR_DEFAULT_Y_COORDINATE = 30;

	public static final int CAR_DEFAULT_DIRECTION = 180; // left
	public static final int CAR_RADAR_RANGE = 500;
	public static final int CAR_RADAR_RANGE_DIAGONAL = (int) Math.sqrt(CAR_RADAR_RANGE * CAR_RADAR_RANGE / 2);

	public static final double CAR_DEFAULT_SPEED = 10;
	public static final double CAR_MAX_ROTATION = 8;

	/*
	 * Neural network related stuff
	 */
	public static final double NETWORK_DEFAULT_BIAS = -1;
	public static final double NETWORK_WEIGHT_RANGE = 16;

	public static final int NETWORK_INPUT_LAYER_SIZE = 5;
	public static final int NETWORK_HIDDEN_LAYER_SIZE = 8;
	public static final int NETWORK_OUTPUT_LAYER_SIZE = 2;

	public static final int NETWORK_POPULATION_SIZE = 20;
	public static final double NETWORK_MAX_FITNESS = 7000.0d;

	public static final double MUTATION_PROBABILITY = 0.15;
	public static final double MUTATION_AMOUNT = 0.20;

	public static final double DEFAULT_FRAME_RATE = 85;

	public static final double MAP_HEIGHT = 700;
	public static final double MAP_WIDTH = 1100;
	public static boolean SETTINGS_LOAD_PRETRAINED_NETWORK = false;

	public static final boolean DEBUG_MODE = true;
	public static int MAP_INDEX = 0;
	public static final String[] MAPS = { "map-easy", "map-medium", "map-hard" };

}
