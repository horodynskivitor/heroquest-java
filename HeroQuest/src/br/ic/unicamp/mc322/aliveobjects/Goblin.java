package br.ic.unicamp.mc322.aliveobjects;

import br.ic.unicamp.mc322.engine.MapObjectVisitor;
import br.ic.unicamp.mc322.map.Coordinate;
import br.ic.unicamp.mc322.weapons.Dagger;

public class Goblin extends Monster {
	private static final int INITIAL_NUMBER_OF_DAGGERS = 3;
	private final boolean SPELL_THROWER = true;
	private final int START_ATTACK_DICE = 1, START_DEFENSE_DICE = 1, START_HEALTH = 2, START_INTELLIGENCE = 2;
	private final int START_PERCEPTION = 3;

	public Goblin(int x, int y) {
		super(x, y);
		attackDice = START_ATTACK_DICE;
		defenseDice = START_DEFENSE_DICE;
		healthPoints = START_HEALTH;
		intelligence = START_INTELLIGENCE;
		perception = START_PERCEPTION;
		canThrowSpell = SPELL_THROWER;
		for (int i = 0; i < INITIAL_NUMBER_OF_DAGGERS; i++)
			weapons.add(new Dagger());
	}

	@Override
	public void accept(MapObjectVisitor visitor) {
		visitor.visit(this);
	}

	@Override
	public String toString() {
		Coordinate coord = getCoordinate();
		return "Goblin. Posição x = " + coord.getX() + " e y = " + coord.getY();
	}

}
