package GameLogic.Components;

import java.util.*;

public class Foundation {

    private Vector<Card> contents;
    private String suit;

    public Foundation(String suit){
        this.suit = suit;
        contents = new Vector<Card>();
    }

    public boolean canAddCard(Card card){
        return (card.suit == this.suit) && ((contents.isEmpty() && card.value == 1) || card.value > contents.get(0).value);
    }

    public void addCard(Card card){
        contents.insertElementAt(card, 0);
    }

    public Card popOffTopCard(){
        Card temp = contents.firstElement();
        contents.remove(0);
        return temp;
    }

    public boolean is_complete(){
        return contents.firstElement().value == 13;
    }
}
