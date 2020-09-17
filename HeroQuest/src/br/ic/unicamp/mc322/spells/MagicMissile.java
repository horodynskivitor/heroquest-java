package br.ic.unicamp.mc322.spells;

import br.ic.unicamp.mc322.aliveobjects.AliveMapObject;
import br.ic.unicamp.mc322.engine.RandomNumberGenerator;

public class MagicMissile extends Magic {
	private final int DAMAGE_BY_EACH_ARROW = 2;
	private final int NUMBER_OF_ARROWS = 3;
	private final int RANGE = 6;

	public MagicMissile() {
		range = RANGE;
	}

	public int use(AliveMapObject origin, AliveMapObject target) {
		int originDice = RandomNumberGenerator.diceRoll();
		int intel = target.getIntelligence();
		if (originDice < intel) {
			int defensePoints = target.defendAgainstSpell();
			if (NUMBER_OF_ARROWS * DAMAGE_BY_EACH_ARROW - defensePoints > 0) {
				target.changeLifePointsByAmount(-NUMBER_OF_ARROWS * DAMAGE_BY_EACH_ARROW + defensePoints);
				return NUMBER_OF_ARROWS * DAMAGE_BY_EACH_ARROW - defensePoints;
			}
		}
		return 0;
	}

}
