package se.yrgo.am3.ui;

import se.yrgo.am3.gameobjects.*;
import javax.swing.*;
import java.io.IOException;

import static javax.swing.JFrame.EXIT_ON_CLOSE;

public class App {

	public static void main(String[] args) throws IOException {
			JFrame main = new JFrame("Jumpybirb");

			GameSurface gs = new GameSurface(1200, 800);

			main.setSize(1200, 800);
			main.setResizable(false);
			main.add(gs);
			main.addKeyListener(gs);
			main.setDefaultCloseOperation(EXIT_ON_CLOSE);
			main.setVisible(true);
	}

}
