import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.File;
import java.util.*;

import Interface.Board;
import GameLogic.Components.Card;
import Interface.Physics;

public class Main {

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        Card testerCard = new Card();
        testerCard.xLocation = 20;
        testerCard.yLocation = 20;
        testerCard.depth = 0;
        testerCard.width = 200;
        testerCard.height = 300;
        testerCard.flipped = true;
        testerCard.suit = "spades";
        testerCard.value = 1;

        try {
            testerCard.image = ImageIO.read(new File("C:\\Users\\Richie Frederick\\IdeaProjects\\Solitaire\\Resources\\Resources.CardPictures\\ace_of_spades.png"));
        }
        catch (Exception e){
            e.printStackTrace();
        }

        Vector<Card> cards = new Vector<Card>();

        cards.add(testerCard);

        Board board = new Board(cards);

        Physics physics = new Physics(cards);
        board.addMouseMotionListener(physics);
        board.addMouseListener(physics);

        frame.setContentPane(board);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        while(true){
            board.repaint();
        }
    }

    /*
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        Game game = new Game();
        GameBoard board = new GameBoard();
        board.setDeck(game.getDeck());
        board.setPlayPile(game.getPlayPile());
        board.setRows(game.getRows());

        Vector<Foundation> foundations = new Vector<Foundation>();
        foundations.add(game.getClubsFoundation());
        foundations.add(game.getSpadesFoundation());
        foundations.add(game.getDiamondsFoundation());
        foundations.add(game.getHeartsFoundation());

        board.setFoundations(foundations);

        frame.setContentPane(board);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        Scanner scanner = new Scanner(System.in);

        while(true){

            System.out.print("Deal or Move? : ");
            String option = scanner.nextLine();

            if(option.equals("Deal") || option.equals("deal")){
                game.deal();
            }
            else {
                System.out.print("Multi Card or Single Card? : ");
                option = scanner.nextLine();

                if (option.equals("Multi Card")){

                    System.out.print("Start Row? : ");
                    int startRow = scanner.nextInt();

                    System.out.print("End Row? : ");
                    int endRow = scanner.nextInt();

                    System.out.print("Number of Cards? : ");
                    int cardsUp = scanner.nextInt();

                    game.moveCards(startRow, cardsUp, endRow);
                }
                else {

                    System.out.print("Start Location: ");
                    String startLocation = scanner.nextLine();

                    System.out.print("End Location: ");
                    String endLocation = scanner.nextLine();

                    game.moveCard(startLocation, endLocation);

                }
            }

            board.setDeck(game.getDeck());
            board.setPlayPile(game.getPlayPile());
            board.setRows(game.getRows());
            foundations = new Vector<Foundation>();
            foundations.add(game.getClubsFoundation());
            foundations.add(game.getSpadesFoundation());
            foundations.add(game.getDiamondsFoundation());
            foundations.add(game.getHeartsFoundation());
            board.setFoundations(foundations);
            board.repaint();

        }
    }
    */

}
