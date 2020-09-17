package br.ic.unicamp.mc322.spells;

import java.util.ArrayList;

import br.ic.unicamp.mc322.aliveobjects.AliveMapObject;
import br.ic.unicamp.mc322.engine.RandomNumberGenerator;

public class Fireball extends Magic {
	private final int RANGE = 6;

	public final int MAIN_TARGET_DAMAGE = 6, COLLATERAL_TARGET_DAMAGE = 3;

	public Fireball() {
		range = RANGE;
	}

	public int use(AliveMapObject origin, AliveMapObject target, ArrayList<AliveMapObject> adjacentObjects) {
		int originDice = RandomNumberGenerator.diceRoll();
		int intel = target.getIntelligence();
		if (originDice < intel) {
			int defensePoints = target.defendAgainstSpell();
			if (MAIN_TARGET_DAMAGE - defensePoints > 0) {
				target.changeLifePointsByAmount(-MAIN_TARGET_DAMAGE + defensePoints);
			}
			for (AliveMapObject a : adjacentObjects) {
				defensePoints = a.defendAgainstSpell();
				if (COLLATERAL_TARGET_DAMAGE - defensePoints > 0) {
					a.changeLifePointsByAmount(-COLLATERAL_TARGET_DAMAGE + defensePoints);
					return COLLATERAL_TARGET_DAMAGE - defensePoints;
				}
			}
		}
		return 0;
	}
}
