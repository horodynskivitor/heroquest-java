package br.ic.unicamp.mc322.engine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import br.ic.unicamp.mc322.aliveobjects.AliveMapObject;
import br.ic.unicamp.mc322.aliveobjects.Monster;
import br.ic.unicamp.mc322.armor.Armor;
import br.ic.unicamp.mc322.map.Coordinate;
import br.ic.unicamp.mc322.map.Direction;
import br.ic.unicamp.mc322.spells.Spell;
import br.ic.unicamp.mc322.weapons.Weapon;

public class RandomNumberGenerator {

	private static Random r = new Random();
	private static final double ARMOR_IN_TREASURE_CHANCE = 0.2;
	private static final double HERO_SHIELD_DICE_CUMULATIVE = 0.5 + 2 / 6;
	private static final int NUMBER_OF_COIN_TOSSES = 10;
	private static final int NUMBER_OF_SPELL_TOSSES = 5;
	private static final double PROBABILITY_OF_SUCCESS_OF_COIN_TOSS = 0.5;
	private static final double PROBABILITY_OF_SUCCESS_OF_SPELL_TOSS = 0.4;
	private static final double SKULL_DICE_CUMULATIVE = 0.5;
	private static final double SPAWN_MONSTER_CHANCE_FROM_BAD_TREASURE_SEARCH = 0.10;
	private static final double WEAPON_IN_TREASURE_CHANCE = 0.6;

	public static Map<DiceResult, Integer> combatDiceRoll() {
		return combatDiceRoll(1);

	}

	public static Map<DiceResult, Integer> combatDiceRoll(int amount) {
		Map<DiceResult, Integer> combatResult = new HashMap<DiceResult, Integer>();
		for (DiceResult result : DiceResult.values()) {
			combatResult.put(result, 0);
		}
		for (int i = 0; i < amount; i++) {
			double randomNumber = r.nextDouble();
			if (randomNumber < SKULL_DICE_CUMULATIVE)
				combatResult.put(DiceResult.SKULL, combatResult.get(DiceResult.SKULL) + 1);
			else if (randomNumber < HERO_SHIELD_DICE_CUMULATIVE)
				combatResult.put(DiceResult.HERO_SHIELD, combatResult.get(DiceResult.HERO_SHIELD) + 1);
			else
				combatResult.put(DiceResult.MONSTER_SHIELD, combatResult.get(DiceResult.MONSTER_SHIELD) + 1);
		}
		return combatResult;
	}

	public static int diceRoll() {
		return r.nextInt(6) + 1;
	}

	public static int diceRoll(int amount) {
		int sum = 0;
		for (int i = 0; i < amount; i++) {
			sum += diceRoll();
		}
		return sum;
	}

	public static double getDoubleBetween(double min, double max) {
		return (r.nextDouble() * (max - min) + min);
	}

	public static Monster getMonsterFromBadTreasureSearch(ArrayList<Coordinate> availableCoordinates) {
		double randomNumber = r.nextDouble();
		if (randomNumber < SPAWN_MONSTER_CHANCE_FROM_BAD_TREASURE_SEARCH) {
			Coordinate coord = getRandomElementFromArrayList(availableCoordinates);
			return getRandomMonster(coord);
		}
		return null;
	}

	public static Armor getRandomArmor() {
		List<Armor> armorList = Armor.getArmorsInListFormat();
		Collections.shuffle(armorList);
		return armorList.get(0);
	}

	public static List<Direction> getRandomDirections() {
		List<Direction> directions = Direction.getDirectionInListFormat();
		Collections.shuffle(directions);
		return directions;
	}

	public static <R> R getRandomElementFromArrayList(ArrayList<R> listElements) {
		return listElements.get(r.nextInt(listElements.size()));
	}

	public static Monster getRandomMonster(Coordinate coord) {
		List<Monster> monsters = Monster.getMonstersInListFormat(coord);
		Collections.shuffle(monsters);
		return monsters.get(0);
	}

	public static Map<Spell, Integer> getRandomSpell() {
		return getRandomSpell(1);
	}

	public static Map<Spell, Integer> getRandomSpell(int amount) {
		if (amount <= 0)
			return null;
		Map<Spell, Integer> spells = Spell.getEmptySpellInventory();
		List<Spell> randomizedSpells = Spell.getSpellsInListFormat();
		for (int i = 0; i < amount; i++) {
			Collections.shuffle(randomizedSpells);
			Spell selectedSpell = randomizedSpells.get(0);
			spells.put(selectedSpell, spells.get(selectedSpell) + 1);
		}
		return spells;
	}

	public static Weapon getRandomWeapon() {
		List<Weapon> weapons = Weapon.getWeaponsInListFormat();
		Collections.shuffle(weapons);
		return weapons.get(0);
	}

	public static void giveTreasure(AliveMapObject player) {
		if (r.nextDouble() < WEAPON_IN_TREASURE_CHANCE)
			player.giveNewWeapon(getRandomWeapon());
		if (r.nextDouble() < ARMOR_IN_TREASURE_CHANCE)
			player.giveNewArmor(getRandomArmor());
		int spells = makeBinomialTrial(NUMBER_OF_SPELL_TOSSES, PROBABILITY_OF_SUCCESS_OF_SPELL_TOSS);
		player.giveNewMagics(getRandomSpell(spells));
		int coins = makeBinomialTrial(NUMBER_OF_COIN_TOSSES, PROBABILITY_OF_SUCCESS_OF_COIN_TOSS);
		player.giveGoldenCoins(coins);
	}

	private static int makeBinomialTrial(int numberOfTosses, double probability) {
		int successes = 0;
		for (int i = 0; i < numberOfTosses; i++) {
			if (r.nextDouble() < probability) {
				successes += 1;
			}
		}
		return successes;
	}

}
