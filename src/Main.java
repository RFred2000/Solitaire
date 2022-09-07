import javax.swing.*;

import Interface.Board;
import GameLogic.Game;
import Interface.Physics;

public class Main {

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        Board board = new Board();
        Physics physics = new Physics();
        Game game = new Game(board, physics);

        frame.setContentPane(board);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        while(true){
            if(game.gameWon()){
                System.exit(0);
            }
            game.tick();
        }
    }
}
