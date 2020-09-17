package br.ic.unicamp.mc322.map;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import br.ic.unicamp.mc322.aliveobjects.Goblin;
import br.ic.unicamp.mc322.aliveobjects.Hero;
import br.ic.unicamp.mc322.aliveobjects.Monster;
import br.ic.unicamp.mc322.aliveobjects.Skeleton;
import br.ic.unicamp.mc322.aliveobjects.SkeletonWizard;
import br.ic.unicamp.mc322.engine.MapGenerationType;
import br.ic.unicamp.mc322.engine.RandomNumberGenerator;

public class MapLoader {
	private static MapLoader labyrinthMapLoader = null;
	private static final double MONSTER_MIN_DENSITY = 0.01, MONSTER_MAX_DENSITY = 0.05;
	private static final int MONSTER_MIN_NUMBER = 1;
	private static final double TRAP_MIN_DENSITY = 0.001, TRAP_MAX_DENSITY = 0.01;
	private static final double TREASURE_MIN_DENSITY = 0.01, TREASURE_MAX_DENSITY = 0.05;

	static public MapLoader getInstance() {
		if (labyrinthMapLoader == null) {
			labyrinthMapLoader = new MapLoader();
		}
		return labyrinthMapLoader;
	}

	private final String MAP_FILE_LOCATION = "src/maptext", MAP_WALLS_AND_DOORS_ONLY_LOCATION = "src/mapskeleton";

	private MapLoader() {

	}

	public Map loadMap(MapGenerationType generationType, Hero player) {
		switch (generationType) {
		case RANDOM:
			return loadMapRandomly(MAP_WALLS_AND_DOORS_ONLY_LOCATION, player);
		default:
			return loadMapFromFile(MAP_FILE_LOCATION, player);
		}
	}

	private Map loadMapFromFile(String path, Hero player) {
		Scanner mazeScanner;
		try {
			File mapFile = new File(path);
			mazeScanner = new Scanner(mapFile);
			int height = 0, width = 0;
			ArrayList<Monster> monsters = new ArrayList<Monster>();
			ArrayList<Wall> walls = new ArrayList<Wall>();
			ArrayList<Door> doors = new ArrayList<Door>();
			ArrayList<Treasure> treasures = new ArrayList<Treasure>();
			ArrayList<Trap> traps = new ArrayList<Trap>();
			while (mazeScanner.hasNextLine()) {
				String mapLine = mazeScanner.nextLine();
				char[] mapLineChar = mapLine.toCharArray();
				int i;
				for (i = 0; i < mapLine.length(); i++) {
					char c = mapLineChar[i];
					if (c == 'G') {
						monsters.add(new Goblin(i, height));
					} else if (c == 'S') {
						monsters.add(new Skeleton(i, height));
					} else if (c == 'M') {
						monsters.add(new SkeletonWizard(i, height));
					} else if (c == 'P') {
						walls.add(new Wall(i, height));
					} else if (c == 'D') {
						doors.add(new Door(i, height));
					} else if (c == 'T') {
						treasures.add(new Treasure(i, height));
					} else if (c == '$') {
						traps.add(new Trap(i, height));
					}
				}
				height += 1;
				width = mapLine.length();
			}
			mazeScanner.close();
			return new Map(width, height, player, monsters, walls, doors, treasures, traps);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.out.println("O arquivo que armazena o mapa não foi encontrado. Terminando a execução.");
			System.exit(1);
		}
		return null;
	}

	private Map loadMapRandomly(String path, Hero player) {
		Scanner mazeScanner;
		try {
			File mapFile = new File(path);
			mazeScanner = new Scanner(mapFile);
			int height = 0, width = 0;
			ArrayList<Coordinate> freeCoordinates = new ArrayList<Coordinate>();
			ArrayList<Monster> monsters = new ArrayList<Monster>();
			ArrayList<Wall> walls = new ArrayList<Wall>();
			ArrayList<Door> doors = new ArrayList<Door>();
			ArrayList<Treasure> treasures = new ArrayList<Treasure>();
			ArrayList<Trap> traps = new ArrayList<Trap>();
			while (mazeScanner.hasNextLine()) {
				String mapLine = mazeScanner.nextLine();
				char[] mapLineChar = mapLine.toCharArray();
				int i;
				for (i = 0; i < mapLine.length(); i++) {
					freeCoordinates.add(new Coordinate(i, height));
					char c = mapLineChar[i];
					if (c == 'P') {
						walls.add(new Wall(i, height));
						freeCoordinates.remove(freeCoordinates.size() - 1);
					} else if (c == 'D') {
						doors.add(new Door(i, height));
						freeCoordinates.remove(freeCoordinates.size() - 1);
					}
				}
				height += 1;
				width = mapLine.length();
			}
			mazeScanner.close();
			int totalAvailableCells = width * height - 1 - doors.size() - monsters.size();
			freeCoordinates.remove(0);
			ArrayList<Coordinate> freeCoordinatesCopy = new ArrayList<Coordinate>(freeCoordinates);

			int numberOfTreasures = (int) (totalAvailableCells
					* RandomNumberGenerator.getDoubleBetween(TREASURE_MIN_DENSITY, TREASURE_MAX_DENSITY));
			int numberOfTraps = (int) (totalAvailableCells
					* RandomNumberGenerator.getDoubleBetween(TRAP_MIN_DENSITY, TRAP_MAX_DENSITY));
			for (int i = 0; i < numberOfTreasures; i++) {
				Coordinate coord = RandomNumberGenerator.getRandomElementFromArrayList(freeCoordinates);
				treasures.add(new Treasure(coord.getX(), coord.getY()));
				freeCoordinates.remove(coord);
			}
			for (int i = 0; i < numberOfTraps; i++) {
				Coordinate coord = RandomNumberGenerator.getRandomElementFromArrayList(freeCoordinates);
				traps.add(new Trap(coord.getX(), coord.getY()));
				freeCoordinates.remove(coord);
			}

			freeCoordinates.addAll(freeCoordinatesCopy);
			int numberOfMonsters = (int) (totalAvailableCells
					* RandomNumberGenerator.getDoubleBetween(MONSTER_MIN_DENSITY, MONSTER_MAX_DENSITY));
			numberOfMonsters = Math.max(numberOfMonsters, MONSTER_MIN_NUMBER);
			for (int i = 0; i < numberOfMonsters; i++) {
				Coordinate coord = RandomNumberGenerator.getRandomElementFromArrayList(freeCoordinates);
				Monster randomMonster = RandomNumberGenerator.getRandomMonster(coord);
				monsters.add(randomMonster);
				freeCoordinates.remove(coord);
			}

			return new Map(width, height, player, monsters, walls, doors, treasures, traps);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.out.println("O arquivo que armazena o mapa não foi encontrado. Terminando a execução.");
			System.exit(1);
		}
		return null;
	}

}
