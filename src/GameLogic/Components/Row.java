package GameLogic.Components;

import java.util.*;

public class Row {

    private Vector<Card> contents;

    public Row(Vector<Card> starterCards){
        contents = starterCards;
    }

    public Card get_top_card(){
        Card temp = contents.get(0);
        contents.remove(0);
        return temp;
    }

    public void add_card(Card card){
        contents.insertElementAt(card, 0);
    }

    public boolean can_add_card(Card card){
        if(contents.get(0).get_suit() == "diamonds" || contents.get(0).get_suit() == "hearts"){
            return (card.get_suit() == "clubs" || card.get_suit() == "spades") && card.get_value() < contents.get(0).get_value();
        }
        else {
            return (card.get_suit() == "diamonds" || card.get_suit() == "hearts") && card.get_value() < contents.get(0).get_value();
        }
    }
}
