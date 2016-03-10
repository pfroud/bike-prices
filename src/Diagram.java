import de.erichseifert.vectorgraphics2d.PDFGraphics2D;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;
import java.util.Vector;


public class Diagram {

    // appearance
    final Color BAR_BACKGROUND_COLOR = Color.gray;
    final int RECT_HEIGHT = 20; //height of each horizontal bar
    final int MARKER_SIZE = RECT_HEIGHT - 5; //diameter of circle to mark a model version
    final int RECT_RADIUS = 0;

    // page properties
    private int width, height, margin; //size and margins of the pdf page. units are millimeters
    private int gridStep; //spacing between vertical grid steps. units are dollars.

    // cost range information
    private int costMin = 999999; //cost of the least expensive bike in the input file
    private int costMax = 0; //cost of the most expensive bike in the input file
    private float costRange; //difference between least and most expensive bike in input file
    private boolean doCostRangeOverride = false; //whether or not to override the cost range
    private int costMin_override, costMax_override; //custom cost range start and end

    private Vector<Bike> allBikes = new Vector<>(); //holds every bike model

    private PDFGraphics2D g; //graphics context

    private Color[] colors; //possibly not used

    /**
     * Constructs a new Diagram instance.
     *
     * @param pageWidth width of the page in millimeters
     * @param pageHeight height of the page in millimeters
     * @param pageMargin margin in millimeters
     * @param gridStep distance between vertical grid lines in dollars
     */
    public Diagram(int pageWidth, int pageHeight, int pageMargin, int gridStep) {
        this.width = pageWidth;
        this.height = pageHeight;
        this.margin = pageMargin;
        this.gridStep = gridStep;

        g = new PDFGraphics2D(0.0, 0.0, this.width, this.height);
    }

    /**
     * Override the cost (x-axis) range.
     *
     * @param newMin custom minimum amount in dollars
     * @param newMax custom maximum amount in dollars
     */
    public void setRangeOverride(int newMin, int newMax) {
        doCostRangeOverride = true;
        costMin_override = newMin;
        costMax_override = newMax;
    }

    /**
     * Reads bikes from a file and draws diagram.
     *
     * @param filename name of the text file with bike info.
     */
    public void loadBikes(String filename) {
        readAllBikes(filename);

        int numHistogramBins = 3; //will go in Analysis.java eventually

        drawGrid(g);
        drawBikes(g, numHistogramBins);
    }

    /**
     * Writes the diagram to a PDF file.
     *
     * @param filename name of the PDF file to write to
     * @throws IOException
     */
    public void writeFile(String filename) throws IOException {
        try (FileOutputStream file = new FileOutputStream(filename)) {
            file.write(g.getBytes());
        }
    }

    /**
     * Opens the input file and reads all bikes in the file.
     * Called by main().
     *
     * @param filename name of text file with bike info
     */
    private void readAllBikes(String filename) {

        Scanner fileScan = null;//Scanner to read input text file

        //open the file
        try {
            fileScan = new Scanner(new File(filename));
        } catch (FileNotFoundException e) {
            System.err.println("File " + filename + " not found.");
            System.exit(1);
        }

        //go through input text file
        while (fileScan.hasNextLine()) {
            allBikes.add(readOneBike(fileScan));

            fileScan.nextLine(); //skip a blank line

            //this works but is terrible
            if (fileScan.hasNextLine()) {
                fileScan.nextLine();
            } else {
                break;
            }
        }

        //optionally set a custom min and max cost to display
        if (doCostRangeOverride) {
            costMax = costMax_override;
            costMin = costMin_override;
        }

        //set the cost range, used in getXPosition()
        costRange = costMax - costMin;
    }


    //@formatter:off (formatter kills indentation in this doc comment)
    /**
     * Reads a bike model and its versions, then returns a Bike object.
     * Called by readAllBikes().
     * Steps:
     *
     * (1) Reads the header, which contains the model name and number of versions.
     *     Example: "Specialized_Diverge 7"
     *
     * (2) Does three for loops to read:
     *     (a) version names
     *     (b) version costs
     *     (c) version materials
     *
     * @param fileScan scanner on the input text file
     * @return Bike object containing all of the version names, costs, and materials
     */
    //@formatter:on
    private Bike readOneBike(Scanner fileScan) {

        Scanner headerScan = new Scanner(fileScan.nextLine());
        Bike bike = new Bike(headerScan.next()); //read model name
        int numModels = headerScan.nextInt();
        headerScan.close();

        // add version names
        for (int i = 0; i < numModels; i++) {
            bike.versionNames.add(fileScan.nextLine());
        }

        // add version versionCosts
        int currentCost;
        for (int i = 0; i < numModels; i++) {
            currentCost = fileScan.nextInt();

            //update global min and max cost
            if (currentCost > costMax) {
                costMax = currentCost;
            }
            if (currentCost < costMin) {
                costMin = currentCost;
            }

            bike.addCost(currentCost);
        }

        // add version materials
        String currentCarbon;
        for (int i = 0; i < numModels; i++) {
            currentCarbon = fileScan.next();

            switch (currentCarbon) {
                case "all":
                    bike.versionCarbons.add(Carbon.ALL);
                    break;
                case "fork":
                    bike.versionCarbons.add(Carbon.FORK);
                    break;
                case "none":
                    bike.versionCarbons.add(Carbon.NONE);
                    break;
                default:
                    System.err.println("unrecognized carbon value \"" + currentCarbon + "\"");
            }
        }

        return bike;
    }


    /**
     * Draws the horizontal axis on the bottom, vertical grid lines, and labels on the left.
     * Called by main().
     *
     * @param g graphics context
     */
    private void drawGrid(Graphics2D g) {
        final Color GRID_VERTICAL_COLOR = Color.decode("0xbbbbbb");

        //          x1   ,  y1            , x2            , y2
        g.drawLine(margin, height - margin, width - margin, height - margin); // bottom axis

        // min and max labels for bottom axis
        g.setFont(new Font("Arial", Font.BOLD, 22)); //22pt size
        g.drawString("$" + costMin, margin, height - margin + 20);
        g.drawString("$" + costMax, width - margin - 60, height - margin + 20);

        //setup for vertical lines
        g.setFont(new Font("Arial", Font.PLAIN, 14)); //14pt size
        g.setColor(GRID_VERTICAL_COLOR);
        int xPos;

        //draw vertical lines
        for (int cost = costMin; cost <= costMax; cost += gridStep) {
            xPos = getXPosition(cost);
            g.drawLine(xPos, 0, xPos, height - margin + 60);

            // Draw labels for vertical lines. Skip if at min and max because already drawn in bigger font.
            if (costMin != cost && costMax != cost) {
                g.drawString("$" + cost, xPos, height - margin + 15);
            }

        }

    }

    /**
     * Draws the PDF.
     * Called by main().
     *
     * @param g                graphics context
     * @param numHistogramBins how many histogram bins - might remove
     */
    private void drawBikes(Graphics2D g, int numHistogramBins) {
        Bike currentBike;
        int verticalSpacing = RECT_HEIGHT + 30; //spacing between each horizontal bar
        int barYPos, barWidth;
        float barXStart, barXEnd; // x positions of start and end of a bar
        Font fontModelName = new Font("Arial", Font.BOLD, 14);
        initColors(numHistogramBins);

        // iterate over all the bike models
        for (int i = 0; i < allBikes.size(); i++) {
            currentBike = allBikes.get(i);

            // colored rectangle bar
            g.setColor(BAR_BACKGROUND_COLOR);
            barYPos = i * verticalSpacing + 20;
            barXStart = getXPosition(currentBike.minCost);
            barXEnd = getXPosition(currentBike.maxCost);
            barWidth = (int) ((barXEnd - barXStart) / numHistogramBins);

            int xStartHist = (int) barXStart;
            int extraWidth = 0;
            for (int j = 0; j < numHistogramBins; j++) {
                g.setColor(colors[j]);
                if (j == numHistogramBins - 1) extraWidth = MARKER_SIZE;
                g.fillRoundRect(xStartHist, barYPos, barWidth + extraWidth, RECT_HEIGHT, RECT_RADIUS, RECT_RADIUS);
                xStartHist += barWidth;
            }

            drawAllDots(g, currentBike, barYPos);

            // draw model name on the left
            g.setColor(Color.black);
            g.setFont(fontModelName);
            g.drawString(currentBike.modelName, 10, barYPos + RECT_HEIGHT - 6);

            //reset font - might not be needed - should not call new every time
            g.setFont(new Font("Arial", Font.PLAIN, 14));
        }
    }

    /**
     * @param numHistogramBins how many histogram bins - might remove
     */
    private void initColors(int numHistogramBins) {
        colors = new Color[numHistogramBins];
        float n = 0.4f;
        colors[0] = new Color(n, n, n);
        for (int i = 1; i < numHistogramBins; i++) {
            colors[i] = colors[i - 1].brighter();
        }
    }


    /**
     * Draw the dots showing variants for a model.
     *
     * @param g           graphics context
     * @param currentBike the bike object from the main loop
     * @param barVertPos  vertical position of that bike's bar
     */
    private void drawAllDots(Graphics2D g, Bike currentBike, int barVertPos) {
        int currentCost, dotX, dotY;
        for (int j = 0; j < currentBike.versionCosts.size(); j++) {
            currentCost = currentBike.versionCosts.get(j);

            // get position for the cost dot
            dotX = getXPosition(currentCost);
            dotY = barVertPos + RECT_HEIGHT / 2 - MARKER_SIZE / 2;

            switch (currentBike.versionCarbons.get(j)) {
                case ALL:
                    g.setColor(Color.cyan);
                    break;
                case FORK:
                    g.setColor(Color.yellow);
                    break;
                case NONE:
                    g.setColor(Color.red);
                    break;
            }
            g.fillOval(dotX, dotY, MARKER_SIZE, MARKER_SIZE);

            // draw cost and model name
            g.setColor(Color.black);
            g.drawString("$" + currentCost, dotX, dotY - 3);
            g.drawString(currentBike.versionNames.get(j), dotX, dotY + 30);
        }
    }


    /**
     * Given the cost of a model version, returns the x position to draw it at.
     * Called by drawBikes() and drawGrid().
     *
     * @param cost cost in dollars
     * @return x position for that cost
     */
    private int getXPosition(int cost) {
        int endWidth = width - margin * 2; //x location where everything should end
        return (int) ((((float) cost - costMin) / costRange) * endWidth + margin);
    }


}
