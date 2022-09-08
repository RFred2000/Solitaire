package GameLogic.Components;

import java.util.Vector;

public class WastePile {

    private Vector<Card> contents;

    public WastePile(){
        contents = new Vector<Card>();
    }

    public Vector<Card> popOffContents(){
        Vector<Card> temp = new Vector<Card>(contents);
        contents.clear();
        return temp;
    }

    public void addContents(Vector<Card> set){
        for(int i = 0; i < set.size(); ++i){
            set.get(i).visible = false;
            set.get(i).movable = false;
            set.get(i).owner = null;
            contents.add(set.get(i));
        }
    }

    public boolean isEmpty(){
        return this.contents.isEmpty();
    }

}
