import javax.swing.*;
import java.awt.*;

/**
 * Used to test Analysis and Histogram drawing before putting them in the PDF.
 */
public class AnalysisTesting extends JPanel {

    private static final int WIDTH = 1600;

    private static Analysis a;

    public static void main(String[] args) {
        a = new Analysis(4, 50, 150, 100);
        a.init(Bike.readBikes("new_test_input_corrected_supersix.txt"));

        // http://php.scripts.psu.edu/djh300/cmpsc221/notes-graphics-intro.php
        AnalysisTesting panel = new AnalysisTesting();
        JFrame application = new JFrame();
        application.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        application.add(panel);
        application.setSize(WIDTH, 600);
        application.setVisible(true);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        a.draw(g);
    }


}
