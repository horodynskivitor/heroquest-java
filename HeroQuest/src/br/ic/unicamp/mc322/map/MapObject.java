package br.ic.unicamp.mc322.map;

import br.ic.unicamp.mc322.engine.MapObjectVisitor;

public abstract class MapObject {
	private Coordinate coordinate;

	public MapObject(int x, int y) {
		coordinate = new Coordinate(x, y);
	}

	public abstract void accept(MapObjectVisitor visitor);

	public boolean areCoordinatesAdjcent(MapObject object) {
		return coordinate.areCoordinatesAdjcent(object.getCoordinate());
	}

	protected Coordinate getCoordinate() {
		return coordinate;
	}

	public double getEuclideanDistanceTo(MapObject target) {
		Coordinate targetCoordinate = target.getCoordinate();
		return coordinate.getEuclideanDistanceTo(targetCoordinate);
	}

	public int getX() {
		return coordinate.getX();
	}

	public int getY() {
		return coordinate.getY();
	}

	public boolean isSameCoordinates(Coordinate anotherCoordinate) {
		return coordinate.equals(anotherCoordinate);
	}

	public boolean isSameCoordinates(int x, int y) {
		return coordinate.isSameCoordinates(x, y);
	}

	public boolean isSameCoordinates(MapObject object) {
		Coordinate coord = object.getCoordinate();
		return isSameCoordinates(coord);
	}
}
