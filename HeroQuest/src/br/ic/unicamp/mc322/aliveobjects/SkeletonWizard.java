package br.ic.unicamp.mc322.aliveobjects;

import br.ic.unicamp.mc322.engine.MapObjectVisitor;
import br.ic.unicamp.mc322.map.Coordinate;
import br.ic.unicamp.mc322.spells.Spell;

public class SkeletonWizard extends Skeleton {
	private final int MAGIC_MISSILE_START_NUMBER = 1;
	private final boolean SPELL_THROWER = true;
	private final int START_ATTACK_DICE = 1, START_DEFENSE_DICE = 1, START_HEALTH = 4, START_INTELLIGENCE = 3;
	private final int START_PERCEPTION = 4;

	public SkeletonWizard(int x, int y) {
		super(x, y);
		attackDice = START_ATTACK_DICE;
		defenseDice = START_DEFENSE_DICE;
		healthPoints = START_HEALTH;
		intelligence = START_INTELLIGENCE;
		perception = START_PERCEPTION;
		canThrowSpell = SPELL_THROWER;
		spells.put(Spell.MAGIC_MISSILE, MAGIC_MISSILE_START_NUMBER);
	}

	@Override
	public void accept(MapObjectVisitor visitor) {
		visitor.visit(this);
	}

	@Override
	public String toString() {
		Coordinate coord = getCoordinate();
		return "Esqueleto Mago. Posição x = " + coord.getX() + " e y = " + coord.getY();
	}
}
