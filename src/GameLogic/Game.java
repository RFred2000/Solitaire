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

        Vector<String> suits = new Vector<String();
        suits.add("diamonds");
        suits.add("hearts");
        suits.add("spades");
        suits.add("clubs");

        for(int i = 0; i < suits.size(); ++i){
            for(int x = 0; x < 13; ++x){
                starterDeck.add(new Card(suits.get(i), x+1));
            }
        }

        Random rand = new Random();

        for(int i = 0; i < 500; ++i){
            int temp = rand.nextInt(52);
            starterDeck.add(starterDeck.get(temp));
            starterDeck.remove(temp);
        }



        deck = new Deck(starterDeck);
        playPile = new PlayPile();
        wastePile = new WastePile();
    }



    public void deal() {
        wastePile.add_contents(playPile.get_contents());
        playPile.add_contents(deck.get_deal());
    }

}
