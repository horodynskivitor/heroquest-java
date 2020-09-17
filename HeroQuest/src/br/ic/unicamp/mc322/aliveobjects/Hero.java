package br.ic.unicamp.mc322.aliveobjects;

import java.util.ArrayList;

import br.ic.unicamp.mc322.engine.Choice;
import br.ic.unicamp.mc322.engine.DiceResult;
import br.ic.unicamp.mc322.engine.InputOutputUtils;
import br.ic.unicamp.mc322.engine.RandomNumberGenerator;
import br.ic.unicamp.mc322.spells.Fireball;
import br.ic.unicamp.mc322.spells.MagicMissile;
import br.ic.unicamp.mc322.spells.Spell;
import br.ic.unicamp.mc322.weapons.Weapon;

public abstract class Hero extends AliveMapObject {

	Hero(int x, int y) {
		super(x, y);
	}

	public Monster attack(ArrayList<Monster> monsters) {
		Weapon primaryWeapon = selectWeapon();
		Weapon secondaryWeapon = selectSecondaryWeapon(primaryWeapon);
		ArrayList<Monster> attackableMonsters = new ArrayList<Monster>();
		for (Monster m : monsters) {
			if (m.getEuclideanDistanceTo(this) <= primaryWeapon.getRange())
				attackableMonsters.add(m);
		}
		Monster target = InputOutputUtils.selectElementFromArrayList("Escolha o monstro a ser atacado",
				attackableMonsters);

		if (target != null) {
			int attack = RandomNumberGenerator.combatDiceRoll(attackDice).get(DiceResult.SKULL);
			int defense = target.defendAgainstAttack();
			attack += primaryWeapon.getAttackBonus();
			if (primaryWeapon.destroyedAfterUsed())
				weapons.remove(primaryWeapon);
			if (secondaryWeapon != null) {
				attack += secondaryWeapon.getAttackBonus();
				if (secondaryWeapon.destroyedAfterUsed())
					weapons.remove(secondaryWeapon);
			}
			if (attack - defense > 0) {
				target.changeLifePointsByAmount(-attack + defense);
			}
			InputOutputUtils.printAttackResults(attack - defense);
		}
		return target;
	}

	@Override
	public int defendAgainstAttack() {
		return getDefenseBonus() + RandomNumberGenerator.combatDiceRoll(defenseDice).get(DiceResult.HERO_SHIELD);
	}

	@Override
	public int defendAgainstSpell() {
		return getDefenseBonus() + RandomNumberGenerator.combatDiceRoll(intelligence).get(DiceResult.HERO_SHIELD);
	}

	private Weapon selectSecondaryWeapon(Weapon primaryWeapon) {
		ArrayList<Weapon> secondaryWeapons = new ArrayList<Weapon>();
		secondaryWeapons.addAll(weapons);
		secondaryWeapons.remove(primaryWeapon);
		Weapon secondaryWeapon = null;
		for (Weapon w : weapons) {
			if (w.isTwoHanded())
				secondaryWeapons.remove(w);
		}
		if (secondaryWeapons.size() > 0 && !primaryWeapon.isTwoHanded()) {
			Choice getSecondWeapon = InputOutputUtils
					.yesOrNoPrompt("Você deseja atacar o monstro com uma arma secundária?");
			switch (getSecondWeapon) {
			case YES:
				secondaryWeapon = InputOutputUtils.selectElementFromArrayList(
						"Escolha uma arma secundária para atacar os monstros :", secondaryWeapons);
				break;
			default:
				break;
			}
		}
		return secondaryWeapon;
	}

	@Override
	public Spell selectSpell() {
		ArrayList<Spell> spellInventory = new ArrayList<Spell>();
		for (Spell s : spells.keySet()) {
			for (int i = 0; i < spells.get(s); i++) {
				spellInventory.add(s);
			}
		}
		Spell selectedSpell = InputOutputUtils
				.selectElementFromArrayList("Escolha uma magia para lançar sob os monstros :", spellInventory);
		return selectedSpell;
	}

	private Weapon selectWeapon() {
		return InputOutputUtils.selectElementFromArrayList("Escolha uma arma para atacar os monstros :", weapons);
	}

	@Override
	public ArrayList<AliveMapObject> throwFireballSpell(ArrayList<AliveMapObject> entities) {
		if (canThrowSpell) {
			Fireball fireball = new Fireball();
			ArrayList<AliveMapObject> attackableEntities = new ArrayList<AliveMapObject>();
			for (AliveMapObject m : entities) {
				if (m.getEuclideanDistanceTo(this) <= fireball.getRange())
					attackableEntities.add(m);
			}
			AliveMapObject target = InputOutputUtils.selectElementFromArrayList(
					"Selecione o monstro em que você gostaria de jogar a mágica sob : ", attackableEntities);
			ArrayList<AliveMapObject> adjacanetEntities = new ArrayList<AliveMapObject>();
			if (target != null) {
				for (AliveMapObject m : entities) {
					if (m.areCoordinatesAdjcent(this))
						adjacanetEntities.add(m);
				}
				int damage = fireball.use(this, target, adjacanetEntities);
				InputOutputUtils.printSpellResults(damage);
				adjacanetEntities.add(target);
			}
			return adjacanetEntities;
		}
		return null;
	}

	@Override
	public AliveMapObject throwMagicMissileSpell(ArrayList<AliveMapObject> entities) {
		if (canThrowSpell) {
			MagicMissile missile = new MagicMissile();
			ArrayList<AliveMapObject> attackableMonsters = new ArrayList<AliveMapObject>();
			for (AliveMapObject m : entities) {
				if (m.getEuclideanDistanceTo(this) <= missile.getRange())
					attackableMonsters.add(m);
			}
			AliveMapObject target = InputOutputUtils.selectElementFromArrayList(
					"Selecione o monstro em que você gostaria de jogar a mágica sob : ", attackableMonsters);
			if (target != null) {
				int damage = missile.use(this, target);
				InputOutputUtils.printSpellResults(damage);
				spells.put(Spell.MAGIC_MISSILE, spells.get(Spell.MAGIC_MISSILE) - 1);
			}
			return target;
		}
		return null;
	}

}
