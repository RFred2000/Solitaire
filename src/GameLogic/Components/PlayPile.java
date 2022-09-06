package GameLogic.Components;

public class PlayPile extends Pile {

    public Card popOffTopCard(){
        Card temp = contents.firstElement();
        contents.remove(0);

        if (!contents.isEmpty()){
            contents.firstElement().moveable = true;
        }

        return temp;
    }
}
