import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;
import java.util.Vector;

import de.erichseifert.vectorgraphics2d.PDFGraphics2D;

/**
 * Makes a PDF of bikes from input file.
 */
public class Main_vector {

    static final String FILE_INPUT = "bikesInput.txt"; //location of text file containing bike information
    static final String FILE_OUTPUT = "testing.pdf"; //location of PDF file to write diagram to
    static Scanner fileScan; //Scanner to read input text file

    static int globalMinCost = 999999; //cost of the least expensive bike in the input file
    static int globalMaxCost = 0;//cost of the most expensive bike in the input file
    static float globalCostRange; //difference between least and most expensive bike in input file

    static final boolean doRangeOverride = true; //whether or not to override the cost range
    static final int globalMin_override = 500; //custom cost range start
    static final int globalMax_override = 10000; //custom cost range end

    static final int WIDTH = 1900; //width of the output PDF file. Units unknown.
    static final int HEIGHT = 940; //width of the output PDF file. Units unknown.
    static final int MARGIN = 100; //space between content and edges of page. Units unknown.
    static final int END_WIDTH = WIDTH - MARGIN * 2; //x location where everything should end

    static final int RECT_HEIGHT = 20; //height of each horizontal bar
    static final int VERTICAL_SPACING = RECT_HEIGHT + 30; //spacing between each horizontal bar
    static final int MARKER_SIZE = RECT_HEIGHT - 5; //diameter of circle to mark a model version

    static final int GRID_STEP = 500; //spacing *in dollars* between vertical grid lines

    static final Color GRID_VERTICAL_COLOR = Color.decode("0xbbbbbb");
    static final Color BAR_BACKGROUND_COLOR = Color.gray;

    static Vector<Bike> allBikes = new Vector<>(); //holds every bike model


    /**
     * Program entry point.
     * Opens a text file with information about and writes a PDF file showing range and gradiation of prices.
     */
    public static void main(String[] args) throws IOException {

        //PDFGraphics2D extends Graphics2D so has the same interface.
        PDFGraphics2D g = new PDFGraphics2D(0.0, 0.0, WIDTH, HEIGHT);

        readAllBikes();

        Bike.numHistogramBins = 3;
//        printHistograms();
//        System.exit(0); //stop here for testing

        drawGrid(g);
        drawBikes(g);

        //write to PDF file
        try (FileOutputStream file = new FileOutputStream(FILE_OUTPUT)) {
            file.write(g.getBytes());
        }
    }

    /**
     * Prints histogram info for each bike.
     */
    public static void printHistograms() {
        for (Bike bike : allBikes) {
            bike.printHistogram();
        }
    }

    /**
     * Prints range info for each bike.
     */
    public static void printRanges() {
        System.out.println("model\tabsolute range\tfactor"); //header for use in csv file
        for (Bike bike : allBikes) {
            bike.printRange();
        }
    }

    /**
     * Opens the input file and reads all bikes in the file.
     * Called by main().
     */
    private static void readAllBikes() {

        //open the file
        try {
            fileScan = new Scanner(new File(FILE_INPUT));
        } catch (FileNotFoundException e) {
            System.err.println("File " + FILE_INPUT + " not found.");
            System.exit(1);
        }

        //go through file
        while (fileScan.hasNextLine()) {
            allBikes.add(readOneBike());

            fileScan.nextLine(); //skip a blank line

            //this works but is terrible
            if (fileScan.hasNextLine()) {
                fileScan.nextLine();
            } else {
                break;
            }
        }

        //optionally set a custom min and max cost to display
        if (doRangeOverride) {
            globalMaxCost = globalMax_override;
            globalMinCost = globalMin_override;
        }

        //set the cost range
        globalCostRange = globalMaxCost - globalMinCost;
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
     * @return Bike object containing all of the version names, costs, and materials
     */
    //@formatter:on
    private static Bike readOneBike() {

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
            if (currentCost > globalMaxCost) {
                globalMaxCost = currentCost;
            }
            if (currentCost < globalMinCost) {
                globalMinCost = currentCost;
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
    private static void drawGrid(Graphics2D g) {
        //          x1   ,  y1            , x2            , y2
        g.drawLine(MARGIN, HEIGHT - MARGIN, WIDTH - MARGIN, HEIGHT - MARGIN); // bottom axis

        // min and max labels for bottom axis
        g.setFont(new Font("Arial", Font.BOLD, 22)); //22pt size
        g.drawString("$" + globalMinCost, MARGIN, HEIGHT - MARGIN + 20);
        g.drawString("$" + globalMaxCost, WIDTH - MARGIN - 60, HEIGHT - MARGIN + 20);

        //setup for vertical lines
        g.setFont(new Font("Arial", Font.PLAIN, 14)); //14pt size
        g.setColor(GRID_VERTICAL_COLOR);
        int xPos;

        //draw vertical lines
        for (int cost = globalMinCost; cost <= globalMaxCost; cost += GRID_STEP) {
            xPos = getXPosition(cost);
            g.drawLine(xPos, 0, xPos, HEIGHT - MARGIN + 60);

            // Draw labels for vertical lines. Skip if at min and max because already drawn in bigger font.
            if (globalMinCost != cost && globalMaxCost != cost) {
                g.drawString("$" + cost, xPos, HEIGHT - MARGIN + 15);
            }

        }

    }

    /**
     * Draws the PDF.
     * Called by main().
     *
     * @param g graphics context
     */
    private static void drawBikes(Graphics2D g) {
        Bike currentBike;
        int barYPos, barWidth;
        float barXStart, barXEnd; // x positions of start and end of a bar
        Font fontModelName = new Font("Arial", Font.BOLD, 14);

        // iterate over all the bike models
        for (int i = 0; i < allBikes.size(); i++) {
            currentBike = allBikes.get(i);

            // colored rectangle bar
            g.setColor(BAR_BACKGROUND_COLOR);
            barYPos = i * VERTICAL_SPACING + 20;
            barXStart = getXPosition(currentBike.minCost);
            barXEnd = getXPosition(currentBike.maxCost);
            barWidth = (int) (barXEnd - barXStart);

            //binWidth is in dollars
//            double binWidth = (double) (currentBike.maxCost - currentBike.minCost) / Bike.numHistogramBins;
//
//            for(int j=0; i<Bike.numHistogramBins; i++){
//
//            }
            g.fillRoundRect((int) barXStart, barYPos, barWidth + MARKER_SIZE, RECT_HEIGHT, 10, 10);

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
     * @param g           graphics context
     * @param currentBike the bike object from the main loop
     * @param barVertPos  vertical position of that bike's bar
     */
    private static void drawAllDots(Graphics2D g, Bike currentBike, int barVertPos) {
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
    private static int getXPosition(int cost) {
        return (int) ((((float) cost - globalMinCost) / globalCostRange) * END_WIDTH + MARGIN);
    }


}
