import javax.swing.*;
import java.awt.*;
import java.util.Vector;

public class HistogramTesting extends JApplet {

    Histogram hist;

    public void init() {
        setSize(800, 800);

        Vector<Integer> data = new Vector<>();
        data.add(2);
        data.add(5);
        data.add(6);
        data.add(1);
        hist = new Histogram(new Bike("test_model"), data);
    }

    public void paint(Graphics g) {
        hist.draw(g, 0, 0, 0, 0);
    }

}
