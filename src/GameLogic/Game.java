package GameLogic;

import GameLogic.Components.*;

import java.util.*;

/**
 * The class which drives the game of solitaire
 * @author Richie
 */
public class Game {

    private Deck deck;

    private Foundation spadeFoundation;
    private Foundation clubFoundation;
    private Foundation heartFoundation;
    private Foundation diamondFoundation;

    private PlayPile playPile;
    private WastePile wastePile;

    private Vector<Row> rows;

    public Game(){
        Vector<Card> starterDeck = new Vector<Card>();
        starterDeck.add(new Card("diamonds", "two"));
        starterDeck.add(new Card("diamonds", "three"));
        starterDeck.add(new Card("diamonds", "four"));

        deck = new Deck(starterDeck);

        playPile = new PlayPile();
        wastePile = new WastePile();
    }



    public void deal() {
        wastePile.add_contents(playPile.get_contents());
        playPile.add_contents(deck.get_deal());
    }

}
