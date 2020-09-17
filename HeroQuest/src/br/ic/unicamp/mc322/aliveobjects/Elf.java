package br.ic.unicamp.mc322.aliveobjects;

import br.ic.unicamp.mc322.engine.MapObjectVisitor;
import br.ic.unicamp.mc322.spells.Spell;
import br.ic.unicamp.mc322.weapons.ShortSword;

public class Elf extends Hero {
	private final int SIMPLE_HEAL_START_NUMBER = 1;
	private final boolean SPELL_THROWER = true;
	private final int START_ATTACK_DICE = 2, START_DEFENSE_DICE = 2, START_HEALTH = 6, START_INTELLIGENCE = 4;
	private final int START_PERCEPTION = 4;

	public Elf(int x, int y) {
		super(x, y);
		attackDice = START_ATTACK_DICE;
		defenseDice = START_DEFENSE_DICE;
		healthPoints = START_HEALTH;
		intelligence = START_INTELLIGENCE;
		perception = START_PERCEPTION;
		canThrowSpell = SPELL_THROWER;
		weapons.add(new ShortSword());
		spells.put(Spell.SIMPLE_HEAL, SIMPLE_HEAL_START_NUMBER);
	}

	@Override
	public void accept(MapObjectVisitor visitor) {
		visitor.visit(this);
	}
}
