package br.ic.unicamp.mc322.engine;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import br.ic.unicamp.mc322.aliveobjects.Barbarian;
import br.ic.unicamp.mc322.aliveobjects.Dwarf;
import br.ic.unicamp.mc322.aliveobjects.Elf;
import br.ic.unicamp.mc322.aliveobjects.Hero;
import br.ic.unicamp.mc322.aliveobjects.Wizard;
import br.ic.unicamp.mc322.map.Direction;

public class InputOutputUtils {

	private static final int HERO_MENU_OPTIONS = 4;
	private static final String HERO_MENU_PATH = "src/heroselection";
	private static final int INTERACTION_MENU_OPTIONS = 5;
	private static final String INTERACTION_MENU_PATH = "src/interaction";
	private static final int MAP_MENU_OPTIONS = 2;
	private static final String MAP_MENU_PATH = "src/greetings";
	private static final Scanner scanner = new Scanner(System.in);

	public static <R> void printArrayList(ArrayList<R> listOfElements) {
		for (int i = 0; i < listOfElements.size(); i++) {
			System.out.println((i + 1) + " - " + listOfElements.get(i));
		}
	}

	public static void printAttackResults(int damage) {
		if (damage > 0)
			System.out.print("Você ataca o monstro e consegue acerta-lo, desferindo " + damage + " pontos de dano!");
		else
			System.out.print("Você tenta atacar o monstro, mas ele se defende com sucesso.");
		System.out.println("\n\n");
	}

	public static void printFile(String path) {
		try {
			Scanner scanner = new Scanner(new File(path));
			while (scanner.hasNextLine())
				System.out.println(scanner.nextLine());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.out.println(
					"O arquivo necessário para a execução do programa que deveria no caminho parcial do projeto " + path
							+ " não foi encontrado. Terminando a execução.");
			System.exit(1);
		}
	}

	public static int printInteractionMenuAndChooseAction() {
		printFile(INTERACTION_MENU_PATH);
		int option = readOptionFromKeyboard(INTERACTION_MENU_OPTIONS);
		System.out.println("Jogando os dados vermelhos ...");
		return option;
	}

	public static void printSpellResults(int damage) {
		if (damage > 0)
			System.out.print(
					"Você lança a magia sob o monstro e consegue acerta-lo, desferindo " + damage + " pontos de dano!");
		else
			System.out.print("Você tenta lança a magia contra o monstro, mas ele se defende com sucesso.");
		System.out.println("\n\n");
	}

	public static Direction readCommandFromKeyboard() {
		while (true) {
			try {
				String command = scanner.nextLine();
				if (command.equals("exit") || command.equals("e")) {
					System.exit(1);
					return null;
				} else if (command.equals("up") || command.equals("w"))
					return Direction.UP;
				else if (command.equals("down") || command.equals("s"))
					return Direction.DOWN;
				else if (command.equals("left") || command.equals("a"))
					return Direction.LEFT;
				else if (command.equals("right") || command.equals("d"))
					return Direction.RIGHT;
				else if (command.equals("u")) {
					return null;
				} else
					throw new IOException();
			} catch (IOException e) {
				System.out.println("Digite um comando válido (w,a,s,d,u) para andar com o seu herói!");
				continue;
			}
		}
	}

	public static int readOptionFromKeyboard(int numberOfOptions) {
		while (true) {
			try {
				int option = Integer.parseInt(scanner.nextLine());
				if (option >= 1 && option <= numberOfOptions)
					return option;
				else
					continue;
			} catch (NumberFormatException f) {
				System.out.println("Digite uma opção válida para selecionar!");
				System.out.println("Uma opção válida é um número inteiro entre 1 e " + numberOfOptions);
				continue;
			}
		}
	}

	public static <R> R selectElementFromArrayList(String message, ArrayList<R> listOfElements) {
		if (listOfElements.size() == 0) {
			return null;
		} else if (listOfElements.size() == 1)
			return listOfElements.get(0);
		if (message != null)
			System.out.println(message + "\n\n");
		printArrayList(listOfElements);
		int option = readOptionFromKeyboard(listOfElements.size());
		return listOfElements.get(option - 1);
	}

	public static Hero selectHeroType() {
		printFile(HERO_MENU_PATH);
		int option = readOptionFromKeyboard(HERO_MENU_OPTIONS);
		if (option == 1)
			return new Barbarian(0, 0);
		else if (option == 2)
			return new Dwarf(0, 0);
		else if (option == 3)
			return new Elf(0, 0);
		else
			return new Wizard(0, 0);
	}

	public static MapGenerationType selectMapGenerationType() {
		printFile(MAP_MENU_PATH);
		int option = readOptionFromKeyboard(MAP_MENU_OPTIONS);
		if (option == 1)
			return MapGenerationType.FILE;
		else
			return MapGenerationType.RANDOM;
	}

	public static Choice yesOrNoPrompt(String message) {
		if (message != null)
			System.out.println(message);
		System.out.println("Digite 1 para sim e 2 para não");
		if (readOptionFromKeyboard(2) == 1)
			return Choice.YES;
		return Choice.NO;
	}
}
