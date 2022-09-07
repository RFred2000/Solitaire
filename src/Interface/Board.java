package Interface;

import GameLogic.Components.*;
import Interface.Physics;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.*;

public class Board extends JPanel {

    private class DepthComparator implements Comparator<Card> {

        @Override
        public int compare(Card o1, Card o2) {
            if (o1.depth > o2.depth) {
                return 1;
            }
            else if (o1.depth == o2.depth) {
                return 0;
            }
            else {
                return -1;
            }
        }
    }

    private Deck deck;
    private WastePile wastePile;
    private PlayPile playPile;

    private Vector<Card> cards;
    private BufferedImage flippedImage;
    private BufferedImage backgroundPicture;

    private int glowIncrement;

    public Board(){
        try {
            flippedImage = ImageIO.read(new File("./Resources/CardPictures/back_of_card.png"));
            backgroundPicture = ImageIO.read(new File("./Resources/pexels-fwstudio-172277.jpg"));
        }
        catch (Exception e){
            e.printStackTrace();
            System.exit(-1);
        }

        glowIncrement = 0;
    }

    public void attachCards(Vector<Card> cards){
        this.cards = cards;
    }

    public void attachDeck(Deck deck){
        this.deck = deck;
    }

    public void attachWastePile(WastePile wastePile){
        this.wastePile = wastePile;
    }

    public void attachPlayPile(PlayPile playPile){
        this.playPile = playPile;
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(Physics.WINDOW_WIDTH, Physics.WINDOW_HEIGHT);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;

        g2d.setStroke(new BasicStroke(3));
        g2d.drawImage(backgroundPicture, 0, 0, Physics.WINDOW_WIDTH, Physics.WINDOW_HEIGHT, this);

        for(int i = 0; i < Physics.FOUNDATION_LOCATIONS.size(); ++i){
            g2d.drawRect(Physics.FOUNDATION_LOCATIONS.get(i).x, Physics.FOUNDATION_LOCATIONS.get(i).y, Physics.CARD_WIDTH, Physics.CARD_HEIGHT);
        }

        for(int i = 0; i < Physics.ROW_LOCATIONS.size(); ++i){
            g2d.drawRect(Physics.ROW_LOCATIONS.get(i).x, Physics.ROW_LOCATIONS.get(i).y, Physics.CARD_WIDTH, Physics.CARD_HEIGHT);
        }

        if(deck.isEmpty() && (!playPile.isEmpty() || !wastePile.isEmpty())) {
            g2d.setColor(new Color(255, 0, 0, (int) Math.round(255*(Math.sin(System.currentTimeMillis()/100)+1)/2)));
            g2d.drawRect(Physics.DECK_LOCATION.x, Physics.DECK_LOCATION.y, Physics.CARD_WIDTH, Physics.CARD_HEIGHT);
        }

        cards.sort(new DepthComparator());
        for(int i = 0; i < cards.size(); ++i){
            Card temp = cards.get(i);
            if (temp.visible) {
                if (temp.flipped) {
                    g2d.drawImage(temp.image, temp.location.x, temp.location.y, Physics.CARD_WIDTH, Physics.CARD_HEIGHT, this);
                } else {
                    g2d.drawImage(this.flippedImage, temp.location.x, temp.location.y, Physics.CARD_WIDTH, Physics.CARD_HEIGHT, this);
                }
            }
        }
    }
}
