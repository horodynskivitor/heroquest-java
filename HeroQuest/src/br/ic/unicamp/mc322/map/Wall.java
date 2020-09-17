package br.ic.unicamp.mc322.map;

import br.ic.unicamp.mc322.engine.MapObjectVisitor;

public class Wall extends MapObject {
	Wall(int x, int y) {
		super(x, y);
	}

	@Override
	public void accept(MapObjectVisitor visitor) {
		visitor.visit(this);
	}
}
