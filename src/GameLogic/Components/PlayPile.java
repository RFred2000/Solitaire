package GameLogic.Components;

/**
 * A class that inherits from pile in order to add additional
 * functionality to the Pile class
 */
public class PlayPile extends Pile {

    /**
     * Allows the user to get the top card of the play pile
     * @return The top card
     */
    public Card get_top_card(){
        Card temp = contents.firstElement();
        contents.remove(0);
        return temp;
    }
}
