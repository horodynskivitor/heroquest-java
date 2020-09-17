package br.ic.unicamp.mc322.aliveobjects;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import br.ic.unicamp.mc322.engine.DiceResult;
import br.ic.unicamp.mc322.engine.RandomNumberGenerator;
import br.ic.unicamp.mc322.map.Coordinate;
import br.ic.unicamp.mc322.spells.Fireball;
import br.ic.unicamp.mc322.spells.MagicMissile;
import br.ic.unicamp.mc322.spells.Spell;
import br.ic.unicamp.mc322.weapons.Weapon;

public abstract class Monster extends AliveMapObject {

	public static List<Monster> getMonstersInListFormat(Coordinate coord) {
		List<Monster> monsters = Arrays.asList(new Goblin(coord.getX(), coord.getY()),
				new Skeleton(coord.getX(), coord.getY()), new SkeletonWizard(coord.getX(), coord.getY()));
		return monsters;
	}

	public Monster(int x, int y) {
		super(x, y);
	}

	public void attack(AliveMapObject target) {
		Weapon selectedWeapon = RandomNumberGenerator.getRandomElementFromArrayList(weapons);
		if (this.getEuclideanDistanceTo(target) <= selectedWeapon.getRange()) {
			int attack = RandomNumberGenerator.combatDiceRoll(attackDice).get(DiceResult.SKULL)
					+ selectedWeapon.getAttackBonus();
			int defense = target.defendAgainstAttack();
			if (attack - defense > 0)
				target.changeLifePointsByAmount(-attack + defense);
		}
	}

	@Override
	public int defendAgainstAttack() {
		return getDefenseBonus() + RandomNumberGenerator.combatDiceRoll(defenseDice).get(DiceResult.MONSTER_SHIELD);
	}

	@Override
	public int defendAgainstSpell() {
		return getDefenseBonus() + RandomNumberGenerator.combatDiceRoll(intelligence).get(DiceResult.MONSTER_SHIELD);
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
			if (attackableEntities.size() == 0)
				return null;
			AliveMapObject target = entities.get(0);
			ArrayList<AliveMapObject> adjacentEntities = new ArrayList<AliveMapObject>();
			for (AliveMapObject m : entities) {
				if (m.areCoordinatesAdjcent(this))
					adjacentEntities.add(m);
			}
			fireball.use(this, target, adjacentEntities);
			spells.put(Spell.FIREBALL, spells.get(Spell.FIREBALL) - 1);
			adjacentEntities.add(target);
			return adjacentEntities;
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
			if (attackableMonsters.size() == 0)
				return null;
			AliveMapObject target = attackableMonsters.get(0);
			missile.use(this, target);
			spells.put(Spell.MAGIC_MISSILE, spells.get(Spell.MAGIC_MISSILE) - 1);
			return target;
		}
		return null;
	}
}
