import java.awt.*;
import java.util.Vector;

public class Histogram {

    private static int numBins;

    private Bike bike;
    private Vector<Integer> data;

    public Histogram(Bike bike, Vector<Integer> data){
        this.bike = bike;
        this.data = data;
    }

    public void draw(Graphics g, int x, int y, int width, int height){
        bike = null; //eliminate warning
        data = null;
    }



}
