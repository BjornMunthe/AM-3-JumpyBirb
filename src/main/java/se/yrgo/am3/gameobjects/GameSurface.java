package se.yrgo.am3.gameobjects;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import javax.imageio.ImageIO;
import javax.swing.*;

public class GameSurface extends JPanel implements ActionListener, KeyListener {
    private static final long serialVersionUID = 6260582674762246325L;
    private Timer timer;
    private Pipe firstPipe;
    private Pipe secondPipe;
    private Pipe thirdPipe;
    private Pipe fourthPipe;
    private List<Pipe> pipes;
    private Rectangle birb;
    private boolean gameOver;
    private int points = 0;
    private final int SCREEN_WIDTH;
    private final int SCREEN_HEIGHT;
    private final int PIPE_WIDTH;
    private final int PIPE_HEIGHT;
    private final int PIPE_GAP;
    //Flytta ut till birb
    private Image birbDown;
    private Image birbUp;
    private Image birbDead;
    private Image background;
    private int backgroundCounter;
    private int fallingCounter;
    private Highscore highscore;
    private boolean firstRound;

    public GameSurface(final int width, final int height) {
        this.timer = new Timer(12, this);
        this.firstRound = true;
        this.gameOver = false;
        // Kanske flytta till egen metod "setconstants()"
        this.SCREEN_WIDTH = width;
        this.SCREEN_HEIGHT = height;
        this.PIPE_WIDTH = SCREEN_WIDTH / 8;
        this.PIPE_HEIGHT = height;
        this.PIPE_GAP = height / 6;

        try {
            addPipes();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.birb = new Rectangle(SCREEN_WIDTH / 2, SCREEN_HEIGHT / 4, 60, 40);
        // Ut i klasserna evt
        this.background = setImage("src/main/resources/background.png");
        this.birbDown = setImage("src/main/resources/birbner.png");
        this.birbUp = setImage("src/main/resources/birbupp.png");
        this.birbDead = setImage("src/main/resources/Dead.png");
        this.highscore = new Highscore();
        this.repaint();
    }


    public BufferedImage setImage(String path) {
        BufferedImage image = null;
        try {
            image = ImageIO.read(new File(path));
        } catch (Exception e) {
            // TODO: handle exception
        }
        return image;
    }

    /**
     * Method to get a fitting starting position for the pipe
     * @return Random number fitted to the screen
     */
    private int calculateBottomY() {
            return ThreadLocalRandom.current().nextInt(PIPE_GAP+ SCREEN_HEIGHT / 10,SCREEN_HEIGHT - PIPE_GAP);
    }

    /**
     *Initiates the pipes at set start positions
     * @throws IOException
     */
    public void addPipes() throws IOException{
        pipes = new ArrayList<>();
        pipes.add(firstPipe = new Pipe(PIPE_WIDTH, PIPE_HEIGHT, SCREEN_WIDTH + PIPE_WIDTH, calculateBottomY(), "top"));
        pipes.add(secondPipe = new Pipe(PIPE_WIDTH, PIPE_HEIGHT, SCREEN_WIDTH + PIPE_WIDTH, firstPipe.getyLoc() - PIPE_GAP - PIPE_HEIGHT, "bot"));
        pipes.add(thirdPipe = new Pipe(0, 0, 0, 0, "top"));
        pipes.add(fourthPipe = new Pipe(0, 0, 0, 0, "bot"));
    }

    @Override
    protected void paintComponent(Graphics g) {
        repaint(g);
    }

    private void repaint(Graphics g) {
        if (firstRound) {
            setStartingScreen(g);
        }
        else {
            g.drawImage(background, -backgroundCounter / 2, 0, SCREEN_WIDTH * 2, SCREEN_HEIGHT, this);
        if (gameOver) {
            // Flytta ut till egen metod i gamSurface
            g.setColor(Color.yellow);
            g.setFont(new Font("Candara", Font.BOLD, SCREEN_HEIGHT / 12));
            if (!highscore.fileNotRead()) {
                String[] highscores = highscore.printHighscore();
                int y = 50;

                g.drawString("HIGSCORES", SCREEN_WIDTH/2-120, y);

                for (int i = 0; i < highscore.getPoints().length; i ++) {
                    y += 50;

                    g.drawString(highscores[i*2], SCREEN_WIDTH/14, y);
                    g.drawString(highscores[i*2+1], SCREEN_WIDTH - SCREEN_WIDTH/12, y);
                }
            } else {
                g.drawString(String.format("Although you scored %d fabulous points,", points), 10, SCREEN_HEIGHT/8);
                g.drawString("the highscores could unfortunately not", 10, (SCREEN_HEIGHT/8)*2);
                g.drawString("be retrieved!", 10, (SCREEN_HEIGHT/8)*3);
            }
            // Flytta ut till egen metod typ show gameOver() och använd SCREEN konstanter
            g.drawImage(birbDead, birb.x, birb.y, birb.width, birb.height, this);
            g.setColor(Color.green);
            g.fillRect(6*SCREEN_WIDTH/10,  2*SCREEN_HEIGHT/10, 3*SCREEN_WIDTH/10, 4*SCREEN_HEIGHT/10);
            g.setColor(Color.black);
            g.setFont(new Font("Arial", Font.BOLD, SCREEN_HEIGHT / 35));
            g.drawString("GAME OVER!", 13*SCREEN_WIDTH/20, 3 * SCREEN_HEIGHT / 10);
            g.drawString("Choose difficulty to play again", 6*SCREEN_WIDTH/10, 4 * SCREEN_HEIGHT / 10);
            g.drawString("1: EASY  2:NORMAL  3:HARD", 6*SCREEN_WIDTH/10, 5 * SCREEN_HEIGHT / 10);

        }
        // Under denna if sats ska ALLT komma som ska hända när spelet kör
            if (!gameOver && !firstRound) {
                //Paints the points
                g.setColor(Color.yellow);
                g.setFont(new Font("Candara", Font.BOLD, 90));
                g.drawString(String.valueOf(points), SCREEN_WIDTH / 2, SCREEN_HEIGHT / 9);
                drawPipes(g);
                paintBirb(g);
            }
        }
    }

    /**
     * Animates the pipes
     * @param g
     */

    private void drawPipes(Graphics g) {
        for (Pipe pipe : pipes) {
            g.drawImage(pipe.getPipeImage(), pipe.getxLoc(), pipe.getyLoc(), pipe.getWidth(), pipe.getHeight(), this);
        }
    }

    /**
     * Paints the starting screen with instructions
     * @param g
     */
    private void setStartingScreen(Graphics g) {
        g.setColor(Color.green);
        g.fillRect(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
        g.setColor(Color.black);
        g.setFont(new Font("Arial", Font.BOLD, SCREEN_HEIGHT / 15));
        g.drawString("Welcome to Jumpybirb!", SCREEN_WIDTH / 10, SCREEN_HEIGHT / 5);
        g.drawString("Press SPACE to jump.", SCREEN_WIDTH / 10, 2 * SCREEN_HEIGHT / 5);
        g.drawString("Choose difficulty to start", SCREEN_WIDTH / 10, 3 * SCREEN_HEIGHT / 5);
        g.drawString("1: EASY   2:NORMAL    3:HARD", SCREEN_WIDTH / 10, 4 * SCREEN_HEIGHT/ 5);
    }

    /**
     * animation for the birb
     * @param g
     */

    private void paintBirb(Graphics g) {
        if (fallingCounter < 20 && fallingCounter > 3 && !gameOver) {
            g.drawImage(birbDown, birb.x, birb.y, birb.width, birb.height, this);
        }
        else if (!gameOver) {
            g.drawImage(birbUp, birb.x, birb.y, birb.width, birb.height, this);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // moves the pipes and checks if it intersects with the bird.
        for (Pipe pipe : pipes) {
            pipe.setxLoc(pipe.getxLoc() - 2);
            if (pipe.getRectangle().intersects(birb)) {
                gameOver = true;
            }
        }
        // Game over if you hit the ground
        if (birb.y > SCREEN_HEIGHT - SCREEN_HEIGHT / 6) {
            gameOver = true;
        }
        // birbVelocity med fallingcounter som inparameter
        if (birb.y < (this.getSize().height - birb.height - 10)) {
            if (fallingCounter > 0 && fallingCounter < 5) {
                birb.translate(0, -(2 * fallingCounter * fallingCounter - 3 * fallingCounter - 2));
            }
            if (fallingCounter > 12 && fallingCounter < 26) {
                birb.translate(0, 1);
            }
            else if (fallingCounter >= 26 && fallingCounter < 40) {
                birb.translate(0, 3);
            }
            else if (fallingCounter >= 40) {
                birb.translate(0, 5);
            }
        }
        movePipes();

        //Increase points if pipes move past a point.
        if (firstPipe.getxLoc() == SCREEN_WIDTH / 2 - PIPE_WIDTH || thirdPipe.getxLoc() == SCREEN_WIDTH / 2 - PIPE_WIDTH) {
            points++;
        }
        // Sätter bakgrundsbilden dåligt namn på backgroundconter?? backgroundPosition evt
        if (backgroundCounter >= 2 * SCREEN_WIDTH) {
            backgroundCounter = 0;
        }
        // ska ligga vid birdVelocity
        fallingCounter++;
        // Ska ligga i repaint där man målar bakgrunden kanske?
        backgroundCounter++;
        this.repaint();
        if (gameOver) {
            // Bra kommentar eller egen metod highscoreblabla(boolean)
            if (points > highscore.getLowscore() && !highscore.fileNotRead()) {
                String defaultEntry = ((highscore.getLatestEntry()!= null) ? highscore.getLatestEntry() : "Put you're name here, champ!");
                String s = (String)JOptionPane.showInputDialog(
                        this,
                        "Concratulations!\n"
                                + "You've set a highscore\n"
                                + points + " points\n"
                        + "Please enter your name below:",
                        "Highscore",
                        JOptionPane.PLAIN_MESSAGE,
                        null,
                        null,
                        defaultEntry);
                highscore.newEntry(points, s);
            }
            points = 0;
            timer.stop();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        final int minHeight = 10;
        final int kc = e.getKeyCode();
        if (kc == KeyEvent.VK_SPACE && birb.y > minHeight && !gameOver) {
            fallingCounter = 0;
        }
        if (firstRound || gameOver){
            // Evt överflödigt att ha dessa i konstruktorn också
            try {
                addPipes();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            this.birb = new Rectangle(SCREEN_WIDTH / 2, SCREEN_HEIGHT / 4, 60, 40);
            // Hitta enum för 1,2 och 3
            // Göra metod som tar kc som inparameter
            if (kc == 49) {
                gameOver = false;
                timer.setDelay(16);
                firstRound = false;
                timer.start();
            }
            else if (kc == 50 || kc == KeyEvent.VK_SPACE) {
                gameOver = false;
                timer.setDelay(13);
                firstRound = false;
                timer.start();
            }
            else if (kc == 51) {
                gameOver = false;
                timer.setDelay(10);
                firstRound = false;
                timer.start();
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // do nothing
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // do nothing
    }

    /**
     * Method that handle the pipes movement and when they shoud be reset
     */
    private void movePipes() {
        if (firstPipe.getxLoc() == SCREEN_WIDTH / 2 - PIPE_WIDTH) {
            thirdPipe.setItAll(SCREEN_WIDTH, calculateBottomY(), PIPE_HEIGHT, PIPE_WIDTH);
            fourthPipe.setItAll(SCREEN_WIDTH, thirdPipe.getyLoc() - PIPE_GAP - PIPE_HEIGHT, PIPE_HEIGHT, PIPE_WIDTH);
        }
        if (firstPipe.getxLoc() == -PIPE_WIDTH) {
            firstPipe.setxLoc(SCREEN_WIDTH + PIPE_WIDTH);
            secondPipe.setxLoc(SCREEN_WIDTH + PIPE_WIDTH);
            firstPipe.setyLoc(calculateBottomY());
            secondPipe.setyLoc(firstPipe.getyLoc() - PIPE_GAP - PIPE_HEIGHT);
        }
        if (thirdPipe.getxLoc() == -PIPE_WIDTH) {
            thirdPipe.setxLoc(SCREEN_WIDTH + PIPE_WIDTH);
            fourthPipe.setxLoc(SCREEN_WIDTH + PIPE_WIDTH);
            thirdPipe.setyLoc(calculateBottomY());
            fourthPipe.setyLoc(thirdPipe.getyLoc() - PIPE_GAP - PIPE_HEIGHT);
        }
    }
}




