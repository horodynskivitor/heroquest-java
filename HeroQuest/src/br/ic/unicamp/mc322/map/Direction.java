package br.ic.unicamp.mc322.map;

import java.util.ArrayList;
import java.util.List;

public enum Direction {
	DOWN, LEFT, RIGHT, UP;

	public static List<Direction> getDirectionInListFormat() {
		List<Direction> listingDirections = new ArrayList<Direction>();
		for (Direction d : Direction.values())
			listingDirections.add(d);
		return listingDirections;
	}
}
