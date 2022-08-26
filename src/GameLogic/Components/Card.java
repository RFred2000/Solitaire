package GameLogic.Components;

/**
 * Represents a single card in the game of solitaire
 * @author Richie
 */
public class Card {
    /** The suit of the card  */
    private String suit;
    /** The face value of the card */
    private int value;

    public Card(String suit, int value){
        this.suit = suit;
        this.value = value;
    }

    public String get_suit(){
        return suit;
    }

    public int get_value(){
        return value;
    }
}
