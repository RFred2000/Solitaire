package GameLogic.Components;

import java.util.*;

public class Row {

    private Vector<Card> contents;

    public Row(Vector<Card> starterCards){
        contents = starterCards;
    }

    public Vector<Card> popOffTopCards(int cards_from_bottom){
        Vector<Card> temp = new Vector<Card>();
        for(int i = 0; i < cards_from_bottom; ++i){
            temp.insertElementAt(contents.firstElement(), 0);
            contents.remove(0);
        }

        if(!contents.isEmpty() && !contents.lastElement().flipped){
            contents.firstElement().flipped = true;
        }

        return temp;
    }

    public void addCards(Vector<Card> cards){
        while(cards.size() > 0){
            contents.insertElementAt(cards.lastElement(), 0);
            contents.remove(contents.size());
        }
    }

    public boolean canAddCards(Vector<Card> cards){
        if (contents.isEmpty() && cards.lastElement().value == 13){
            return true;
        }
        else if(contents.get(0).suit == "diamonds" || contents.get(0).suit == "hearts"){
            return (cards.lastElement().suit == "clubs" || cards.lastElement().suit == "spades") && cards.lastElement().value < contents.get(0).value;
        }
        else {
            return (cards.lastElement().suit == "diamonds" || cards.lastElement().suit == "hearts") && cards.lastElement().value < contents.get(0).value;
        }
    }
}
