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
            temp.add(contents.firstElement());
            contents.remove(0);
        }

        if(!contents.isEmpty() && !contents.lastElement().flipped){
            contents.firstElement().flipped = true;
            contents.firstElement().moveable = true;
        }

        return temp;
    }

    public void addCards(Vector<Card> cards){
        for(int i = 0; i < cards.size(); ++i){
            if(!contents.isEmpty()) {
                cards.get(cards.size() - 1 - i).depth = contents.firstElement().depth + 1;
                contents.firstElement().childCard = cards.get(cards.size() - 1 - i);
                cards.get(cards.size() - 1 - i).parentCard = contents.firstElement();
            }
            else {
                cards.get(cards.size() - 1 - i).depth = 0;
            }
            contents.insertElementAt(cards.get(cards.size()-1-i), 0);
        }
    }

    public boolean canAddCard(Card card){
        if (contents.isEmpty() && card.value == 13){
            return true;
        }
        else if(contents.get(0).suit == "diamonds" || contents.get(0).suit == "hearts"){
            return (card.suit == "clubs" || card.suit == "spades") && card.value < contents.get(0).value;
        }
        else {
            return (card.suit == "diamonds" || card.suit == "hearts") && card.value < contents.get(0).value;
        }
    }

    public boolean isEmpty(){
        return contents.isEmpty();
    }
}
