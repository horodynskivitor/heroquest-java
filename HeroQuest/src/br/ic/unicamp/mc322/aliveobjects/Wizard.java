package br.ic.unicamp.mc322.aliveobjects;

import br.ic.unicamp.mc322.engine.MapObjectVisitor;
import br.ic.unicamp.mc322.spells.Spell;
import br.ic.unicamp.mc322.weapons.Dagger;

public class Wizard extends Hero {
	private final int FIREBALL_START_NUMBER = 1;
	private final int MAGIC_MISSILE_START_NUMBER = 3;
	private final boolean SPELL_THROWER = true;
	private final int START_ATTACK_DICE = 1, START_DEFENSE_DICE = 2, START_HEALTH = 4, START_INTELLIGENCE = 6;
	private final int START_PERCEPTION = 5;

	private final int TELEPORT_START_NUMBER = 1;

	public Wizard(int x, int y) {
		super(x, y);
		attackDice = START_ATTACK_DICE;
		defenseDice = START_DEFENSE_DICE;
		healthPoints = START_HEALTH;
		intelligence = START_INTELLIGENCE;
		perception = START_PERCEPTION;
		canThrowSpell = SPELL_THROWER;
		weapons.add(new Dagger());
		weapons.add(new Dagger());
		weapons.add(new Dagger());
		spells.put(Spell.FIREBALL, FIREBALL_START_NUMBER);
		spells.put(Spell.MAGIC_MISSILE, MAGIC_MISSILE_START_NUMBER);
		spells.put(Spell.TELEPORT, TELEPORT_START_NUMBER);
	}

	@Override
	public void accept(MapObjectVisitor visitor) {
		visitor.visit(this);
	}
}
