package GameLogic.Components;

import java.util.*;

abstract class Pile {

    protected Vector<Card> contents;

    public Pile(){
        contents = new Vector<Card>();
    }

    public Vector<Card> popOffContents() {
        Vector<Card> temp = new Vector<Card>(contents);
        contents.clear();
        return temp;
    }
}
