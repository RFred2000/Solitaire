package GameLogic.Components;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

public class Card {

    public String suit;
    public int value;
    public boolean flipped;
    public boolean moveable;
    public int xLocation;
    public int yLocation;
    public int width;
    public int height;
    public int depth;
    public BufferedImage image;
}
