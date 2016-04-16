import de.erichseifert.vectorgraphics2d.PDFGraphics2D;

import java.awt.*;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Vector;

/**
 * A diagram of bars, dots, and a grid, showing cost distribution.
 */
class Diagram {

    // region fields
    // core
    private Vector<Bike> allBikes; // holds every bike model
    private PDFGraphics2D g; //graphics context
    private Legend legend;
    private Analysis analysis;

    // appearance
    private final Color BAR_BACKGROUND_COLOR = Color.decode("#999999");
    private final int RECT_HEIGHT = 20; //height of each horizontal bar
    private final int MARKER_SIZE = RECT_HEIGHT - 5; //diameter of circle to mark a model version

    // fonts
    private final Font fontDotCaption = new Font("Arial", Font.PLAIN, 14);
    private final Font fontRowName = new Font("Arial", Font.BOLD, 14);

    // page properties
    private int width, height, margin; //size and margins of the pdf page. Units are millimeters.
    private int gridStep; //spacing between vertical grid steps. Units are dollars.

    // cost range
    private int costMin = 999999; //cost of the least expensive bike in the input file
    private int costMax = 0; //cost of the most expensive bike in the input file
    private float costRange; //difference between least and most expensive bike in input file
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

        costMin = Bike.getCostMin();
        costMax = Bike.getCostMax();

        costRange = costMax - costMin;
    }

    /// PUBLIC FUNCTIONS

    /**
     * Override the cost (x-axis) range.
     *
     * @param newMin custom minimum amount in dollars
     * @param newMax custom maximum amount in dollars
     */
    void addCustomRange(int newMin, int newMax) {
        costMin = newMin;
        costMax = newMax;

        costRange = costMax - costMin;
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
        drawGrid(g);
        drawBikes(g);

        if (legend != null) legend.draw(g);
        if (analysis != null) analysis.draw(g);

        try (FileOutputStream file = new FileOutputStream(filename)) {
            file.write(g.getBytes());
        }
    }


    /**
     * Draws the horizontal axis on the bottom, vertical grid lines, and labels on the left.
     *
     * @param g graphics context
     */
    private void drawGrid(Graphics g) {

        int bottomEdge = height - margin - 100;
        int rightEdge = width - margin;

        //          x1   ,  y1            , x2            , y2
        g.drawLine(margin, bottomEdge, rightEdge, bottomEdge); // bottom axis

        // min and max labels for bottom axis
        g.setFont(new Font("Arial", Font.BOLD, 22)); //22pt size
        g.drawString("$" + costMin, margin, bottomEdge + 20);
        g.drawString("$" + costMax, rightEdge - 60, bottomEdge + 20);

        //setup for vertical lines
        g.setFont(new Font("Arial", Font.PLAIN, 14)); //14pt size
        g.setColor(Color.decode("0xbbbbbb"));
        int xPos;

        //draw vertical lines
        for (int cost = costMin; cost <= costMax; cost += gridStep) {
            xPos = getXPosition(cost);
            g.drawLine(xPos, 0, xPos, bottomEdge + 60);

            // Draw labels for vertical lines. Skip if at min and max because already drawn in bigger font.
            if (costMin != cost && costMax != cost) {
                g.drawString("$" + cost, xPos, bottomEdge + 15);
            }
        }
    }

    /**
     * Draws all the Bike objects.
     *
     * @param g graphics context
     */
    private void drawBikes(Graphics g) {
        Bike currentBike;
        int verticalSpacing = RECT_HEIGHT + 30; //spacing between each horizontal bar
        int barYPos, barWidth;
        float barXStart, barXEnd; // x positions of start and end of a bar

        // iterate over all the bike models
        for (int i = 0; i < allBikes.size(); i++) {
            currentBike = allBikes.get(i);

            // rectangle bar
            g.setColor(BAR_BACKGROUND_COLOR);
            barYPos = i * verticalSpacing + 20;
            barXStart = getXPosition(currentBike.minCost);
            barXEnd = getXPosition(currentBike.maxCost);
            barWidth = (int) (barXEnd - barXStart);
            int RECT_RADIUS = 10;
            g.fillRoundRect((int) barXStart, barYPos, barWidth + MARKER_SIZE, RECT_HEIGHT, RECT_RADIUS, RECT_RADIUS);

            drawDots(g, currentBike, barYPos);

            // draw model name on the left
            g.setColor(Color.black);
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
    private void drawDots(Graphics g, Bike currentBike, int barVertPos) {
        int currentCost, dotX, dotY;

        for (int i = 0; i < currentBike.numModels; i++) {
            currentCost = currentBike.versionCosts.get(i);

            // draw dot
            dotX = getXPosition(currentCost);
            dotY = barVertPos + RECT_HEIGHT / 2 - MARKER_SIZE / 2;
            g.fillOval(dotX, dotY, MARKER_SIZE, MARKER_SIZE);
            currentBike.versionCarbons.get(i).draw(g, dotX, dotY, MARKER_SIZE, false);

            // draw cost above the dot and model name below the dot
            g.setColor(Color.black);
            g.setFont(fontDotCaption);
            g.drawString("$" + currentCost, dotX, dotY - 3);
            g.drawString(currentBike.versionNames.get(i), dotX, dotY + 30);
        }
    }

    /**
     * Given the cost of a model version, returns the x position to draw it at.
     *
     * @param cost cost in dollars
     * @return x position for that cost
     */
    private int getXPosition(int cost) {
        int endWidth = width - margin * 2; //x location where everything should end
        return (int) ((((float) cost - costMin) / costRange) * endWidth + margin);
    }

}
