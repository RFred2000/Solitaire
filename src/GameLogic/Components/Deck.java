package GameLogic.Components;

import java.util.*;

/**
 * Represents the draw pile in a game of solitaire
 * @author Richie
 */
public class Deck {
    private Vector<Card> contents;

    public Deck(Vector<Card> newDeck){
        contents = newDeck;
    }

    /**
     * Gets the top three or remaining cards from the deck
     * @return Vector with top three cards or remaining portion of the deck
     */
    public Vector<Card> get_deal(){
        Vector<Card> temp = new Vector<Card>();

        int currentDeal = 0;
        while(currentDeal < 3 && contents.size() > 0) {
            temp.add(contents.firstElement());
            contents.remove(0);
            currentDeal += 1;
        }

        return temp;
    }

    /**
     * Sets the contents of the deck to the provided vector
     * @param cards
     */
    public void set_contents(Vector<Card> cards){
        contents = cards;
    }
}
