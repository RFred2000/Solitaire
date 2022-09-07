package GameLogic.Components;

import java.util.Vector;

public class WastePile extends Pile{

    public void addContents(Vector<Card> set){
        for(int i = 0; i < set.size(); ++i){
            set.get(i).visible = false;
            set.get(i).movable = false;
            set.get(i).owner = "waste pile";
            contents.add(set.get(i));
        }
    }

    public boolean isEmpty(){
        return this.contents.isEmpty();
    }

}
