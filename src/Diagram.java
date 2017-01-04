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
    private Vector<Bike> allBikes;
    private PDFGraphics2D g;
    private Legend legend;
    private Analysis analysis;

    // appearance
    private final Color BAR_BACKGROUND_COLOR = Color.decode("#999999");
    private final int BAR_HEIGHT = 20; //height of each horizontal bar
    private final int MARKER_SIZE = BAR_HEIGHT - 5; //diameter of circle to mark a model version

    // fonts
    private final Font fontXAxis = new Font("Arial", Font.PLAIN, 20);
    private final Font fontXAxisEnds = new Font("Arial", Font.BOLD, 30);
    private final Font fontRowName = new Font("Arial", Font.PLAIN, 14);

    // page properties
    private int width, height, margin; //units are millimeters
    private int gridStep; //spacing between vertical grid steps. Units are dollars.

    // price range to display
    private int priceMin, priceMax;

    //    private NumberFormat numFmt = NumberFormat.getInstance();
    private NumberFormat numFmt = new DecimalFormat("$#,###");
    //endregion fields

    /**
     * Constructs a new Diagram.
     *
     * @param pageWidth  width of the page in millimeters
     * @param pageHeight height of the page in millimeters
     * @param pageMargin margin in millimeters
     * @param gridStep   distance between vertical grid lines in dollars
     */
    Diagram(Vector<Bike> bikes, int pageWidth, int pageHeight, int pageMargin, int gridStep) {
        width = pageWidth;
        height = pageHeight;
        margin = pageMargin;
        this.gridStep = gridStep;

        allBikes = bikes;

        g = new PDFGraphics2D(0.0, 0.0, width, height);

        priceMin = Bike.priceMinGlobal;
        priceMax = Bike.priceMaxGlobal;
    }


    // region not private
    // IntelliJ wants stuff to have package-private access instead of public

    /**
     * Override the price (x-axis) range.
     *
     * @param newMin new minimum amount in dollars
     * @param newMax new maximum amount in dollars
     */
    void addCustomRange(int newMin, int newMax) {
        priceMin = newMin;
        priceMax = newMax;
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
     * @throws IOException if cannot write to the file
     */
    void writePDF(String filename) throws IOException {
        drawGrid();
        drawBikes();

        if (legend != null) legend.draw(g);
        if (analysis != null) analysis.draw(g);

        try (FileOutputStream file = new FileOutputStream(filename)) {
            file.write(g.getBytes());
        }

    }

    // endregion not private

    // region privates

    /**
     * Draws the horizontal axis on the bottom, vertical grid lines, and labels on the left.
     */
    private void drawGrid() {
        int edgeBottom = height - margin;
        int edgeRight = width - margin;

        // x axis line
        g.setColor(Color.black);
        g.drawLine(margin, edgeBottom, edgeRight, edgeBottom);

        g.setColor(Color.decode("0x999999"));

        g.setFont(fontXAxis);
        FontMetrics metrics = g.getFontMetrics(fontXAxis);
        int textHeight = metrics.getHeight() - 10;

        // vertical lines and labels for vertical lines
        int xPos;
        for (int price = priceMin + gridStep; price <= priceMax - gridStep; price += gridStep) {
            xPos = priceToX(price);
            g.drawLine(xPos, 0, xPos, edgeBottom + 60);
            g.drawString(numFmt.format(price), xPos, edgeBottom + textHeight);
        }

        // min and max labels for x axis
        g.setFont(fontXAxisEnds);
        metrics = g.getFontMetrics(fontXAxisEnds);
        textHeight = g.getFontMetrics(fontXAxisEnds).getHeight();

        int marginNudge = 30;
        g.setColor(Color.black);

        g.drawString(numFmt.format(priceMin), margin - marginNudge, edgeBottom + textHeight / 2);

        String maxPrice = numFmt.format(priceMax);
        g.drawString(maxPrice, edgeRight - metrics.stringWidth(maxPrice) + marginNudge, edgeBottom + textHeight / 2);


    }

    /**
     * Draws all the Bike objects.
     */
    private void drawBikes() {
        Bike currentBike;
        int verticalSpacing = BAR_HEIGHT + 11; // spacing between each horizontal bar
        int barYPos, barWidth, barXStart, barXEnd;

        for (int i = 0; i < allBikes.size(); i++) {
            currentBike = allBikes.get(i);


            // draw the background stripe
            g.setColor(BAR_BACKGROUND_COLOR);

            barYPos = i * verticalSpacing + 20;
            barXStart = priceToX(currentBike.minPriceModel) - MARKER_SIZE / 2;
            barXEnd = priceToX(currentBike.maxPriceModel) - MARKER_SIZE / 2;
            barWidth = barXEnd - barXStart + MARKER_SIZE;

            final int RECT_RADIUS = 10;
            g.fillRoundRect(barXStart, barYPos, barWidth, BAR_HEIGHT, RECT_RADIUS, RECT_RADIUS);

            // draw model name on the left
            g.setColor(Color.black);
            g.setFont(fontRowName);
            g.drawString(currentBike.modelName, 10, barYPos + BAR_HEIGHT - 6);

            drawDots(currentBike, barYPos);
        }
    }


    /**
     * Draw the dots showing variants for a model.
     *
     * @param currentBike the bike object from the main loop
     * @param barYPos     vertical position of that bike's bar
     */
    private void drawDots(Bike currentBike, int barYPos) {
        int dotX, dotY;

        for (int i = 0; i < currentBike.numModels; i++) {
            dotX = priceToX(currentBike.prices.get(i)) - MARKER_SIZE / 2;
            dotY = barYPos + (BAR_HEIGHT / 2) - (MARKER_SIZE / 2);

            g.setColor(currentBike.groupsets.get(i).getColor());
            g.fill(currentBike.carbons.get(i).getShape(dotX, dotY, MARKER_SIZE));

            // draw price above the dot and model name below the dot
//            g.setColor(Color.black);
//            g.setFont(fontDotCaption);
//            g.drawString("$" + currentPrice, dotX, dotY - 3);
//            g.drawString(currentBike.versionNames.get(i), dotX, dotY); // for size 8

        }
    }

    /**
     * Given the price of a model version, returns the x position to draw it at.
     *
     * @param price price in dollars
     * @return x position for that price
     */
    private int priceToX(float price) {
        int priceRange = priceMax - priceMin;
        float percent = (price - priceMin) / priceRange;
        int usableWidth = width - margin * 2;
        return (int) (percent * usableWidth) + margin;
    }

    // endregion privates

}
