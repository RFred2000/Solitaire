package Interface;

import GameLogic.Components.*;

import javax.swing.*;
import java.awt.*;
import java.util.*;

public class Board extends JPanel {

    Vector<Card> cards;

    public Board(Vector<Card> cards){
        this.cards = cards;
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(1500, 1000);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        for(int i = 0; i < cards.size(); ++i){
            Card temp = cards.get(i);
            g.drawImage(temp.image, temp.xLocation, temp.yLocation, temp.width, temp.height, this);
        }
    }
}
