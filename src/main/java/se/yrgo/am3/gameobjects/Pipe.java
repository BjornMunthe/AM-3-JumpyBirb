package se.yrgo.am3.gameobjects;

import java.awt.*;

public class Pipe {
    private int xLoc, yLoc;
    private int width, height;
    private String pos;

    public Pipe(int initialWidth, int initialHeight,int x, int y, String inpos) {
        this.width = initialWidth;
        this.height = initialHeight;
        this.xLoc = x;
        this.yLoc = y;
        this.pos = inpos;
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

    public void setHeight(int height) {
        this.height = height;
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

    public void setWidth(int width) {
        this.width = width;
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
