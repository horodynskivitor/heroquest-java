package br.ic.unicamp.mc322.armor;

import java.util.Arrays;
import java.util.List;

public abstract class Armor {

	public static List<Armor> getArmorsInListFormat() {
		return Arrays.asList(new SimpleArmor());
	}

	int defensePoints;

	public int getDefense() {
		return defensePoints;
	}
}
