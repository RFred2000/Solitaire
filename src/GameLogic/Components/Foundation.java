package GameLogic.Components;

import java.util.*;

/**
 * Represents a single foundation in the game of solitaire
 * @author Richie
 */
public class Foundation {
    private Vector<Card> contents;

    /**
     * Adds a card to the top of a foundation
     * @param card
     */
    public void add_card(Card card){
        contents.insertElementAt(card, 0);
    }

    /**
     * Retrieves the top card from the foundation
     * @return The top card on the foundation
     */
    public Card get_top_card(){
        Card temp = contents.firstElement();
        contents.remove(0);
        return temp;
    }

    /**
     * Checks the foundation for completion
     * @return Boolean stating whether this foundation is complete
     */
    public boolean is_complete(){
        return contents.firstElement().get_value() == "king";
    }
}
