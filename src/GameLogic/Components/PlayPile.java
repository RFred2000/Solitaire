package GameLogic.Components;

public class PlayPile extends Pile {

    public Card popOffTopCard(){
        Card temp = contents.firstElement();
        contents.remove(0);
        return temp;
    }
}
