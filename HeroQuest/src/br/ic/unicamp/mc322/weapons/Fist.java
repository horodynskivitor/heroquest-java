package br.ic.unicamp.mc322.weapons;

public class Fist extends Weapon {
	private final boolean DESTROY_AFTER_ONE_USE = false, TWO_HANDED = true;
	private final int RANGE = 1, ATTACK_BONUS = 0;

	public Fist() {
		super();
		range = RANGE;
		attackBonus = ATTACK_BONUS;
		destroyedAfterUsed = DESTROY_AFTER_ONE_USE;
		twoHanded = TWO_HANDED;
	}

	@Override
	public String toString() {
		return "Soco:    " + super.toString();
	}
}
