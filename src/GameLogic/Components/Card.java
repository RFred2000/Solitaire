package GameLogic.Components;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Card {

    public Card(String suit, int value){
        this.suit = suit;
        this.value = value;
        this.location = new Point();
    }

    public String suit;
    public int value;

    public boolean flipped;
    public boolean movable;
    public boolean visible;

    public Point location;
    public int depth;

    public CardPlayContainer owner;
    public Card parentCard;
    public Card childCard;

    public BufferedImage image;
}
