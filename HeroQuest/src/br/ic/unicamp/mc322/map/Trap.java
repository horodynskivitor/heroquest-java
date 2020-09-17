package br.ic.unicamp.mc322.map;

import br.ic.unicamp.mc322.actioninterfaces.Discoverable;
import br.ic.unicamp.mc322.aliveobjects.Hero;
import br.ic.unicamp.mc322.engine.MapObjectVisitor;

public class Trap extends MapObject implements Discoverable {

	private final int TRAP_DAMAGE = 2;

	public Trap(int x, int y) {
		super(x, y);
	}

	@Override
	public void accept(MapObjectVisitor visitor) {
		visitor.visit(this);
	}

	@Override
	public int discover(Hero player) {
		int defense = player.defendAgainstAttack();
		int damage = TRAP_DAMAGE - defense;
		if (damage > 0)
			player.changeLifePointsByAmount(-damage);
		return damage;
	}

}
