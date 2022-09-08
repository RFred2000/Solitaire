package GameLogic.Components;

import java.awt.*;
import java.util.Vector;

public abstract class CardPlayContainer {

    protected Vector<Card> contents;
    protected Point playBoxLocation;

    abstract public Vector<Card> pickupCards(int numCards) throws Exception;
    abstract public void placeCards(Vector<Card> cards) throws Exception;
    abstract public boolean canPlaceCards(Vector<Card> cards);

    final public Point getPlayBoxLocation(){
        return playBoxLocation;
    }

    final public boolean isEmpty(){
        return contents.isEmpty();
    }
}
