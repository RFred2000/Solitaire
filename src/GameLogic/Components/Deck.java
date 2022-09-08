package GameLogic.Components;

import Interface.Physics;

import java.awt.*;
import java.util.*;

public class Deck {

    private Vector<Card> contents;

    public Deck(Vector<Card> contents){
        for(int i = 0; i < contents.size(); ++i) {
            contents.get(i).flipped = false;
            contents.get(i).movable = false;
            contents.get(i).visible = true;
            contents.get(i).location = new Point(Physics.DECK_LOCATION);
            contents.get(i).owner = null;
            contents.get(i).depth = 0;
        }
        this.contents = contents;
    }

    public Vector<Card> popOffDDeal(){

        Vector<Card> temp = new Vector<Card>();
        for(int i = 0; i < 3; ++i){
            if(contents.isEmpty()){
                break;
            }

            temp.add(contents.firstElement());
            contents.remove(0);
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
            cards.get(i).owner = null;
        }

        contents = cards;
    }

    public boolean isEmpty(){
        return contents.isEmpty();
    }
}
