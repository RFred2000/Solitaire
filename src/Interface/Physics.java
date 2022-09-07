package Interface;

import GameLogic.Components.*;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;

public class Physics extends MouseAdapter {

    public static Point DECK_LOCATION;
    public static Vector<Point> PLAY_PILE_LOCATIONS;
    public static Vector<Point> ROW_LOCATIONS;
    public static Vector<Point> FOUNDATION_LOCATIONS;

    public static int WINDOW_WIDTH;
    public static int WINDOW_HEIGHT;

    public static int CARD_WIDTH;
    public static int CARD_HEIGHT;

    static {
        DECK_LOCATION = new Point();
        DECK_LOCATION.x = 100;
        DECK_LOCATION.y = 100;

        PLAY_PILE_LOCATIONS = new Vector<Point>();
        for(int i = 0; i < 3; ++i){
            Point tempPoint = new Point();
            tempPoint.y = 100;
            tempPoint.x = 350 - i*50;
            PLAY_PILE_LOCATIONS.add(tempPoint);
        }

        ROW_LOCATIONS = new Vector<Point>();
        for(int i = 0; i < 7; ++i){
            Point tempPoint = new Point();
            tempPoint.y = 400;
            tempPoint.x = 100 + i*150;
            ROW_LOCATIONS.add(tempPoint);
        }

        FOUNDATION_LOCATIONS = new Vector<Point>();
        for(int i = 0; i < 4; ++i) {
            Point tempPoint = new Point();
            tempPoint.y = 100;
            tempPoint.x = 550 + i * 150;
            FOUNDATION_LOCATIONS.add(tempPoint);
        }

        CARD_WIDTH = 100;
        CARD_HEIGHT = 150;

        WINDOW_WIDTH = 1300;
        WINDOW_HEIGHT = 1000;
    }

    private Vector<Card> cards;

    private Card movingCard;
    private Vector<Point> movingCardPriorLocations;
    private Vector<Integer> movingCardPriorDepths;

    private Deck deck;
    private PlayPile playPile;
    private WastePile wastePile;
    private Vector<Row> rows;
    private Vector<Foundation> foundations;

    private Vector<Point> rowLastCardLocations;

    private Point mouseDelta;

    public Physics() {
        mouseDelta = new Point();
        movingCardPriorLocations = new Vector<Point>();
        movingCardPriorDepths = new Vector<Integer>();
        rowLastCardLocations = new Vector<Point>();
    }

    public void attachCards(Vector<Card> cards){
        this.cards = cards;
    }

    public void attachDeck(Deck deck){
        this.deck = deck;
    }

    public void attachPlayPile(PlayPile playPile){
        this.playPile = playPile;
    }

    public void attachWastePile(WastePile wastePile){
        this.wastePile = wastePile;
    }

    public void attachRows(Vector<Row> rows){
        this.rows = rows;
        rowLastCardLocations = new Vector<Point>();
        for(int i = 0; i < rows.size(); ++i){
            Point basePosition = new Point(ROW_LOCATIONS.get(i));
            basePosition.y += i * 0.2 * CARD_HEIGHT;
            rowLastCardLocations.add(basePosition);
        }
    }

    public void attachFoundations(Vector<Foundation> foundations){
        this.foundations = foundations;
    }

    private boolean cardClicked(Point clickerLocation, Point cardLocation){
        return (clickerLocation.x > cardLocation.x)
                && (clickerLocation.y > cardLocation.y)
                && (clickerLocation.x < cardLocation.x + CARD_WIDTH)
                && (clickerLocation.y < cardLocation.y + CARD_HEIGHT);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(cardClicked(e.getPoint(), DECK_LOCATION)){
            if(deck.isEmpty()){
                wastePile.addContents(playPile.popOffContents());
                deck.setContents(wastePile.popOffContents());
            }
            else {
                wastePile.addContents(playPile.popOffContents());
                playPile.addContents(deck.popOffDeal());
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        for(int i = 0; i < cards.size(); ++i){
            Card tempCard = cards.get(i);

            boolean test = cardClicked(e.getPoint(), tempCard.location);

            if(test
            && ((movingCard == null) || (tempCard.depth > movingCard.depth))
            && (tempCard.movable)) {
                movingCard = tempCard;
            }
        }

        if(movingCard != null){
            mouseDelta.x = movingCard.location.x - e.getPoint().x;
            mouseDelta.y = movingCard.location.y - e.getPoint().y;

            Card currentCard = movingCard;

            int index = 0;
            int baseDepth = 100;

            while(currentCard != null) {
                movingCardPriorLocations.add(new Point(currentCard.location));
                movingCardPriorDepths.add(currentCard.depth);

                currentCard.depth = baseDepth + index;
                ++index;
                currentCard = currentCard.childCard;
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (movingCard != null) {

            try {

                String moveLocation = null;
                int rowNumber = -1;
                int foundationNumber = -1;

                for(int i = 0; i < rows.size(); ++i){
                    if(cardClicked(e.getPoint(), rows.get(i).getLastCardLocation())){
                        moveLocation = "row";
                        rowNumber = i;
                        break;
                    }
                }

                if(moveLocation == null) {
                    for (int i = 0; i < foundations.size(); ++i) {
                        if(cardClicked(e.getPoint(), foundations.get(i).getFoundationLocation())){
                            moveLocation = "foundation";
                            foundationNumber = i;
                        }
                    }
                }

                if(moveLocation == null){
                    throw new Exception("No Move");
                }

                if (movingCard.owner.equals("play pile")) {

                    if (moveLocation.equals("foundation") && foundations.get(foundationNumber).canAddCard(movingCard)) {
                        foundations.get(foundationNumber).addCard(playPile.popOffTopCard());

                    } else if (moveLocation.equals("row") && rows.get(rowNumber).canAddCard(movingCard)) {
                        // Pop off the card from the play pile
                        Card playPileCard = playPile.popOffTopCard();

                        // Add the card to the row
                        Vector<Card> tempCardVector = new Vector<Card>();
                        tempCardVector.add(playPileCard);
                        rows.get(rowNumber).addCards(tempCardVector);

                    } else {
                        throw new Exception("No Move");
                    }
                } else if (movingCard.owner.equals("foundation")) {
                    if (moveLocation.equals("foundation") && foundations.get(foundationNumber).canAddCard(movingCard)){
                        foundations.get(foundationNumber).addCard(foundations.get(movingCard.ownerSubAddress).popOffTopCard());
                    }
                    else if (moveLocation.equals("row") && rows.get(rowNumber).canAddCard(movingCard)) {
                        // Pop the top card off of the foundation
                        Card foundationCard = foundations.get(foundationNumber).popOffTopCard();

                        // Add the card to the row
                        Vector<Card> tempCardVector = new Vector<Card>();
                        tempCardVector.add(foundationCard);
                        rows.get(rowNumber).addCards(tempCardVector);
                    } else {
                        throw new Exception("No Move");
                    }
                } else if (movingCard.owner.equals("row")) {

                    // Figure out how many cards are moving
                    int cardsToMove = 0;
                    Card currentCard = movingCard;
                    while (currentCard != null) {
                        cardsToMove += 1;
                        currentCard = currentCard.childCard;
                    }

                    if (moveLocation.equals("foundation") && cardsToMove == 1 && foundations.get(foundationNumber).canAddCard(movingCard)) {
                        // Pop the cards off of the row
                        Vector<Card> movingCards = rows.get(movingCard.ownerSubAddress).popOffTopCards(cardsToMove);

                        // Add the conditioned cards to the foundation
                        foundations.get(foundationNumber).addCard(movingCards.firstElement());
                    } else if (moveLocation.equals("row") && rows.get(rowNumber).canAddCard(movingCard)) {
                        // Add the cards to the new row
                        rows.get(rowNumber).addCards(rows.get(movingCard.ownerSubAddress).popOffTopCards(cardsToMove));
                    } else {
                        throw new Exception("No Move");
                    }
                }
            }
            catch(Exception exception) {
                if (exception.getMessage() == "No Move") {

                    int index = 0;
                    Card currentCard = movingCard;

                    while(currentCard != null){
                        currentCard.location = new Point(movingCardPriorLocations.get(index));
                        currentCard.depth = movingCardPriorDepths.get(index);
                        currentCard = currentCard.childCard;
                        ++index;
                    }
                }
                else {
                    exception.printStackTrace();
                    System.exit(-1);
                }
            }
            finally {
                movingCard = null;
                movingCardPriorDepths.clear();
                movingCardPriorLocations.clear();
            }
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if(movingCard != null) {
            movingCard.location.x = e.getPoint().x + mouseDelta.x;
            movingCard.location.y = e.getPoint().y + mouseDelta.y;

            Card currentCard = movingCard.childCard;
            while(currentCard != null){
                currentCard.location.y = (int) Math.round(currentCard.parentCard.location.y + 0.2*CARD_HEIGHT);
                currentCard.location.x = currentCard.parentCard.location.x;
                currentCard = currentCard.childCard;
            }
        }
    }
}
