package br.ic.unicamp.mc322.engine;

import br.ic.unicamp.mc322.map.Map;

public abstract class GameEngine {
	private Map labyrinthMap;

	public GameEngine(Map labyrinthMap) {
		this.labyrinthMap = labyrinthMap;
	}

	public abstract void gameLoop();

	protected Map getLabyrinthMap() {
		return labyrinthMap;
	}
}
