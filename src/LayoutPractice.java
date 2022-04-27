import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

/**
 * @author Herth S
 * Date: 4/19/22
 * Time: 3:05 PM
 */
public class LayoutPractice extends JFrame {

    int clicks = 0;
    final int rightSideWidth = 150;
    JPanel panel = null;
    JPanel target = null;
    JPanel target1 = null;
    JPanel target2 = null;
    JPanel target3 = null;
    JLabel clicksLabel;

    private void go() {

        target1 = new TargetPanel(TargetPanel.TARGET,     660, 600, 150, 285, 285);
        target2 = new TargetPanel(TargetPanel.RECTANGLE,  660, 600, 150, 100, 125);
        target3 = new TargetPanel(TargetPanel.ROUND_RECT, 660, 600, 150, 100, 125);
        target = target1;

        panel = new JPanel(new BorderLayout());
        panel.add(BorderLayout.CENTER, target);

        JLabel spacerLabel = new JLabel(" ");
        spacerLabel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        JPanel spacer = new JPanel();
        spacer.add(spacerLabel);

        JPanel rightSide = new JPanel();
        rightSide.setLayout(new GridLayout(0,1));
        rightSide.setMaximumSize(new Dimension(rightSideWidth, 660));
        rightSide.setAlignmentX(Component.LEFT_ALIGNMENT);
        rightSide.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        clicksLabel = new JLabel(" ");
        clicksLabel.setPreferredSize(new Dimension(rightSideWidth, 150));
        clicksLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        clicksLabel.setBorder(BorderFactory.createEmptyBorder(20, 20, 50, 20));
        rightSide.add(clicksLabel);

        rightSide.add(spacer);
        rightSide.add(spacer);
        rightSide.add(spacer);
        rightSide.add(spacer);

        JPanel clickMePanel = new JPanel();
        JButton clickMe = new JButton("Click me for a dialog");
        clickMe.addActionListener(e -> clickButtonClicked());
        clickMe.setPreferredSize(new Dimension(rightSideWidth, 50));
        clickMe.setBorderPainted(true);
        clickMePanel.add(clickMe);
        clickMePanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 50, 10));
        rightSide.add(clickMePanel);

        rightSide.add(spacer);

        JPanel buttonPanel = new JPanel(new GridLayout(0, 1));
        buttonPanel.setPreferredSize(new Dimension(rightSideWidth, 300));
        buttonPanel.setBorder(BorderFactory.createTitledBorder(new LineBorder(Color.BLACK, 2, false), "Select Shape"));

        JRadioButton button1 = new JRadioButton("circle");
        button1.addActionListener(e -> replaceTargetPanel(target1));
        button1.setSelected(true);
        buttonPanel.add(button1);

        JRadioButton button2 = new JRadioButton("rectangle");
        button2.addActionListener(e -> replaceTargetPanel(target2));
        buttonPanel.add(button2);

        JRadioButton button3 = new JRadioButton("round rectangle");
        button3.addActionListener(e -> replaceTargetPanel(target3));
        buttonPanel.add(button3);

        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(button1);
        buttonGroup.add(button2);
        buttonGroup.add(button3);

        rightSide.add(buttonPanel);

        panel.add(BorderLayout.EAST, rightSide);

        add(panel);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(800, 660);
        setVisible(true);

    }

    private void clickButtonClicked() {
        clicks++;
        clicksLabel.setText(String.format("Button clicks = %d", clicks));
        repaint();
    }

    private void replaceTargetPanel(JPanel source) {
        Container parent = target.getParent();
        int index = parent.getComponentZOrder(target);
        parent.remove(target);
        target = source;
        parent.add(target, index);
        parent.validate();
        parent.repaint();
    }

    public static void main( String[] args ) {
        new LayoutPractice().go();
    }
}
