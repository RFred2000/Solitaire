package GameLogic.Components;

import Interface.Physics;

import java.awt.*;
import java.util.Vector;

public class PlayPile extends CardPlayContainer {

    public PlayPile(){
        contents = new Vector<Card>();
    }

    @Override
    public Vector<Card> pickupCards(int numCards) throws Exception {

        if(numCards != 1){
            throw new Exception("Cannot pickup more than one card from the play pile");
        }

        Card temp = contents.firstElement();
        contents.remove(0);

        if (!contents.isEmpty()){
            contents.firstElement().movable = true;
        }

        Vector<Card> tempCards = new Vector<Card>();
        tempCards.add(temp);

        return tempCards;
    }

    @Override
    public void placeCards(Vector<Card> cards){
        for(int i = 0; i < cards.size(); ++i){
            cards.get(i).location = new Point(Physics.PLAY_PILE_LOCATIONS.get(i));
            cards.get(i).depth = cards.size()-1-i;
            cards.get(i).flipped = true;
            cards.get(i).owner = this;
            contents.add(cards.get(i));
        }
        contents.firstElement().movable = true;
    }

    @Override
    public boolean canPlaceCards(Vector<Card> cards){
        return false;
    }

    public void addContents(Vector<Card> contents){
        for(int i = 0; i < contents.size(); ++i){
            contents.get(i).location = new Point(Physics.PLAY_PILE_LOCATIONS.get(i));
            contents.get(i).depth = contents.size()-1-i;
            contents.get(i).flipped = true;
            contents.get(i).owner = this;
            this.contents.add(contents.get(i));
        }

        contents.firstElement().movable = true;
    }

    public Vector<Card> popOffContents(){
        Vector<Card> temp = new Vector<Card>(contents);
        contents.clear();
        return temp;
    }
}
