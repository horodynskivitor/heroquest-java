package br.ic.unicamp.mc322.engine;

import br.ic.unicamp.mc322.aliveobjects.Barbarian;
import br.ic.unicamp.mc322.aliveobjects.Dwarf;
import br.ic.unicamp.mc322.aliveobjects.Elf;
import br.ic.unicamp.mc322.aliveobjects.Goblin;
import br.ic.unicamp.mc322.aliveobjects.Skeleton;
import br.ic.unicamp.mc322.aliveobjects.SkeletonWizard;
import br.ic.unicamp.mc322.aliveobjects.Wizard;
import br.ic.unicamp.mc322.map.Door;
import br.ic.unicamp.mc322.map.Map;
import br.ic.unicamp.mc322.map.MapObject;
import br.ic.unicamp.mc322.map.Trap;
import br.ic.unicamp.mc322.map.Treasure;
import br.ic.unicamp.mc322.map.Wall;

class TextRenderManager implements MapObjectVisitor {

	private char charMap[][];
	private boolean[][] discoveredPortion;
	private int healthPoints;

	public TextRenderManager(int mapWidth, int mapHeight) {
		this.charMap = new char[mapHeight][mapWidth];
	}

	private void clearMap() {
		for (int i = 0; i < charMap.length; i++) {
			for (int j = 0; j < charMap[0].length; j++) {
				charMap[i][j] = '_';
			}
		}
	}

	private void printMap() {
		for (int i = 0; i < charMap.length; i++) {
			for (int j = 0; j < charMap[0].length; j++) {
				if (discoveredPortion[i][j] == true)
					System.out.print(charMap[i][j] + " ");
				else
					System.out.print("  ");
			}
			System.out.println();
		}
	}

	public void render(Map map) {
		clearMap();
		map.accept(this);
		discoveredPortion = map.getDiscoveredPortion();
		System.out.println("Vida do jogador : " + healthPoints);
		printMap();
	}

	private void setSymbol(MapObject obj, char character) {
		charMap[obj.getY()][obj.getX()] = character;
	}

	@Override
	public void visit(Barbarian barbarian) {
		setSymbol(barbarian, 'B');
		healthPoints = barbarian.getHealthPoints();
	}

	@Override
	public void visit(Door door) {
		setSymbol(door, 'D');
	}

	@Override
	public void visit(Dwarf dwarf) {
		setSymbol(dwarf, 'A');
		healthPoints = dwarf.getHealthPoints();
	}

	@Override
	public void visit(Elf elf) {
		setSymbol(elf, 'E');
		healthPoints = elf.getHealthPoints();
	}

	@Override
	public void visit(Goblin goblin) {
		setSymbol(goblin, 'G');
	}

	@Override
	public void visit(Skeleton skeleton) {
		setSymbol(skeleton, 'S');
	}

	@Override
	public void visit(SkeletonWizard magSkeleton) {
		setSymbol(magSkeleton, 'M');
	}

	@Override
	public void visit(Trap trap) {
		setSymbol(trap, '_');
		// mudar este simbolo pode fazer as armadilhas aparecerem durante a execução.
	}

	@Override
	public void visit(Treasure treasure) {
		setSymbol(treasure, '_');
		// mudar este simbolo pode fazer os tesouros aparecerem durante a execução.
	}

	@Override
	public void visit(Wall wall) {
		setSymbol(wall, 'P');
	}

	@Override
	public void visit(Wizard wizard) {
		setSymbol(wizard, 'W');
		healthPoints = wizard.getHealthPoints();
	}

}
