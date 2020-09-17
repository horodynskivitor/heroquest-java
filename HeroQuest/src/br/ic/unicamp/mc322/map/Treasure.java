package br.ic.unicamp.mc322.map;

import br.ic.unicamp.mc322.actioninterfaces.Discoverable;
import br.ic.unicamp.mc322.aliveobjects.Hero;
import br.ic.unicamp.mc322.engine.MapObjectVisitor;
import br.ic.unicamp.mc322.engine.RandomNumberGenerator;

public class Treasure extends MapObject implements Discoverable {

	public Treasure(int x, int y) {
		super(x, y);
	}

	@Override
	public void accept(MapObjectVisitor visitor) {
		visitor.visit(this);
	}

	@Override
	public int discover(Hero player) {
		RandomNumberGenerator.giveTreasure(player);
		return 0;
	}

}
