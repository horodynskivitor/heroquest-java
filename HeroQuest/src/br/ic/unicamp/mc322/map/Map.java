package br.ic.unicamp.mc322.map;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import br.ic.unicamp.mc322.aliveobjects.AliveMapObject;
import br.ic.unicamp.mc322.aliveobjects.Goblin;
import br.ic.unicamp.mc322.aliveobjects.Hero;
import br.ic.unicamp.mc322.aliveobjects.Monster;
import br.ic.unicamp.mc322.customexceptions.GoblinBlockedException;
import br.ic.unicamp.mc322.engine.MapObjectVisitor;
import br.ic.unicamp.mc322.engine.RandomNumberGenerator;
import br.ic.unicamp.mc322.spells.Spell;

public class Map {
	private ArrayList<MapObject> colisionElements;
	private boolean[][] discovered;
	private ArrayList<Door> doors;
	private final int FIELD_OF_VIEW_PRECISION = 200;
	private int height, width;
	private ArrayList<Monster> monsters;
	private Hero player;
	private ArrayList<Trap> traps;
	private ArrayList<Treasure> treasures;
	private ArrayList<Wall> walls;

	protected Map(int width, int height, Hero player, ArrayList<Monster> monsters, ArrayList<Wall> walls,
			ArrayList<Door> doors, ArrayList<Treasure> treasures, ArrayList<Trap> traps) {
		this.width = width;
		this.height = height;
		this.player = player;
		this.monsters = monsters;
		this.walls = walls;
		this.doors = doors;
		this.treasures = treasures;
		this.traps = traps;

		discovered = new boolean[height][width];
		discoverMap();
		colisionElements = new ArrayList<MapObject>();
		colisionElements.addAll(walls);
		colisionElements.addAll(monsters);
		colisionElements.add(player);
	}

	public void accept(MapObjectVisitor visitor) {
		for (Wall wall : walls)
			wall.accept(visitor);
		for (Door door : doors)
			door.accept(visitor);
		for (Treasure treasure : treasures)
			treasure.accept(visitor);
		for (Trap trap : traps)
			trap.accept(visitor);
		for (Monster monster : monsters)
			monster.accept(visitor);
		player.accept(visitor);
	}

	private boolean areAllEnemiesDead() {
		return monsters.size() == 0;
	}

	private int[] dijkstraAlgorithm(MapObject source) {
		ArrayList<ArrayList<Integer>> adjlist = getAdjacencyList();
		int x = source.getX(), y = source.getY();

		int[] distance = new int[height * width];
		int[] parent = new int[height * width];
		boolean[] visited = new boolean[height * width];
		Arrays.fill(distance, Integer.MAX_VALUE);
		Arrays.fill(parent, -1);
		Arrays.fill(visited, false);

		distance[vertex(y, x)] = 0;
		for (int i = 0; i < height * width; i++) {
			int v = -1;
			for (int j = 0; j < height * width; j++) {
				if (!visited[j] && (v == -1 || distance[j] < distance[v]))
					v = j;
			}
			if (distance[v] == Integer.MAX_VALUE) {
				break;
			}
			visited[v] = true;
			for (Integer u : adjlist.get(v)) {
				if (distance[v] + 1 < distance[u]) {
					distance[u] = distance[v] + 1;
					parent[u] = v;
				}
			}
		}
		return parent;
	}

	private void discoverMap() {
		MapObject[][] itemBoard = getItemBoard();
		int sourcex = player.getX(), sourcey = player.getY();
		int numberOfSteps = FIELD_OF_VIEW_PRECISION;
		double stepSize = 2 * Math.PI / numberOfSteps;
		for (double angle = 0; angle < 2 * Math.PI; angle += stepSize) {
			double xCurrent = sourcex + 0.5, yCurrent = sourcey + 0.5;
			double xStep = Math.cos(angle), yStep = Math.sin(angle);
			while (true) {
				int x = (int) Math.floor(xCurrent);
				int y = (int) Math.floor(yCurrent);
				if (x >= 0 && x < width && y >= 0 && y < height) {
					discovered[y][x] = true;
					if (itemBoard[y][x] != null)
						break;
				} else
					break;
				xCurrent += xStep;
				yCurrent += yStep;
			}
		}
	}

	public void enterCombatMode() {
		Monster target = player.attack(monsters);
		if (target != null && !target.isAlive()) {
			monsters.remove(target);
			colisionElements.remove(target);
		}
	}

	public void enterSpellThrowingMode() {
		Spell selectedSpell = player.selectSpell();
		if (selectedSpell == null) {
			return;
		}
		ArrayList<AliveMapObject> targets;
		switch (selectedSpell) {
		case SIMPLE_HEAL:
			player.throwHealSpell(player);
			break;
		case TELEPORT:
			ArrayList<Coordinate> teleportableCoordinates = getAvailableCoordinates();
			player.throwTeleportSpell(teleportableCoordinates);
			break;
		case MAGIC_MISSILE:
			targets = new ArrayList<AliveMapObject>();
			targets.addAll(monsters);
			AliveMapObject target = player.throwMagicMissileSpell(targets);
			if (target != null && !target.isAlive()) {
				monsters.remove(target);
				colisionElements.remove(target);
			}
			break;
		case FIREBALL:
			targets = new ArrayList<AliveMapObject>();
			targets.addAll(monsters);
			ArrayList<AliveMapObject> hitEntities = player.throwFireballSpell(targets);
			if (hitEntities != null && hitEntities.size() > 0) {
				for (AliveMapObject m : hitEntities) {
					if (!m.isAlive()) {
						monsters.remove(m);
						colisionElements.remove(m);
					}
				}
			}
			break;
		}
	}

	private ArrayList<ArrayList<Integer>> getAdjacencyList() {
		MapObject[][] wallBoard = new MapObject[height][width];
		ArrayList<ArrayList<Integer>> adjlist = new ArrayList<ArrayList<Integer>>(height * width);
		for (int i = 0; i < height * width; i++)
			adjlist.add(new ArrayList<Integer>());
		for (Wall w : walls)
			wallBoard[w.getY()][w.getX()] = w;
		for (int i = 0; i < wallBoard.length; i++) {
			for (int j = 0; j < wallBoard[i].length; j++) {
				if (wallBoard[i][j] == null) {
					if (j - 1 >= 0 && wallBoard[i][j - 1] == null)
						adjlist.get(vertex(i, j)).add(vertex(i, j - 1));
					if (j + 1 < wallBoard[i].length && wallBoard[i][j + 1] == null)
						adjlist.get(vertex(i, j)).add(vertex(i, j + 1));
					if (i - 1 >= 0 && wallBoard[i - 1][j] == null)
						adjlist.get(vertex(i, j)).add(vertex(i - 1, j));
					if (i + 1 < wallBoard.length && wallBoard[i + 1][j] == null)
						adjlist.get(vertex(i, j)).add(vertex(i + 1, j));
				}
			}
		}
		return adjlist;
	}

	private ArrayList<Coordinate> getAvailableCoordinates() {
		MapObject[][] itemBoard = getItemBoard();
		boolean[][] discoveredPart = getDiscoveredPortion();
		ArrayList<Coordinate> availableCoordinates = new ArrayList<Coordinate>();
		for (int i = 0; i < itemBoard.length; i++) {
			for (int j = 0; j < itemBoard[i].length; j++) {
				if (itemBoard[i][j] == null && discoveredPart[i][j] == true) {
					availableCoordinates.add(new Coordinate(j, i));
				}
			}
		}
		return availableCoordinates;
	}

	public boolean[][] getDiscoveredPortion() {
		return discovered;
	}

	public int getHeight() {
		return height;
	}

	private MapObject[][] getItemBoard() {
		MapObject[][] itemBoard = new MapObject[height][width];
		for (Wall w : walls)
			itemBoard[w.getY()][w.getX()] = w;
		for (Door d : doors)
			itemBoard[d.getY()][d.getX()] = d;
		for (Monster m : monsters) {
			itemBoard[m.getY()][m.getX()] = m;
		}
		return itemBoard;
	}

	private Direction getOptimalDirectionForShortestPath(MapObject source, MapObject destination) {

		int[] parent = dijkstraAlgorithm(source);
		int x = source.getX(), y = source.getY();
		int destx = destination.getX(), desty = destination.getY();
		int destvertex = vertex(desty, destx);
		int nextMove = destvertex;
		while (parent[destvertex] != -1) {
			nextMove = destvertex;
			destvertex = parent[destvertex];
		}
		if (nextMove == vertex(y, x) + 1)
			return Direction.RIGHT;
		else if (nextMove == vertex(y, x) - 1)
			return Direction.LEFT;
		else if (nextMove == vertex(y, x) + width)
			return Direction.DOWN;
		else if (nextMove == vertex(y, x) - width)
			return Direction.UP;
		throw new GoblinBlockedException("The goblin is stuck behind some walls!");
	}

	public int getWidth() {
		return width;
	}

	public boolean isDone() {
		return isPlayerDead() || areAllEnemiesDead();
	}

	public boolean isPlayerDead() {
		return !player.isAlive();
	}

	private void monsterCombatSystem(Monster monster) {
		Spell selectedSpell = monster.selectSpell();
		ArrayList<AliveMapObject> targets;
		if (selectedSpell == null) {
			monster.attack(player);
			return;
		}
		switch (selectedSpell) {
		case SIMPLE_HEAL:
			monster.throwHealSpell(monster);
			break;
		case TELEPORT:
			ArrayList<Coordinate> teleportableCoordinates = getAvailableCoordinates();
			monster.throwTeleportSpell(teleportableCoordinates);
			break;
		case MAGIC_MISSILE:
			targets = new ArrayList<AliveMapObject>();
			targets.add(player);
			if (monster.throwMagicMissileSpell(targets) != null)
				return;
			break;
		case FIREBALL:
			targets = new ArrayList<AliveMapObject>();
			targets.add(player);
			if (monster.throwFireballSpell(targets) != null)
				return;
			break;
		}
	}

	private boolean moveAliveElement(AliveMapObject element, Direction direction) {
		try {
			Goblin g = (Goblin) element;
			Direction d = getOptimalDirectionForShortestPath(g, player);
			element.move(d, colisionElements);
			return true;
		} catch (ClassCastException | GoblinBlockedException e) {
			if (direction == null)
				return false;
			int x = element.getX();
			int y = element.getY();
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
			case LEFT:
				x -= 1;
				break;
			}
			if (x >= 0 && x < width && y >= 0 && y < height) {
				if (element.move(direction, colisionElements))
					return true;
			}
		}

		return false;
	}

	public void searchAndRemoveTraps() {
		int perception = player.getPerception();
		ArrayList<Trap> foundTraps = new ArrayList<Trap>();
		for (Trap t : traps) {
			if (player.getEuclideanDistanceTo(t) <= perception) {
				foundTraps.add(t);
			}
		}
		traps.removeAll(foundTraps);
	}

	public boolean searchForTreasure() {
		Coordinate playerCoordinate = player.getCoordinate();
		for (Treasure t : treasures) {
			if (t.isSameCoordinates(playerCoordinate)) {
				treasures.remove(t);
				t.discover(player);
				return true;
			}
		}
		ArrayList<Coordinate> availableCoordinates = getAvailableCoordinates();
		Monster newMonster = RandomNumberGenerator.getMonsterFromBadTreasureSearch(availableCoordinates);
		if (newMonster != null)
			monsters.add(newMonster);

		return false;
	}

	public void updateMap(Direction direction) {
		moveAliveElement(player, direction);
		for (Trap t : traps) {
			if (t.isSameCoordinates(player)) {
				t.discover(player);
				traps.remove(t);
				break;
			}
		}
		for (Monster m : monsters) {
			monsterCombatSystem(m);
			List<Direction> randomDirections = RandomNumberGenerator.getRandomDirections();
			for (Direction d : randomDirections) {
				if (moveAliveElement(m, d))
					break;
			}
		}
		discoverMap();
	}

	private int vertex(int i, int j) {
		return i * width + j;
	}
}
