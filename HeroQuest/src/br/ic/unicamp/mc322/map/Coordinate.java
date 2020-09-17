package br.ic.unicamp.mc322.map;

public class Coordinate {
	private int x, y;

	public Coordinate(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public boolean areCoordinatesAdjcent(Coordinate anotherCoordinate) {
		return (Math.abs(x - anotherCoordinate.getX()) == 1 && Math.abs(y - anotherCoordinate.getY()) == 0
				|| Math.abs(x - anotherCoordinate.getX()) == 0 && Math.abs(y - anotherCoordinate.getY()) == 1);
	}

	public void changeCoordinates(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public boolean equals(Coordinate anotherCoordinate) {
		return isSameCoordinates(anotherCoordinate.getX(), anotherCoordinate.getY());
	}

	public double getEuclideanDistanceTo(Coordinate anotherCoordinate) {
		return Math.sqrt(Math.pow(x - anotherCoordinate.getX(), 2) + Math.pow(y - anotherCoordinate.getY(), 2));
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public boolean isSameCoordinates(int x, int y) {
		return (this.x == x && this.y == y);
	}
}
