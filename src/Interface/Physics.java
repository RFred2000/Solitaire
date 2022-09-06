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
            tempPoint.x = 400 - i*50;
            PLAY_PILE_LOCATIONS.add(tempPoint);
        }

        ROW_LOCATIONS = new Vector<Point>();
        for(int i = 0; i < 7; ++i){
            Point tempPoint = new Point();
            tempPoint.y = 400;
            tempPoint.x = 100 + i*200;
            ROW_LOCATIONS.add(tempPoint);
        }

        FOUNDATION_LOCATIONS = new Vector<Point>();
        for(int i = 0; i < 4; ++i) {
            Point tempPoint = new Point();
            tempPoint.y = 100;
            tempPoint.x = 700 + i * 200;
            FOUNDATION_LOCATIONS.add(tempPoint);
        }

        CARD_WIDTH = 100;
        CARD_HEIGHT = 150;
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

    @Override
    public void mouseClicked(MouseEvent e) {
        int mouseX = e.getX();
        int mouseY = e.getY();
        if((mouseX > DECK_LOCATION.x)
                && (mouseY > DECK_LOCATION.y)
                && (mouseX < DECK_LOCATION.x + CARD_WIDTH)
                && (mouseY < DECK_LOCATION.y + CARD_HEIGHT)){

            if(deck.isEmpty()){
                Vector<Card> playPileContents = playPile.popOffContents();

                for(int i = 0; i < playPileContents.size(); ++i){
                    playPileContents.get(i).visible = false;
                    playPileContents.get(i).moveable = false;
                }

                wastePile.addContents(playPileContents);

                Vector<Card> wastePileContents = wastePile.popOffContents();

                for (int i = 0; i < wastePileContents.size(); ++i){
                    wastePileContents.get(i).location = new Point(DECK_LOCATION);
                    wastePileContents.get(i).flipped = false;
                    wastePileContents.get(i).visible = true;
                    wastePileContents.get(i).moveable = false;
                    wastePileContents.get(i).depth = 0;
                    wastePileContents.get(i).owner = "deck";
                }

                deck.setContents(wastePileContents);
            }
            else {
                Vector<Card> playPileContents = playPile.popOffContents();

                for(int i = 0; i < playPileContents.size(); ++i){
                    playPileContents.get(i).visible = false;
                    playPileContents.get(i).moveable = false;
                    playPileContents.get(i).owner = "waste pile";
                }
                wastePile.addContents(playPileContents);

                Vector<Card> dealContents = deck.popOffDeal();
                for(int i = 0; i < dealContents.size(); ++i){
                    dealContents.get(i).location = new Point(PLAY_PILE_LOCATIONS.get(i));
                    dealContents.get(i).depth = dealContents.size()-1-i;
                    dealContents.get(i).flipped = true;
                    dealContents.get(i).owner = "play pile";
                }

                dealContents.firstElement().moveable = true;
                playPile.addContents(dealContents);
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        for(int i = 0; i < cards.size(); ++i){
            int mouseX = e.getX();
            int mouseY = e.getY();
            Card tempCard = cards.get(i);

            if((mouseX > tempCard.location.x)
                    && (mouseY > tempCard.location.y)
                    && (mouseX < tempCard.location.x + tempCard.width)
                    && (mouseY < tempCard.location.y + tempCard.height)
                    && ((movingCard == null) || (tempCard.depth > movingCard.depth))
                    && (tempCard.moveable)) {

                movingCard = tempCard;
            }
        }

        if(movingCard != null){
            mouseDelta.x = movingCard.location.x - e.getPoint().x;
            mouseDelta.y = movingCard.location.y - e.getPoint().y;

            Point tempPoint = new Point();
            Card currentCard = movingCard;

            int index = 0;
            int baseDepth = 100;

            while(currentCard != null) {
                tempPoint.x = currentCard.location.x;
                tempPoint.y = currentCard.location.y;

                movingCardPriorLocations.add(new Point(tempPoint));
                movingCardPriorDepths.add(movingCard.depth);

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

                for(int i = 0; i < rowLastCardLocations.size(); ++i){
                    if (e.getPoint().x > rowLastCardLocations.get(i).x
                    && e.getPoint().y > rowLastCardLocations.get(i).y
                    && e.getPoint().x < rowLastCardLocations.get(i).x + CARD_WIDTH
                    && e.getPoint().y < rowLastCardLocations.get(i).y + CARD_HEIGHT){

                        moveLocation = "row";
                        rowNumber = i;
                        break;
                    }
                }

                if(moveLocation == null) {
                    for (int i = 0; i < FOUNDATION_LOCATIONS.size(); ++i) {
                        if (e.getPoint().x > FOUNDATION_LOCATIONS.get(i).x
                                && e.getPoint().y > FOUNDATION_LOCATIONS.get(i).y
                                && e.getPoint().x < FOUNDATION_LOCATIONS.get(i).x + CARD_WIDTH
                                && e.getPoint().y < FOUNDATION_LOCATIONS.get(i).y + CARD_HEIGHT) {
                            moveLocation = "foundation";
                            foundationNumber = i;
                        }
                    }
                }

                if(moveLocation == null){
                    throw new Exception("No Move");
                }

                if (movingCard.owner.equals("play pile")) {

                    if (moveLocation.equals("foundation")) {
                        // Pop off the card from the play pile
                        Card playPileCard = playPile.popOffTopCard();

                        // Condition the card for its new location
                        playPileCard.location = new Point(FOUNDATION_LOCATIONS.get(foundationNumber));
                        playPileCard.owner = "foundation";
                        playPileCard.ownerSubAddress = foundationNumber;

                        // Add the card to the foundation
                        foundations.get(foundationNumber).addCard(playPileCard);

                    } else if (moveLocation.equals("row")) {
                        // Pop off the card from the play pile
                        Card playPileCard = playPile.popOffTopCard();

                        // Condition the card for its new location
                        playPileCard.owner = "row";
                        playPileCard.ownerSubAddress = rowNumber;
                        playPileCard.location.x = rowLastCardLocations.get(rowNumber).x;
                        if(rows.get(rowNumber).isEmpty()){
                            playPileCard.location.y = rowLastCardLocations.get(rowNumber).y;
                        }
                        else {
                            playPileCard.location.y = (int) Math.round(rowLastCardLocations.get(rowNumber).y + 0.2 * CARD_HEIGHT);
                        }

                        // Change the location of the last card in that row
                        rowLastCardLocations.set(rowNumber, new Point(playPileCard.location));

                        // Add the card to the row
                        Vector<Card> tempCardVector = new Vector<Card>();
                        tempCardVector.add(playPileCard);
                        rows.get(rowNumber).addCards(tempCardVector);

                    } else {
                        throw new Exception("No Move");
                    }
                } else if (movingCard.owner.equals("foundation")) {
                    if (moveLocation.equals("row") && rows.get(foundationNumber).canAddCard(movingCard)) {
                        // Pop the top card off of the foundation
                        Card foundationCard = foundations.get(foundationNumber).popOffTopCard();

                        // Condition the card for its new location
                        foundationCard.owner = "row";
                        foundationCard.ownerSubAddress = rowNumber;
                        foundationCard.location.x = rowLastCardLocations.get(rowNumber).x;
                        if(rows.get(rowNumber).isEmpty()){
                            foundationCard.location.y = rowLastCardLocations.get(rowNumber).y;
                        }
                        else {
                            foundationCard.location.y = (int) Math.round(rowLastCardLocations.get(rowNumber).y + 0.2 * CARD_HEIGHT);
                        }

                        // Change the location of the last card in that row
                        rowLastCardLocations.set(rowNumber, new Point(foundationCard.location));

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

                        // Fix prior row last card locations
                        if (!rows.get(movingCard.ownerSubAddress).isEmpty()){
                            rowLastCardLocations.get(movingCard.ownerSubAddress).y = (int) Math.round(movingCardPriorLocations.firstElement().y - 0.2*CARD_HEIGHT);
                        }
                        else {
                            rowLastCardLocations.get(movingCard.ownerSubAddress).y = movingCardPriorLocations.firstElement().y;
                        }

                        // Condition the cards for their new location
                        movingCards.firstElement().owner = "foundation";
                        movingCards.firstElement().ownerSubAddress = foundationNumber;
                        movingCards.firstElement().location = new Point(FOUNDATION_LOCATIONS.get(foundationNumber));

                        // Add the conditioned cards to the foundation
                        foundations.get(foundationNumber).addCard(movingCards.firstElement());
                    } else if (moveLocation.equals("row") && rows.get(rowNumber).canAddCard(movingCard)) {
                        // Pop the cards off of the row
                        Vector<Card> movingCards = rows.get(movingCard.ownerSubAddress).popOffTopCards(cardsToMove);

                        // Fix prior row last card locations
                        if (!rows.get(movingCard.ownerSubAddress).isEmpty()){
                            rowLastCardLocations.get(movingCard.ownerSubAddress).y = (int) Math.round(movingCardPriorLocations.firstElement().y - 0.2*CARD_HEIGHT);
                        }
                        else {
                            rowLastCardLocations.get(movingCard.ownerSubAddress).y = movingCardPriorLocations.firstElement().y;
                        }

                        // Condition all the cards for the new row
                        for (int i = 0; i < movingCards.size(); ++i) {
                            movingCards.get(movingCards.size() - 1 - i).ownerSubAddress = rowNumber;
                            movingCards.get(movingCards.size() - 1 - i).location.x = rowLastCardLocations.get(rowNumber).x;
                            if(rows.get(rowNumber).isEmpty()){
                                movingCards.get(movingCards.size() - 1 - i).location.y = rowLastCardLocations.get(rowNumber).y;
                            }
                            else {
                                movingCards.get(movingCards.size() - 1 - i).location.y = (int) Math.round(rowLastCardLocations.get(rowNumber).y + 0.2 * CARD_HEIGHT);
                            }


                        }

                        // Change the location of the last card in the chosen row
                        currentCard = movingCard;
                        while(currentCard.childCard != null){
                            currentCard = currentCard.childCard;
                        }

                        rowLastCardLocations.set(rowNumber, new Point(currentCard.location));

                        // Add the cards to the new row
                        rows.get(rowNumber).addCards(movingCards);
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
