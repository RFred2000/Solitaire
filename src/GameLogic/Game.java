package GameLogic;

import GameLogic.Components.*;
import Interface.Board;
import Interface.Physics;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.util.*;

public class Game {

    private Deck deck;
    private PlayPile playPile;
    private WastePile wastePile;
    private Vector<Row> rows;
    private Vector<Foundation> foundations;

    private Board board;

    public Game(Board board, Physics physics) {

        // Creating bank of cards
        Vector<Card> starterDeck = new Vector<Card>();

        Vector<String> suits = new Vector<String>();
        suits.add("diamonds");
        suits.add("hearts");
        suits.add("spades");
        suits.add("clubs");

        for (int i = 0; i < suits.size(); ++i) {
            for (int x = 0; x < 13; ++x) {
                Card tempCard = new Card(suits.get(i), x+1);
                starterDeck.add(tempCard);
            }
        }

        // Assigning images to them
        for(int i = 0; i < starterDeck.size(); ++i){
            String start;

            switch(starterDeck.get(i).value){

                case 11:
                    start = "jack";
                    break;
                case 12:
                    start = "queen";
                    break;
                case 13:
                    start = "king";
                    break;
                case 1:
                    start = "ace";
                    break;
                default:
                    start = String.valueOf(starterDeck.get(i).value);
                    break;
            }

            String fileName = start + "_of_" + starterDeck.get(i).suit + ".png";

            try {
                starterDeck.get(i).image = ImageIO.read(new File("./Resources/CardPictures/" + fileName));
            }
            catch(Exception e) {
                e.printStackTrace();
                System.exit(-1);
            }
        }

        // Connecting components
        board.attachCards(new Vector<Card>(starterDeck));
        physics.attachCards(new Vector<Card>(starterDeck));
        board.addMouseListener(physics);
        board.addMouseMotionListener(physics);
        this.board = board;

        // Shuffling the cards
        Random rand = new Random();

        for (int i = 0; i < 500; ++i) {
            int temp = rand.nextInt(52);
            starterDeck.add(starterDeck.get(temp));
            starterDeck.remove(temp);
        }

        // Dealing the rows of solitaire
        Vector<Vector<Card>> rowContents = new Vector<Vector<Card>>();
        for (int i = 0; i < 7; ++i) {
            rowContents.add(new Vector<Card>());
        }

        for (int i = 0; i < rowContents.size(); ++i) {
            for (int x = 0; x < i + 1; ++x) {
                rowContents.get(i).add(starterDeck.firstElement());
                starterDeck.remove(0);
            }
        }

        rows = new Vector<Row>();
        for (int i = 0; i < 7; ++i) {
            rows.add(new Row(rowContents.get(i), Physics.ROW_LOCATIONS.get(i)));
        }

        deck = new Deck(starterDeck);

        playPile = new PlayPile();
        wastePile = new WastePile();

        // Making the foundations
        foundations = new Vector<Foundation>();
        for(int i = 0; i < 4; ++i){
            foundations.add(new Foundation(Physics.FOUNDATION_LOCATIONS.get(i)));
        }

        physics.attachDeck(deck);
        physics.attachPlayPile(playPile);
        physics.attachWastePile(wastePile);
        physics.attachRows(rows);
        physics.attachFoundations(foundations);

        board.attachDeck(deck);
        board.attachPlayPile(playPile);
        board.attachWastePile(wastePile);

    }

    public void tick(){
        board.repaint();
    }

    public boolean gameWon(){
        boolean temp = true;
        for(int i = 0; i < 4; ++i) {
            temp = temp && foundations.get(i).is_complete();
        }
        return temp;
    }
}