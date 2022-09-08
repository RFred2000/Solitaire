package GameLogic.Components;

import Interface.Physics;

import java.awt.*;
import java.util.*;

public class Row extends CardPlayContainer{

    private Vector<Card> contents;

    public Row(Vector<Card> contents, Point rowLocation){

        for(int i = 0; i < contents.size(); ++i){
            contents.get(i).location.x = rowLocation.x;
            contents.get(i).location.y = (int) Math.round(rowLocation.y + 0.2*(contents.size()-1-i)*Physics.CARD_HEIGHT);
            contents.get(i).depth = contents.size()-1-i;
            contents.get(i).owner = this;
            contents.get(i).visible = true;
        }
        this.contents = contents;
        this.contents.firstElement().flipped = true;
        this.contents.firstElement().movable = true;

        this.playBoxLocation = new Point(this.contents.firstElement().location);
    }

    @Override
    public Vector<Card> pickupCards(int numCards){
        Vector<Card> temp = new Vector<Card>();
        for(int i = 0; i < numCards; ++i){
            temp.insertElementAt(contents.firstElement(), 0);
            contents.remove(0);
        }

        temp.firstElement().parentCard = null;

        if(!contents.isEmpty()){
            contents.firstElement().flipped = true;
            contents.firstElement().movable = true;
            contents.firstElement().childCard = null;
            playBoxLocation.y -= numCards*0.2*Physics.CARD_HEIGHT;
        }
        else {
            playBoxLocation.y -= (numCards-1)*0.2*Physics.CARD_HEIGHT;
        }

        return temp;
    }

    @Override
    public void placeCards(Vector<Card> cards){

        for(int i = 0; i < cards.size(); ++i){
            cards.get(i).owner = this;
            if(!contents.isEmpty()) {
                cards.get(i).depth = contents.firstElement().depth + 1;
                contents.firstElement().childCard = cards.get(i);
                cards.get(i).parentCard = contents.firstElement();
                cards.get(i).location.x = contents.firstElement().location.x;
                cards.get(i).location.y = (int) Math.round(contents.firstElement().location.y + 0.2 * Physics.CARD_HEIGHT);
            }
            else {
                cards.get(i).depth = 0;
                cards.get(i).location = new Point(playBoxLocation);
            }
            contents.insertElementAt(cards.get(i), 0);
        }
        this.playBoxLocation = new Point(contents.firstElement().location);
    }

    @Override
    public boolean canPlaceCards(Vector<Card> cards){
        Card card = cards.firstElement();

        if (contents.isEmpty()){
            return card.value == 13;
        }
        else {
            if(contents.get(0).suit == "diamonds" || contents.get(0).suit == "hearts"){
                return (card.suit == "clubs" || card.suit == "spades") && card.value == contents.get(0).value-1 && card.value != 1;
            }
            else {
                return (card.suit == "diamonds" || card.suit == "hearts") && card.value == contents.get(0).value-1 && card.value != 1;
            }
        }
    }
}
