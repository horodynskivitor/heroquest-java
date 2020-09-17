package br.ic.unicamp.mc322.weapons;

public class LongSword extends Weapon {
	private final boolean DESTROY_AFTER_ONE_USE = false, TWO_HANDED = true;
	private final int RANGE = 2, ATTACK_BONUS = 3;

	public LongSword() {
		super();
		range = RANGE;
		attackBonus = ATTACK_BONUS;
		destroyedAfterUsed = DESTROY_AFTER_ONE_USE;
		twoHanded = TWO_HANDED;
	}

	@Override
	public String toString() {
		return "Espada Longa:    " + super.toString();
	}

}
