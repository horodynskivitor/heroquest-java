package br.ic.unicamp.mc322.spells;

import br.ic.unicamp.mc322.aliveobjects.AliveMapObject;
import br.ic.unicamp.mc322.engine.RandomNumberGenerator;

public class SimpleHeal extends Magic {

	private final int RANGE = Integer.MAX_VALUE;

	public SimpleHeal() {
		range = RANGE;
	}

	public boolean use(AliveMapObject origin, AliveMapObject target) {
		target.changeLifePointsByAmount(RandomNumberGenerator.diceRoll());
		return true;
	}

}
