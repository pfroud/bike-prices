import javax.swing.*;
import java.awt.*;

public class HistogramTesting extends JPanel {

    private static Analysis a;

    public static void main(String[] args) {
        // disgusting jframe shit
        HistogramTesting panel = new HistogramTesting();
        JFrame application = new JFrame();
        application.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        application.add(panel);
        application.setSize(800, 800);
        application.setVisible(true);


        a = new Analysis(4);
        a.init(Bike.readBikes("bikesInput.txt"));
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.fillRect(10, 10, 200, 300);


        a.draw(g);
    }


}
