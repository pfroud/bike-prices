import de.erichseifert.vectorgraphics2d.PDFGraphics2D;

import java.awt.*;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Vector;

/**
 * A diagram of bars, dots, and a grid, showing price distribution.
 */
class Diagram {

    // region fields
    // core
    private Vector<Bike> allBikes; // holds every bike model
    private PDFGraphics2D g; //graphics context

    // appearance
    private final Color BAR_BACKGROUND_COLOR = Color.decode("#999999");
    private final int RECT_HEIGHT = 20; //height of each horizontal bar
    private final int MARKER_SIZE = RECT_HEIGHT - 5; //diameter of circle to mark a model version

    // fonts
    private final Font fontDotCaption = new Font("Arial", Font.PLAIN, 12);
    private final Font fontRowName = new Font("Arial", Font.PLAIN, 14);

    // page properties
    private int width, height, margin; //size and margins of the pdf page. Units are millimeters.
    private int gridStep; //spacing between vertical grid steps. Units are dollars.

    // price range
    private int priceMin = 999999; //price of the least expensive bike in the input file
    private int priceMax = 0; //price of the most expensive bike in the input file
    private float priceRange; //difference between least and most expensive bike in input file

    //    private NumberFormat numberFormat = NumberFormat.getInstance();
    private NumberFormat numberFormat = new DecimalFormat("$#,###");
    //endregion fields

    /**
     * Constructs a new Diagram instance.
     *
     * @param pageWidth  width of the page in millimeters
     * @param pageHeight height of the page in millimeters
     * @param pageMargin margin in millimeters
     * @param gridStep   distance between vertical grid lines in dollars
     */
    Diagram(Vector<Bike> allBikes, int pageWidth, int pageHeight, int pageMargin, int gridStep) {
        this.width = pageWidth;
        this.height = pageHeight;
        this.margin = pageMargin;
        this.gridStep = gridStep;

        this.allBikes = allBikes;

        g = new PDFGraphics2D(0.0, 0.0, this.width, this.height);

        priceMin = Bike.getPriceMax();
        priceMax = Bike.getPriceMin();

        priceRange = priceMax - priceMin;
    }


    /**
     * Writes the diagram to a PDF file.
     *
     * @param filename name of the PDF file to write to
     * @throws IOException if cannot write to the file
     */
    void writePDF(String filename) throws IOException {
//        draw_margin(g);

        drawGrid(g);
        draw_price_distrib();

        try (FileOutputStream file = new FileOutputStream(filename)) {
            byte[] bytes = g.getBytes();
            file.write(bytes);
        }
    }

    private void draw_margin(Graphics g) {
        g.setColor(Color.red);
        g.drawRect(margin, margin, width - margin - margin, height - margin - margin);
    }

    private void draw_price_distrib() {
        Vector<Integer> priceData = new Vector<>();

        for (Bike b : allBikes) priceData.addAll(b.versionPrices);

        priceData.sort(null);

        final int DOT_SIZE = 3;
        g.setColor(new Color(0, 0, 0, 255 / 4));

        int xPos;
        for (int price : priceData) {
            xPos = getXPosition(price);
//            g.fillOval(xPos - DOT_SIZE / 2, 50, DOT_SIZE, DOT_SIZE);
            g.drawLine(xPos, 60, xPos, 70);
        }

    }

    /**
     * Draws the horizontal axis on the bottom, vertical grid lines, and labels on the left.
     *
     * @param g graphics context
     */
    private void drawGrid(Graphics g) {

        int bottomEdge = height - margin;
        int rightEdge = width - margin;
        g.setColor(Color.black);




        //setup for vertical lines
        Font smallFont = new Font("Arial", Font.PLAIN, 8);
        g.setFont(smallFont); //14pt size
        FontMetrics metrics = g.getFontMetrics(smallFont);
        int textHeght = metrics.getHeight() - 10;
        g.setColor(Color.decode("0x999999"));
        int xPos;


        //draw intermediate price marks
        for (int price = priceMin + gridStep; price <= priceMax - gridStep; price += gridStep) {
            xPos = getXPosition(price);
            g.drawLine(xPos, bottomEdge - 2, xPos, bottomEdge + 2);

            // Draw labels for vertical lines. Skip if at min and max because already drawn in bigger font.
            if (priceMin != price && priceMax != price) {
                g.drawString("$" + price / 1000 + "k", xPos-7, bottomEdge + 10);
            }
        }

        //          x1   ,  y1            , x2            , y2
        g.drawLine(margin, bottomEdge, rightEdge, bottomEdge); // bottom axis


        // min and max labels for bottom axis
        g.setColor(Color.black);
        Font bigFont = new Font("Arial", Font.BOLD, 10);
        g.setFont(bigFont);
        metrics = g.getFontMetrics(bigFont);
        textHeght = metrics.getHeight();
        g.drawString(numberFormat.format(priceMin), margin - 4, bottomEdge + textHeght);
        String maxPrice = "$" + priceMax / 1000 + "k";
        g.drawString(maxPrice, rightEdge - metrics.stringWidth(maxPrice) + 12, bottomEdge + textHeght);


    }


    /**
     * Given the price of a model version, returns the x position to draw it at.
     *
     * @param price price in dollars
     * @return x position for that price
     */
    private int getXPosition(int price) {
        int endWidth = width - margin * 2; //x location where everything should end
        return (int) ((((float) price - priceMin) / priceRange) * endWidth + margin);
    }

}
