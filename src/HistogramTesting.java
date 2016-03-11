import javax.swing.*;
import java.awt.*;

public class HistogramTesting extends JPanel {

    private static Analysis a;

    public static void main(String[] args) {

        a = new Analysis(4);
        a.init(Bike.readBikes("bikesInput.txt"));

        // disgusting jframe shit - http://php.scripts.psu.edu/djh300/cmpsc221/notes-graphics-intro.php
        HistogramTesting panel = new HistogramTesting();
        JFrame application = new JFrame();
        application.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        application.add(panel);
        application.setSize(1800, 500);
        application.setVisible(true);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        a.draw(g);
    }


}
