import javax.swing.*;
import java.awt.*;

/**
 * Used this to test the Analysis and Histogram classes.
 * It only draws histograms, no diagram.
 */
public class AnalysisTesting extends JPanel {

    private static Analysis a;

    public static void main(String[] args) {
        a = new Analysis(4, 50, 150, 50);
        a.init(Bike.readBikes("bikesInput.txt"));

        // http://php.scripts.psu.edu/djh300/cmpsc221/notes-graphics-intro.php since I never remember this stuff
        AnalysisTesting panel = new AnalysisTesting();
        JFrame application = new JFrame();
        application.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        application.add(panel);
        application.setSize(1600, 1900);
        application.setVisible(true);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        a.draw(g);
    }


}
