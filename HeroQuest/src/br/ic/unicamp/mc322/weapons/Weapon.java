package br.ic.unicamp.mc322.weapons;

import java.util.Arrays;
import java.util.List;

public abstract class Weapon {
	public static List<Weapon> getWeaponsInListFormat() {
		return Arrays.asList(new LongSword(), new Dagger(), new ShortSword());
	}

	boolean destroyedAfterUsed, twoHanded;

	int range, attackBonus;

	public Weapon() {

	}

	public boolean destroyedAfterUsed() {
		return destroyedAfterUsed;
	}

	public int getAttackBonus() {
		return attackBonus;
	}

	public int getRange() {
		return range;
	}

	public boolean isTwoHanded() {
		return twoHanded;
	}

	@Override
	public String toString() {
		return "ALCANCE:  " + range + "  BÔNUS DE ATAQUE:  " + attackBonus;
	}

}
