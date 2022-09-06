package GameLogic.Components;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

public class Card {

    public String suit;
    public int value;
    public boolean flipped;
    public boolean moveable;
    public boolean visible;
    public Point location;
    public int width;
    public int height;
    public int depth;
    public Card parentCard;
    public Card childCard;
    public String owner;
    public int ownerSubAddress;
    public BufferedImage image;
}
