package se.yrgo.am3.gameobjects;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Birb {
    private Image birb;

    public Birb(boolean up) throws IOException {
        if (up) {
            BufferedImage b = ImageIO.read(this.getClass().getResource("birbupp.png"));
            birb = b;
        } else {
            BufferedImage b = ImageIO.read(this.getClass().getResource("birbner.png"));
            birb = b;
        }
    }
    /*
        public void scaleBirb(int width, int height) {
            birb = birb.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        }
    */
    public Image getBirb() {
        return birb;
    }
/*
    public int getHeight() {
        return birb.getHeight(null);
    }

    public int getWidth() {
        return birb.getWidth(null);
    }

    public BufferedImage getBI() {
        BufferedImage bi = new BufferedImage(birb.getWidth(null), birb.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics g = bi.getGraphics();
        g.drawImage(birb, 0, 0, null);
        g.dispose();
        return bi;
    }

 */
}
