package br.ic.unicamp.mc322.weapons;

public class Dagger extends Weapon {
	private final boolean DESTROY_AFTER_ONE_USE = true, TWO_HANDED = false;
	private final int RANGE = 1, ATTACK_BONUS = 1;

	public Dagger() {
		super();
		range = RANGE;
		attackBonus = ATTACK_BONUS;
		destroyedAfterUsed = DESTROY_AFTER_ONE_USE;
		twoHanded = TWO_HANDED;
	}

	@Override
	public String toString() {
		return "Punhal:    " + super.toString();
	}
}
