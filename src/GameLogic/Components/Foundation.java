package GameLogic.Components;

import Interface.Physics;

import java.awt.*;
import java.util.*;

public class Foundation {

    private Vector<Card> contents;
    private String suit;
    private int foundationNumber;
    private Point foundationLocation;

    public Foundation(String suit, int foundationNumber){
        this.suit = suit;
        this.foundationNumber = foundationNumber;
        contents = new Vector<Card>();
        this.foundationLocation = Physics.FOUNDATION_LOCATIONS.get(foundationNumber);
    }

    public boolean canAddCard(Card card){
        if (this.suit == "empty"){
            return card.value == 1;
        }
        else {
            if (card.suit == this.suit) {
                if (contents.isEmpty()) {
                    return card.value == 1;
                } else {
                    return card.value == contents.firstElement().value + 1;
                }
            } else {
                return false;
            }
        }
    }

    public void addCard(Card card){
        if(this.suit == "empty"){
            this.suit = card.suit;
        }

        card.depth = contents.size();
        card.location = new Point(Physics.FOUNDATION_LOCATIONS.get(foundationNumber));
        card.owner = "foundation";
        card.ownerSubAddress = foundationNumber;
        contents.insertElementAt(card, 0);

    }

    public Card popOffTopCard(){
        Card temp = contents.firstElement();
        contents.remove(0);
        if(contents.isEmpty()){
            suit = "empty";
        }

        return temp;
    }

    public boolean is_complete(){
        if(!contents.isEmpty()) {
            return contents.firstElement().value == 13;
        }
        else {
            return false;
        }
    }

    public Point getFoundationLocation(){
        return new Point(foundationLocation);
    }
}
