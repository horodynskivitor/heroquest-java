package br.ic.unicamp.mc322.actioninterfaces;

import java.util.ArrayList;

import br.ic.unicamp.mc322.map.Direction;
import br.ic.unicamp.mc322.map.MapObject;

public interface Movable {
	public boolean move(Direction direction, ArrayList<MapObject> colisionElements);
}
