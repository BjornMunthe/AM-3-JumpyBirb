package se.yrgo.am3.gameobjects;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;


import javax.swing.*;

public class GameSurface extends JPanel implements ActionListener {
    private static final long serialVersionUID = 6260582674762246325L;

    private Timer timer;
    private Pipe firstPipe;
    private Pipe secondPipe;
    private Pipe thirdPipe;
    private Pipe fourthPipe;
    private List<Pipe> pipes;

    public GameSurface(final int width, final int height) {
        this.timer = new Timer(20, this);
        this.timer.start();

       addPipes();

    }

    public void addPipes() {
        pipes = new ArrayList<>();
        pipes.add(firstPipe = new Pipe(100, 400));
        firstPipe.setxLoc(900);
        firstPipe.setyLoc(400);
        pipes.add(secondPipe = new Pipe(100, 200));
        secondPipe.setxLoc (900);
        secondPipe.setyLoc(0);
            pipes.add(thirdPipe = new Pipe(0,0));
            thirdPipe.setxLoc(0);
            thirdPipe.setyLoc(0);
            pipes.add(fourthPipe = new Pipe(0,0));
            fourthPipe.setxLoc(0);
            fourthPipe.setyLoc(0);

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


        for (Pipe pipe : pipes) {
            g.setColor(Color.green);
            g.fillRect(pipe.getxLoc(), pipe.getyLoc(), pipe.getWidth(), pipe.getHeight());
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // this will trigger on the timer event
        // this is where we update positions of different elements in the game
        // this is where we set it to game over
        for (Pipe pipe: pipes) {
            pipe.setxLoc(pipe.getxLoc() - 1);
        }

        if (firstPipe.getxLoc() == 400) {
            thirdPipe.setItAll(900, 400, 400, 100);
            fourthPipe.setItAll(900, 0, 200, 100);
        }

        if (firstPipe.getxLoc() == -100) {
            firstPipe.setxLoc(900);
            secondPipe.setxLoc(900);
        }

        if (thirdPipe.getxLoc() == -100) {
            thirdPipe.setxLoc(900);
            fourthPipe.setxLoc(900);
        }



        this.repaint();
    }
}