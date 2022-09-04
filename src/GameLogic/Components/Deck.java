package GameLogic.Components;

import java.util.*;

public class Deck {

    private Vector<Card> contents;

    public Deck(Vector<Card> newDeck){
        contents = newDeck;
    }

    public Vector<Card> popOffDeal(){
        Vector<Card> temp = new Vector<Card>();

        int currentDeal = 0;
        while(currentDeal < 3 && contents.size() > 0) {
            temp.add(contents.firstElement());
            contents.remove(0);
            currentDeal += 1;
        }

        return temp;
    }

    public void setContents(Vector<Card> cards){
        contents = cards;
    }

}
