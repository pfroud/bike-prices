import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;
import java.util.Vector;

public class HistogramTesting extends JApplet implements KeyListener {

    Histogram hist;
    Random rand = new Random();

    public void init() {
//        setSize(800, 800);
        setFocusable(true);
        addKeyListener(this);

        Vector<Integer> data = new Vector<>();
        for (int i = 0; i < 5; i++) {
            data.add(randRange(1, 10));
        }

        hist = new Histogram(data, "hello", 100, 250, 200, 200);
    }

    // http://stackoverflow.com/a/363691
    private int randRange(int min, int max){
        return min + rand.nextInt(max - min + 1);
    }

    public void paint(Graphics g) {
        hist.draw(g);
    }

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) System.exit(0);
    }

    public void keyTyped(KeyEvent e) {
    }

    public void keyReleased(KeyEvent e) {
    }
}
