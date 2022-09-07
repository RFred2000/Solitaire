package GameLogic.Components;

import Interface.Physics;

import java.awt.*;
import java.util.*;

public class Row {

    private Vector<Card> contents;
    private int rowNumber;
    private Point lastCardLocation;

    public Row(Vector<Card> starterCards, int rowNumber){
        contents = starterCards;
        this.rowNumber = rowNumber;
        this.lastCardLocation = new Point(contents.firstElement().location);
    }

    public Vector<Card> popOffTopCards(int cards_from_bottom){
        Vector<Card> temp = new Vector<Card>();
        for(int i = 0; i < cards_from_bottom; ++i){
            temp.insertElementAt(contents.firstElement(), 0);
            contents.remove(0);
        }

        temp.firstElement().parentCard = null;

        if(!contents.isEmpty() && !contents.lastElement().flipped){
            contents.firstElement().flipped = true;
            contents.firstElement().movable = true;
        }

        if(!contents.isEmpty()){
            contents.firstElement().childCard = null;
            lastCardLocation = new Point(contents.firstElement().location);
        }
        else {
            lastCardLocation = Physics.ROW_LOCATIONS.get(rowNumber);
        }

        return temp;
    }

    public void addCards(Vector<Card> cards){
        for(int i = 0; i < cards.size(); ++i){
            cards.get(i).owner = "row";
            cards.get(i).ownerSubAddress = rowNumber;
            if(!contents.isEmpty()) {
                cards.get(i).depth = contents.firstElement().depth + 1;
                contents.firstElement().childCard = cards.get(i);
                cards.get(i).parentCard = contents.firstElement();
                cards.get(i).location.x = contents.firstElement().location.x;
                cards.get(i).location.y = (int) Math.round(contents.firstElement().location.y + 0.2 * Physics.CARD_HEIGHT);
            }
            else {
                cards.get(i).depth = 0;
                cards.get(i).location = Physics.ROW_LOCATIONS.get(rowNumber);
            }
            contents.insertElementAt(cards.get(i), 0);
        }
        this.lastCardLocation = new Point(contents.firstElement().location);
    }

    public boolean canAddCard(Card card){
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

    public boolean isEmpty(){
        return contents.isEmpty();
    }

    public Point getLastCardLocation(){
        if(contents.isEmpty()){
            return Physics.ROW_LOCATIONS.get(rowNumber);
        }
        else {
            return lastCardLocation;
        }
    }
}
