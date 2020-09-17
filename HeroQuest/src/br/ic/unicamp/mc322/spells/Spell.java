package br.ic.unicamp.mc322.spells;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum Spell {
	FIREBALL, MAGIC_MISSILE, SIMPLE_HEAL, TELEPORT;

	public static Map<Spell, Integer> getEmptySpellInventory() {
		Map<Spell, Integer> spells = new HashMap<Spell, Integer>();
		for (Spell s : Spell.values())
			spells.put(s, 0);
		return spells;
	}

	public static List<Spell> getSpellsInListFormat() {
		List<Spell> listingSpells = new ArrayList<Spell>();
		for (Spell s : Spell.values())
			listingSpells.add(s);
		return listingSpells;
	}
}
