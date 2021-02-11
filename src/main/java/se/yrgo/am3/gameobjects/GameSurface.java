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
    private Pipe firstPipe;
    private Pipe secondPipe;

    public GameSurface(final int width, final int height) {
        this.timer = new Timer(20, this);
        this.timer.start();

        firstPipe = new Pipe(100, 400);
        firstPipe.setxLoc(900);
        firstPipe.setyLoc(400);
        secondPipe = new Pipe(100, 200);
        secondPipe.setxLoc (900);
        secondPipe.setyLoc(0);
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


        //firstPipe.getRectangle();
        g.setColor(Color.green);
        g.fillRect(firstPipe.getxLoc(),firstPipe.getyLoc(),firstPipe.getWidth(), firstPipe.getHeight());

        g.setColor(Color.green);
        g.fillRect(secondPipe.getxLoc(),secondPipe.getyLoc(), secondPipe.getWidth(), secondPipe.getHeight());

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // this will trigger on the timer event
        // this is where we update positions of different elements in the game
        // this is where we set it to game over

        firstPipe.setxLoc(firstPipe.getxLoc() - 1);
//        firstPipe.setyLoc(400);

        secondPipe.setxLoc(secondPipe.getxLoc() - 1);
//        secondPipe.setyLoc(0);


        this.repaint();
    }
}