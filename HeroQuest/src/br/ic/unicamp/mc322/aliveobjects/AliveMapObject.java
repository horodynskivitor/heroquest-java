package br.ic.unicamp.mc322.aliveobjects;

import java.util.ArrayList;
import java.util.Map;

import br.ic.unicamp.mc322.actioninterfaces.Movable;
import br.ic.unicamp.mc322.armor.Armor;
import br.ic.unicamp.mc322.map.Coordinate;
import br.ic.unicamp.mc322.map.Direction;
import br.ic.unicamp.mc322.map.MapObject;
import br.ic.unicamp.mc322.spells.SimpleHeal;
import br.ic.unicamp.mc322.spells.Spell;
import br.ic.unicamp.mc322.spells.Teleport;
import br.ic.unicamp.mc322.weapons.Fist;
import br.ic.unicamp.mc322.weapons.Weapon;

public abstract class AliveMapObject extends MapObject implements Movable {
	ArrayList<Armor> armors;
	int attackDice, defenseDice, healthPoints, intelligence, perception;
	boolean canThrowSpell;
	int goldenCoins;
	Map<Spell, Integer> spells;
	ArrayList<Weapon> weapons;

	public AliveMapObject(int x, int y) {
		super(x, y);
		weapons = new ArrayList<Weapon>();
		armors = new ArrayList<Armor>();
		weapons.add(new Fist());
		spells = Spell.getEmptySpellInventory();
		goldenCoins = 0;
	}

	public void changeLifePointsByAmount(int amount) {
		healthPoints += amount;
	}

	public abstract int defendAgainstAttack();

	public abstract int defendAgainstSpell();

	public int getDefenseBonus() {
		int defense = 0;
		if (armors.size() > 0) {
			Armor armor = armors.get(0);
			defense = armor.getDefense();
		}
		return defense;
	}

	public int getHealthPoints() {
		return healthPoints;
	}

	public int getIntelligence() {
		return intelligence;
	}

	public int getPerception() {
		return perception;
	}

	public void giveGoldenCoins(int amount) {
		if (amount > 0) {
			goldenCoins += amount;
		}
	}

	public void giveNewArmor(Armor armor) {
		if (armor != null) {
			armors.add(armor);
		}
	}

	public void giveNewMagics(Map<Spell, Integer> newSpells) {
		if (newSpells != null)
			for (Spell s : newSpells.keySet()) {
				spells.put(s, spells.get(s) + newSpells.get(s));
			}
	}

	public void giveNewWeapon(Weapon newWeapon) {
		if (newWeapon != null)
			weapons.add(newWeapon);
	}

	public boolean isAlive() {
		return (healthPoints > 0);
	}

	@Override
	public boolean move(Direction direction, ArrayList<MapObject> colisionElements) {
		int x = getX(), y = getY();
		switch (direction) {
		case UP:
			y -= 1;
			break;
		case DOWN:
			y += 1;
			break;
		case RIGHT:
			x += 1;
			break;
		default:
			x -= 1;
			break;
		}
		for (MapObject o : colisionElements) {
			if (o.isSameCoordinates(x, y)) {
				return false;
			}
		}
		Coordinate playerPosition = getCoordinate();
		playerPosition.changeCoordinates(x, y);
		return true;
	}

	public Spell selectSpell() {
		for (Spell s : spells.keySet()) {
			if (spells.get(s) > 0) {
				return s;
			}
		}
		return null;
	}

	public abstract ArrayList<AliveMapObject> throwFireballSpell(ArrayList<AliveMapObject> entities);

	public void throwHealSpell(AliveMapObject target) {
		if (canThrowSpell) {
			SimpleHeal heal = new SimpleHeal();
			heal.use(this, target);
			spells.put(Spell.SIMPLE_HEAL, spells.get(Spell.SIMPLE_HEAL) - 1);
		}
	}

	public abstract AliveMapObject throwMagicMissileSpell(ArrayList<AliveMapObject> entities);

	public void throwTeleportSpell(ArrayList<Coordinate> teleportableCoordinates) {
		if (canThrowSpell) {
			Teleport teleport = new Teleport();
			Coordinate coord = getCoordinate();
			teleport.use(coord, teleportableCoordinates);
			spells.put(Spell.TELEPORT, spells.get(Spell.TELEPORT) - 1);
		}
	}

}
