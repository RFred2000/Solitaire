package GameLogic;

import GameLogic.Components.*;

import java.util.*;

public class Game {

    private Deck deck;
    private PlayPile playPile;
    private WastePile wastePile;
    private Vector<Row> rows;
    private Foundation spadesFoundation;
    private Foundation clubsFoundation;
    private Foundation heartsFoundation;
    private Foundation diamondsFoundation;

    public Game() {
        Vector<Card> starterDeck = new Vector<Card>();

        Vector<String> suits = new Vector<String>();
        suits.add("diamonds");
        suits.add("hearts");
        suits.add("spades");
        suits.add("clubs");

        for (int i = 0; i < suits.size(); ++i) {
            for (int x = 0; x < 13; ++x) {
                Card tempCard = new Card();
                tempCard.suit = suits.get(i);
                tempCard.value = x;
                tempCard.flipped = false;
                starterDeck.add(tempCard);
            }
        }

        for(int i = 0; i < starterDeck.size(); ++i){
            String start;

            if
        }

        Random rand = new Random();

        for (int i = 0; i < 500; ++i) {
            int temp = rand.nextInt(52);
            starterDeck.add(starterDeck.get(temp));
            starterDeck.remove(temp);
        }

        Vector<Vector<Card>> rowContents = new Vector<Vector<Card>>();
        for (int i = 0; i < 7; ++i) {
            rowContents.add(new Vector<Card>());
        }

        for (int i = 0; i < rowContents.size(); ++i) {
            for (int x = 0; x < i + 1; ++x) {
                rowContents.get(i).add(starterDeck.firstElement());
                starterDeck.remove(0);
                if (x == 0) {
                    rowContents.get(i).firstElement().flipped = true;
                }
            }
        }

        rows = new Vector<Row>();
        for (int i = 0; i < 7; ++i) {
            rows.add(new Row(rowContents.get(i)));
        }

        deck = new Deck(starterDeck);
        playPile = new PlayPile();
        wastePile = new WastePile();

        spadesFoundation = new Foundation("spades");
        clubsFoundation = new Foundation("clubs");
        heartsFoundation = new Foundation("hearts");
        diamondsFoundation = new Foundation("diamonds");
    }

    public void deal() {

    }
}