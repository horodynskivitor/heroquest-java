package br.ic.unicamp.mc322.aliveobjects;

import br.ic.unicamp.mc322.engine.MapObjectVisitor;
import br.ic.unicamp.mc322.weapons.LongSword;

public class Barbarian extends Hero {
	private final boolean SPELL_THROWER = false;
	private final int START_ATTACK_DICE = 3, START_DEFENSE_DICE = 2, START_HEALTH = 8, START_INTELLIGENCE = 2;
	private final int START_PERCEPTION = 2;

	public Barbarian(int x, int y) {
		super(x, y);
		attackDice = START_ATTACK_DICE;
		defenseDice = START_DEFENSE_DICE;
		healthPoints = START_HEALTH;
		intelligence = START_INTELLIGENCE;
		perception = START_PERCEPTION;
		canThrowSpell = SPELL_THROWER;
		weapons.add(new LongSword());
	}

	@Override
	public void accept(MapObjectVisitor visitor) {
		visitor.visit(this);
	}

}
