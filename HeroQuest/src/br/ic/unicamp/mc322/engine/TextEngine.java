package br.ic.unicamp.mc322.engine;

import br.ic.unicamp.mc322.map.Direction;
import br.ic.unicamp.mc322.map.Map;

public class TextEngine extends GameEngine {
	private int numberOfMovesLeft;
	private TextRenderManager renderManager;
	private int NUMBER_OF_RED_DICE = 2;

	public TextEngine(Map map) {
		super(map);
		renderManager = new TextRenderManager(map.getWidth(), map.getHeight());
	}

	@Override
	public void gameLoop() {
		Map map = getLabyrinthMap();
		Direction newDirection;
		while (!map.isDone()) {
			renderManager.render(map);
			int option = InputOutputUtils.printInteractionMenuAndChooseAction();
			if (option == 1) {
				map.enterCombatMode();
			} else if (option == 2) {
				boolean foundTreasure = map.searchForTreasure();
				if (foundTreasure)
					System.out.println("Você encontrou um tesouro!");
				else
					System.out.println("Sem tesouro aqui :(");
			} else if (option == 3) {
				map.searchAndRemoveTraps();
			} else if (option == 4)
				map.enterSpellThrowingMode();
			if (option != 5)
				map.updateMap(null);
			if (map.isDone())
				break;
			renderManager.render(map);
			numberOfMovesLeft = RandomNumberGenerator.diceRoll(NUMBER_OF_RED_DICE);
			while (numberOfMovesLeft > 0) {
				newDirection = InputOutputUtils.readCommandFromKeyboard();
				if (newDirection == null)
					break;
				System.out.println("Voce ainda tem " + numberOfMovesLeft + " movimentos");
				numberOfMovesLeft -= 1;
				map.updateMap(newDirection);
				if (map.isDone())
					break;
				renderManager.render(map);

			}
		}
		renderManager.render(map);
		if (!map.isPlayerDead())
			System.out.println("Você venceu o jogo!");
		else
			System.out.println("Você morreu :(");
	}
}
