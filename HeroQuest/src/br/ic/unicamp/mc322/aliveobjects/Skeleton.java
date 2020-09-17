package br.ic.unicamp.mc322.aliveobjects;

import br.ic.unicamp.mc322.engine.MapObjectVisitor;
import br.ic.unicamp.mc322.engine.RandomNumberGenerator;
import br.ic.unicamp.mc322.map.Coordinate;

public class Skeleton extends Monster {
	private final boolean SPELL_THROWER = true;
	private final int START_ATTACK_DICE = 1, START_DEFENSE_DICE = 1, START_HEALTH = 2, START_INTELLIGENCE = 2;
	private final int START_PERCEPTION = 1;

	public Skeleton(int x, int y) {
		super(x, y);
		attackDice = START_ATTACK_DICE;
		defenseDice = START_DEFENSE_DICE;
		healthPoints = START_HEALTH;
		intelligence = START_INTELLIGENCE;
		perception = START_PERCEPTION;
		canThrowSpell = SPELL_THROWER;

		weapons.add(RandomNumberGenerator.getRandomWeapon());
	}

	@Override
	public void accept(MapObjectVisitor visitor) {
		visitor.visit(this);
	}

	@Override
	public String toString() {
		Coordinate coord = getCoordinate();
		return "Esqueleto. Posição x = " + coord.getX() + " e y = " + coord.getY();
	}
}
