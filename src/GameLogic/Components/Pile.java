package GameLogic.Components;

import java.util.*;

/**
 * A class that can be used as a bases to the play and waste piles
 * @author Richie
 */
abstract class Pile {
    protected Vector<Card> contents;

    public Pile(){
        contents = new Vector<Card>();
    }

    /**
     * Adds cards in set to the pile
     * @param set
     */
    public void add_contents(Vector<Card> set){
        for(int i = 0; i < set.size(); ++i){
            contents.add(set.get(i));
        }
    }

    /**
     * Returns the contents of the pile
     * @return The contents of the pile
     */
    public Vector<Card> get_contents() {
        Vector<Card> temp = new Vector<Card>(contents);
        contents.clear();
        return temp;
    }
}
