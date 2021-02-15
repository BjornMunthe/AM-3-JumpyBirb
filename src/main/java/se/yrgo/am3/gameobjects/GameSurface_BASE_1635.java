package se.yrgo.am3.gameobjects;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
        g.setColor(Color.cyan);
        g.fillRect(0, 0, d.width, d.height);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // this will trigger on the timer event
        // this is where we update positions of different elements in the game
        // this is where we set it to game over

        this.repaint();
    }
}
