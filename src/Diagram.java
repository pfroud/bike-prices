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
    private Legend legend;
    private Analysis analysis;

    // appearance
//    private final Color BAR_BACKGROUND_COLOR = Color.decode("#999999");
    private final Color BAR_BACKGROUND_COLOR = Color.gray;
    private final int RECT_HEIGHT = 20; //height of each horizontal bar
    private final int MARKER_SIZE = RECT_HEIGHT - 5; //diameter of circle to mark a model version

    // fonts
    private final Font fontDotCaption = new Font("Arial", Font.PLAIN, 14);
    private final Font fontRowName = new Font("Arial", Font.BOLD, 14);

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


    /// PUBLIC FUNCTIONS

    /**
     * Override the price (x-axis) range.
     *
     * @param newMin custom minimum amount in dollars
     * @param newMax custom maximum amount in dollars
     */
    void addCustomRange(int newMin, int newMax) {
        priceMin = newMin;
        priceMax = newMax;

        priceRange = priceMax - priceMin;
    }

    /**
     * Adds a Legend to the diagram.
     *
     * @param l Legend object
     */
    void addLegend(Legend l) {
        legend = l;
        legend.setMarkerSize(MARKER_SIZE);
    }

    /**
     * Adds an Analysis object to the diagram.
     *
     * @param a Analysis object
     */
    void addAnalysis(Analysis a) {
        analysis = a;
        analysis.init(allBikes);
    }

    /**
     * Writes the diagram to a PDF file.
     *
     * @param filename name of the PDF file to write to
     * @throws IOException
     */
    void writePDF(String filename) throws IOException {
        drawBackground(g);

        drawGrid(g);
        drawBikes(g);

        if (legend != null) legend.draw(g);
        if (analysis != null) analysis.draw(g);

        try (FileOutputStream file = new FileOutputStream(filename)) {
            file.write(g.getBytes());
        }
    }

    private void drawBackground(Graphics g){
        g.setColor(Color.black);
        g.fillRect(0,0, width, height);
    }

    /**
     * Prints the range info for every bike.
     */
    void printRanges() {
        for (Bike b : allBikes) {
            b.printRange();
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
        g.setColor(Color.white);

        //          x1   ,  y1            , x2            , y2
        g.drawLine(margin, bottomEdge, rightEdge, bottomEdge); // bottom axis

        //setup for vertical lines
        Font smallFont = new Font("Arial", Font.PLAIN, 40);
        g.setFont(smallFont); //14pt size
        FontMetrics metrics = g.getFontMetrics(smallFont);
        int textHeght = metrics.getHeight()-10;
        g.setColor(Color.decode("0xbbbbbb"));
        int xPos;

        //draw vertical lines
        for (int price = priceMin+gridStep; price <= priceMax-gridStep; price += gridStep) {
            xPos = getXPosition(price);
            g.drawLine(xPos, 0, xPos, bottomEdge + 60);

            // Draw labels for vertical lines. Skip if at min and max because already drawn in bigger font.
            if (priceMin != price && priceMax != price) {
                g.drawString(numberFormat.format(price), xPos, bottomEdge + textHeght);
            }
        }


        // min and max labels for bottom axis
        g.setColor(Color.white);
        Font bigFont = new Font("Arial", Font.BOLD, 60);
        g.setFont(bigFont);
        metrics = g.getFontMetrics(bigFont);
        textHeght = metrics.getHeight();
        int marginNudge = 30;
        g.drawString(numberFormat.format(priceMin), margin-marginNudge, bottomEdge+textHeght/2);
        String maxPrice = numberFormat.format(priceMax);
        g.drawString(maxPrice, rightEdge - metrics.stringWidth(maxPrice)+marginNudge, bottomEdge + textHeght/2);

    }

    /**
     * Draws all the Bike objects.
     *
     * @param g graphics context
     */
    private void drawBikes(Graphics2D g) {
        Bike currentBike;
        int verticalSpacing = RECT_HEIGHT + 11; //spacing between each horizontal bar
        int barYPos, barWidth;
        float barXStart, barXEnd; // x positions of start and end of a bar

        // iterate over all the bike models
        for (int i = 0; i < allBikes.size(); i++) {
            currentBike = allBikes.get(i);

            barYPos = i * verticalSpacing + 20;


            // rectangle bar

            g.setColor(BAR_BACKGROUND_COLOR);
            barXStart = getXPosition(currentBike.minPrice);
            barYPos = i * verticalSpacing + 20;
            barXEnd = getXPosition(currentBike.maxPrice);
            barWidth = (int) (barXEnd - barXStart);
            int RECT_RADIUS = 10;
            g.fillRoundRect((int) barXStart, barYPos, barWidth + MARKER_SIZE, RECT_HEIGHT, RECT_RADIUS, RECT_RADIUS);


//            drawDots(g, currentBike, barYPos);


            // draw model name on the left
            g.setColor(Color.white);
            g.setFont(fontRowName);
            g.drawString(currentBike.modelName, 10, barYPos + RECT_HEIGHT - 6);

        }
    }


    /**
     * Draw the dots showing variants for a model.
     *
     * @param g           graphics context
     * @param currentBike the bike object from the main loop
     * @param barVertPos  vertical position of that bike's bar
     */
    private void drawDots(Graphics2D g, Bike currentBike, int barVertPos) {
        int currentPrice, dotX, dotY;


        for (int i = 0; i < currentBike.numModels; i++) {
            currentPrice = currentBike.versionPrices.get(i);

            // draw dot
            dotX = getXPosition(currentPrice);
            dotY = barVertPos + RECT_HEIGHT / 2 - MARKER_SIZE / 2;
            g.setColor(currentBike.versionGroupsets.get(i).getColor());
            g.fill(currentBike.versionCarbons.get(i).getShape(dotX, dotY, MARKER_SIZE));

            // draw price above the dot and model name below the dot

            g.setColor(Color.white);
            g.setFont(fontDotCaption);
            g.drawString("$" + currentPrice, dotX, dotY - 3);
            g.drawString(currentBike.versionNames.get(i), dotX, dotY + 30);

        }
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
