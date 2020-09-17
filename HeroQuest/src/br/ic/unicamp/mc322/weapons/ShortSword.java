package br.ic.unicamp.mc322.weapons;

public class ShortSword extends Weapon {
	private final boolean DESTROY_AFTER_ONE_USE = false, TWO_HANDED = false;
	private final int RANGE = 1, ATTACK_BONUS = 1;

	public ShortSword() {
		super();
		range = RANGE;
		attackBonus = ATTACK_BONUS;
		destroyedAfterUsed = DESTROY_AFTER_ONE_USE;
		twoHanded = TWO_HANDED;
	}

	@Override
	public String toString() {
		return "Espada Curta:    " + super.toString();
	}
}
