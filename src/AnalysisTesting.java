import javax.swing.*;
import java.awt.*;

/**
 * Used this to test the Analysis and Histogram classes.
 * It only draws histograms, no diagram.
 */
public class AnalysisTesting extends JPanel {

    private static final int WIDTH = 1600;

    private static Analysis a;

    public static void main(String[] args) {
        a = new Analysis(4, 50, 150, 100);
        a.init(Bike.readBikes("new_test_input.txt"));

        // http://php.scripts.psu.edu/djh300/cmpsc221/notes-graphics-intro.php since I never remember this stuff
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
