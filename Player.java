package Othello;

import java.util.Scanner;

public class Player {
	static Scanner s = new Scanner(System.in);
	String name;
	char symbol;
	
	public Player(String name, char symbol) {
		this.name = name;
		this.symbol = symbol;
	}
	
	
	public static Player takePlayerInput(int playerNumber,char symb) {
		
		System.out.println("Enter player " + playerNumber + " name");
		String name = s.nextLine();
		
		char symbol = symb;
		return new Player(name, symbol);
	}
	
	
}
