package Interface;

import GameLogic.Components.*;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;

public class Physics extends MouseAdapter {

    private Vector<Card> cards;
    private Card movingCard;

    private Deck deck;
    private PlayPile playPile;
    private Vector<Row> rows;
    private Vector<Foundation> foundations;

    private Point deckLocation;
    private Point playPileLocation;
    private Vector<Point> rowLocations;
    private Vector<Point> foundationLocations;

    private Point mouseDelta;

    public Physics(Vector<Card> cards) {
        this.cards = cards;
        mouseDelta = new Point();
    }

    public void attachDeck(Deck deck, Point deckLocation){
        this.deck = deck;
    }

    public void attachPlayPile(PlayPile playPile, Point playPileLocation){
        this.playPileLocation = playPileLocation;
    }

    public void attachRows(Vector<Row> rows, Vector<Point> rowLocations){
        this.rowLocations = rowLocations;
    }

    public void attachFoundations(Vector<Card> foundations, Vector<Point> foundationLocations){
        this.foundationLocations = foundationLocations;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        for(int i = 0; i < cards.size(); ++i){
            int mouseX = e.getX();
            int mouseY = e.getY();
            Card tempCard = cards.get(i);

            if((mouseX > tempCard.xLocation)
                    && (mouseY > tempCard.yLocation)
                    && (mouseX < tempCard.xLocation + tempCard.width)
                    && (mouseY < tempCard.yLocation + tempCard.height)
                    && ((movingCard == null) || (tempCard.depth > movingCard.depth))) {

                movingCard = tempCard;
                mouseDelta.x = movingCard.xLocation - e.getPoint().x;
                mouseDelta.y = movingCard.yLocation - e.getPoint().y;

            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        movingCard = null;
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if(movingCard != null) {
            movingCard.xLocation = e.getPoint().x + mouseDelta.x;
            movingCard.yLocation = e.getPoint().y + mouseDelta.y;
        }
    }
}
