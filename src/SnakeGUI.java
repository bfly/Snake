import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

import static java.awt.BorderLayout.NORTH;
import static java.awt.BorderLayout.SOUTH;

public class SnakeGUI extends JFrame {
    private final GameManager game;
    JPanel gameDisplay;

    public SnakeGUI(GameManager game) {
        this.game = game;

        JPanel panel = new JPanel(new BorderLayout());

        JLabel hdr = new JLabel("SNAKE");

        Font font = new Font(Font.SERIF, Font.BOLD, 36);
        hdr.setFont(font );
        panel.add(NORTH, hdr);

        formatMaze();
        panel.add(SOUTH, gameDisplay);

        add(panel);
        this.setMinimumSize(new Dimension(game.getMaze()[0].length*5, game.getMaze().length*5));
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void formatMaze() {
        Font font = new Font(Font.SERIF, Font.BOLD, 24);
        Item[][] maze = game.getMaze();
        JLabel gmaze = new JLabel();
        gmaze.setFont(font);
        gmaze.setText(Arrays.deepToString(maze));
        gameDisplay = new JPanel();
        gameDisplay.setMinimumSize(new Dimension(800, 600));
        gameDisplay.add(gmaze);
//        gameDisplay = new JPanel(new GridLayout(maze[0].length, maze.length));
//        for (Item[] row : maze) {
//            for (Item cell : row) {
//                JLabel label = new JLabel();
//                label.setFont(font );
//                label.setText(String.valueOf(cell.getSymbol()));
//                gameDisplay.add(label);
//            }
//        }
    }

        public void replaceGamePanel() {
            Container parent = gameDisplay.getParent();
            int index = parent.getComponentZOrder(gameDisplay);
            parent.remove(gameDisplay);
            formatMaze();
            parent.add(gameDisplay, index);
            parent.validate();
            repaint();
    }


}
