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

    Vector<Card> cards;
    BufferedImage flippedImage;

    public Board(){
        try {
            flippedImage = ImageIO.read(new File("./Resources/CardPictures/back_of_card.png"));
        }
        catch (Exception e){
            e.printStackTrace();
            System.exit(-1);
        }
    }

    public void attachCards(Vector<Card> cards){
        this.cards = cards;
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(1500, 1000);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        for(int i = 0; i < Physics.FOUNDATION_LOCATIONS.size(); ++i){
            g.drawRect(Physics.FOUNDATION_LOCATIONS.get(i).x, Physics.FOUNDATION_LOCATIONS.get(i).y, Physics.CARD_WIDTH, Physics.CARD_HEIGHT);
        }

        cards.sort(new DepthComparator());
        for(int i = 0; i < cards.size(); ++i){
            Card temp = cards.get(i);
            if (temp.visible) {
                if (temp.flipped) {
                    g.drawImage(temp.image, temp.location.x, temp.location.y, temp.width, temp.height, this);
                } else {
                    g.drawImage(this.flippedImage, temp.location.x, temp.location.y, temp.width, temp.height, this);
                }
            }
        }


    }
}
