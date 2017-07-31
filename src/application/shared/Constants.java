package application.shared;

/*
 * TODO -> extract this shit to property file
 */
public class Constants {
	
	/*
	 * Car related properties
	 */
	public static final int CAR_DEFAULT_LENGTH = 70;
	public static final int CAR_DEFAULT_WIDTH = 40;
	public static final int CAR_DEFAULT_X_COORDINATE = 450;
	public static final int CAR_DEFAULT_Y_COORDINATE = 120;
	public static final int CAR_DEFAULT_DIRECTION = 180; //left
	public static final double CAR_DEFAULT_SPEED = 10;
	public static final int CAR_RADAR_RANGE = 300;
	public static final int CAR_RADAR_RANGE_DIAGONAL= (int) Math.sqrt(CAR_RADAR_RANGE*CAR_RADAR_RANGE/2);
	
	
	public static final double DEFAULT_FRAME_RATE = 30;
	public static final double DEFAULT_BIAS = -1.0;
	
	/*
	 * Neural network related stuff
	 */
	public static final int NETWORK_INPUT_LAYER_SIZE=5;
	public static final int NETWORK_HIDDEN_LAYER_SIZE=8;
	public static final int NETWORK_OUTPUT_LAYER_SIZE=2;
	public static final int NETWORK_POPULATION_SIZE = 15;
	
	
	
}
