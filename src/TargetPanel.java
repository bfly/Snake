import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;

import static java.awt.Color.RED;
import static java.awt.Color.YELLOW;

/**
 * @author Herth S
 * Date: 4/19/22
 * Time: 3:09 PM
 */
public class TargetPanel extends JPanel {
    public static final int TARGET = 1;
    public static final int RECTANGLE = 2;
    public static final int ROUND_RECT = 3;

    int shape, height, width, radius, x, y;

    public TargetPanel( int shape, int height, int width, int radius, int x, int y ) {
        this.shape = shape;
        this.height = height;
        this.width = width;
        this.radius = radius;
        this.x = x;
        this.y = y;
    }

    public void paintComponent( Graphics g ) {
        Graphics2D g2 = ( Graphics2D ) g;
        draw(g2);
    }

    protected void draw( Graphics2D g ) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, width, height);

        switch (shape) {
            case TARGET -> target(g);
            case RECTANGLE -> rect(g);
            case ROUND_RECT -> roundRect(g);
        }
    }

    void target( Graphics2D g ) {
        Ellipse2D.Double circle = null;

        for ( int i = 0; i < 10; i++ ) {
            int radius = width / 2 - 25 * (i + 2);
            double left = x - radius;
            double top = y - radius;
            circle = new Ellipse2D.Double(left, top, 2 * radius, 2 * radius);
            g.setColor(i % 2 == 0 ? RED : YELLOW);
            g.fill(circle);
        }
    }

    void rect( Graphics2D g ) {
        Rectangle2D.Double rect = new Rectangle2D.Double(x, y, width * 2.0 / 3.0, height / 2.0);
        g.setColor(Color.CYAN);
        g.fill(rect);
    }

    void roundRect( Graphics2D g ) {
        RoundRectangle2D.Double round = new RoundRectangle2D.Double(x, y,
            width * 2.0 / 3.0, height / 2.0, 35.0, 35.0);
        g.setColor(Color.MAGENTA);
        g.fill(round);
    }
}


