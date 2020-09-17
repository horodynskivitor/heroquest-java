package br.ic.unicamp.mc322.spells;

import java.util.ArrayList;

import br.ic.unicamp.mc322.engine.RandomNumberGenerator;
import br.ic.unicamp.mc322.map.Coordinate;

public class Teleport extends Magic {

	private final int RANGE = Integer.MAX_VALUE;

	public Teleport() {
		range = RANGE;
	}

	public void use(Coordinate objectCoordinate, ArrayList<Coordinate> availableCoordinates) {
		Coordinate coord = RandomNumberGenerator.getRandomElementFromArrayList(availableCoordinates);
		objectCoordinate.changeCoordinates(coord.getX(), coord.getY());
	}
}
