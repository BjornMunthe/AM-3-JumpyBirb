package se.yrgo.am3.gameobjects;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;

import javax.swing.*;

public class GameSurface extends JPanel implements ActionListener {
    private static final long serialVersionUID = 6260582674762246325L;

    private Timer timer;

    public GameSurface(final int width, final int height) {
        this.timer = new Timer(20, this);
        this.timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        repaint(g);
    }

    //    Fill the background
    private void repaint(Graphics g) {
        final Dimension d = this.getSize();

        BufferedImage img = null;

        try {
            img = ImageIO.read(new File("img/869.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        g.drawImage(img, 0, 0, 1200, 800, this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // this will trigger on the timer event
        // this is where we update positions of different elements in the game
        // this is where we set it to game over

        this.repaint();
    }
}
