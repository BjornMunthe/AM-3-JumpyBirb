package se.yrgo.am3.ui;

import se.yrgo.am3.gameobjects.*;
import javax.swing.*;
import static javax.swing.JFrame.EXIT_ON_CLOSE;

public class App {

	public static void main(String[] args) {
			JFrame main = new JFrame("Jumpybirb");

			GameSurface gs = new GameSurface(500, 650);

			main.setSize(500, 650);
			main.setResizable(false);
			main.add(gs);
			main.addKeyListener(gs);
			main.setDefaultCloseOperation(EXIT_ON_CLOSE);
			main.setVisible(true);
	}

}
