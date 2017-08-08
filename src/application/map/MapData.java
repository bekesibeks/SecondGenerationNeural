package application.map;

public class MapData {

	private double leftLineDistance;
	private double rightLineDistance;
	private double rightFrontLineDistance;
	private double leftFrontLineDistance;
	private double frontLineDistance;

	private boolean isAlive;

	public boolean isAlive() {
		return isAlive;
	}

	public void setAlive(boolean isAlive) {
		this.isAlive = isAlive;
	}

	public double getLeftLineDistance() {
		return leftLineDistance;
	}

	public void setLeftLineDistance(double leftLineDistance) {
		this.leftLineDistance = leftLineDistance;
	}

	public double getRightLineDistance() {
		return rightLineDistance;
	}

	public void setRightLineDistance(double rightLineDistance) {
		this.rightLineDistance = rightLineDistance;
	}

	public double getRightFrontLineDistance() {
		return rightFrontLineDistance;
	}

	public void setRightFrontLineDistance(double rightFrontLineDistance) {
		this.rightFrontLineDistance = rightFrontLineDistance;
	}

	public double getLeftFrontLineDistance() {
		return leftFrontLineDistance;
	}

	public void setLeftFrontLineDistance(double leftFrontLineDistance) {
		this.leftFrontLineDistance = leftFrontLineDistance;
	}

	public double getFrontLineDistance() {
		return frontLineDistance;
	}

	public void setFrontLineDistance(double frontLineDistance) {
		this.frontLineDistance = frontLineDistance;
	}

}
