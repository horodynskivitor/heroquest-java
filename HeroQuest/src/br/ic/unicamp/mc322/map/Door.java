package br.ic.unicamp.mc322.map;

import br.ic.unicamp.mc322.engine.MapObjectVisitor;

public class Door extends MapObject {

	Door(int x, int y) {
		super(x, y);
	}

	@Override
	public void accept(MapObjectVisitor visitor) {
		visitor.visit(this);
	}

}
