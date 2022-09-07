package GameLogic.Components;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Card {

    public String suit;
    public int value;

    public boolean flipped;
    public boolean movable;
    public boolean visible;

    public Point location;
    public int depth;

    public String owner;
    public int ownerSubAddress;
    public Card parentCard;
    public Card childCard;

    public BufferedImage image;
}
