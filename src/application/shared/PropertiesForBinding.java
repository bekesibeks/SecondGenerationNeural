package application.shared;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class PropertiesForBinding {

	public static DoubleProperty steerRotateProperty = new SimpleDoubleProperty();

	public static IntegerProperty populationProperty = new SimpleIntegerProperty();

	public static DoubleProperty topFitnessProperty = new SimpleDoubleProperty();
}
