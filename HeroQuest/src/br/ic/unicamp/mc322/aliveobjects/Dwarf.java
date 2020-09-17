package br.ic.unicamp.mc322.aliveobjects;

import br.ic.unicamp.mc322.engine.MapObjectVisitor;
import br.ic.unicamp.mc322.weapons.ShortSword;

public class Dwarf extends Hero {
	private final boolean SPELL_THROWER = false;
	private final int START_ATTACK_DICE = 2, START_DEFENSE_DICE = 2, START_HEALTH = 7, START_INTELLIGENCE = 3;
	private final int START_PERCEPTION = 1;

	public Dwarf(int x, int y) {
		super(x, y);
		attackDice = START_ATTACK_DICE;
		defenseDice = START_DEFENSE_DICE;
		healthPoints = START_HEALTH;
		intelligence = START_INTELLIGENCE;
		perception = START_PERCEPTION;
		canThrowSpell = SPELL_THROWER;
		weapons.add(new ShortSword());
	}

	@Override
	public void accept(MapObjectVisitor visitor) {
		visitor.visit(this);
	}
}
