package br.ic.unicamp.mc322.gamerun;

import br.ic.unicamp.mc322.aliveobjects.Hero;
import br.ic.unicamp.mc322.engine.GameEngine;
import br.ic.unicamp.mc322.engine.InputOutputUtils;
import br.ic.unicamp.mc322.engine.MapGenerationType;
import br.ic.unicamp.mc322.engine.TextEngine;
import br.ic.unicamp.mc322.map.Map;
import br.ic.unicamp.mc322.map.MapLoader;

public class Main {

	public static void gameRunner(GameEngine engine) {
		engine.gameLoop();
	}

	public static void main(String[] args) {
		MapLoader mapLoader = MapLoader.getInstance();
		MapGenerationType genType = InputOutputUtils.selectMapGenerationType();
		Hero player = InputOutputUtils.selectHeroType();
		Map map = mapLoader.loadMap(genType, player);
		gameRunner(new TextEngine(map));
	}

}
