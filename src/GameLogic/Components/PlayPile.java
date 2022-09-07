package GameLogic.Components;

import Interface.Physics;

import java.awt.*;
import java.util.Vector;

public class PlayPile extends Pile {

    public void addContents(Vector<Card> set){
        for(int i = 0; i < set.size(); ++i){
            set.get(i).location = new Point(Physics.PLAY_PILE_LOCATIONS.get(i));
            set.get(i).depth = set.size()-1-i;
            set.get(i).flipped = true;
            set.get(i).owner = "play pile";
            contents.add(set.get(i));
        }
        contents.firstElement().movable = true;
    }

    public Card popOffTopCard(){
        Card temp = contents.firstElement();
        contents.remove(0);

        if (!contents.isEmpty()){
            contents.firstElement().movable = true;
        }

        return temp;
    }

    public boolean isEmpty(){
        return this.contents.isEmpty();
    }
}
