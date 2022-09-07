package GameLogic.Components;

import Interface.Physics;

import java.awt.*;
import java.util.*;

public class Deck {

    private Vector<Card> contents;

    public Deck(Vector<Card> contents){
        this.contents = contents;
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
        for(int i = 0; i < cards.size(); ++i){
            cards.get(i).location = new Point(Physics.DECK_LOCATION);
            cards.get(i).flipped = false;
            cards.get(i).visible = true;
            cards.get(i).movable = false;
            cards.get(i).depth = 0;
            cards.get(i).owner = "deck";
        }

        contents = cards;
    }

    public boolean isEmpty(){
        return contents.isEmpty();
    }

}
