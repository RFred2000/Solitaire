package GameLogic.Components;

import java.awt.*;
import java.util.*;

public class Foundation extends CardPlayContainer {

    private Vector<Card> contents;
    private String suit;

    public Foundation(Point foundationLocation){
        this.playBoxLocation = foundationLocation;
        this.suit = "empty";
        this.contents = new Vector<Card>();
    }

    @Override
    public Vector<Card> pickupCards(int numCards) throws Exception {

        if(numCards > 1){
            throw new Exception("Foundation cannot pop off more than one card");
        }

        Vector<Card> tempCards = new Vector<Card>();
        tempCards.add(contents.firstElement());
        contents.remove(0);

        if(contents.isEmpty()){
            suit = "empty";
        }

        return tempCards;
    }

    @Override
    public void placeCards(Vector<Card> cards) throws Exception {

        if(cards.size() > 1){
            throw new Exception("Foundation cannot accept more than one card at a time");
        }

        Card tempCard = cards.get(0);

        if(this.suit == "empty"){
            this.suit = tempCard.suit;
        }

        tempCard.depth = contents.size();
        tempCard.location = new Point(playBoxLocation);
        tempCard.owner = this;
        contents.insertElementAt(tempCard, 0);

    }

    @Override
    public boolean canPlaceCards(Vector<Card> cards){

        if(cards.size() > 1){
            return false;
        }

        Card temp = cards.get(0);

        if (this.suit == "empty"){
            return temp.value == 1;
        }

        if (temp.suit == this.suit) {
            if (contents.isEmpty()) {
                return temp.value == 1;
            }

            return temp.value == contents.firstElement().value + 1;
        }

        return false;
    }



    public boolean is_complete(){
        if(!contents.isEmpty()) {
            return contents.firstElement().value == 13;
        }
        else {
            return false;
        }
    }
}
