package GameLogic.Components;

import java.util.*;

abstract class Pile {

    protected Vector<Card> contents;

    public Pile(){
        contents = new Vector<Card>();
    }

    public void addContents(Vector<Card> set){
        for(int i = 0; i < set.size(); ++i){
            contents.add(set.get(i));
        }
    }

    public Vector<Card> popOffContents() {
        Vector<Card> temp = new Vector<Card>(contents);
        contents.clear();
        return temp;
    }
}
