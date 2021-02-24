package se.yrgo.am3.gameobjects;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Pipe {
    private int xLoc, yLoc;
    private int width, height;
    private final String pos;
    private final Image pipeImage;


    public Pipe(int initialWidth, int initialHeight,int x, int y, String inpos) throws IOException {
        this.width = initialWidth;
        this.height = initialHeight;
        this.xLoc = x;
        this.yLoc = y;
        this.pos = inpos;
        if (this.pos.equals("top")) {
            pipeImage = setImage("src/main/resources/topPipe.png");
        } else {
            pipeImage = setImage("src/main/resources/bottomPipe.png");
        }
    }

    public BufferedImage setImage(String path) throws IOException {
        BufferedImage image = null;
        try {
            image = ImageIO.read(new File(path));
        } catch (IOException e) {
            throw new IOException("Could not load image");
        }
        return image;
    }

    public Image getPipeImage() {
        return pipeImage;
    }

    public String getPos() {
        return pos;
    }

    public int getxLoc() {
        return xLoc;
    }

    public void setxLoc(int xLoc) {
        this.xLoc = xLoc;
    }

    public int getHeight() {
        return height;
    }


    public int getyLoc() {
        return yLoc;
    }

    public void setyLoc(int yLoc) {
        this.yLoc = yLoc;
    }

    public int getWidth() {
        return width;
    }


    public void setItAll(int x, int y, int h, int w) {
        this.xLoc = x;
        this.yLoc = y;
        this.height = h;
        this.width = w;
    }

    public Rectangle getRectangle() {
        return (new Rectangle(xLoc, yLoc, width, height));
    }
}
