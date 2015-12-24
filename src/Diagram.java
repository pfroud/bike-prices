import de.erichseifert.vectorgraphics2d.PDFGraphics2D;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;
import java.util.Vector;


public class Diagram {

    Scanner fileScan; //Scanner to read input text file

    int globalMinCost = 999999; //cost of the least expensive bike in the input file
    int globalMaxCost = 0;//cost of the most expensive bike in the input file
    float globalCostRange; //difference between least and most expensive bike in input file

    final boolean doRangeOverride = true; //whether or not to override the cost range
    final int globalMin_override = 500; //custom cost range start
    final int globalMax_override = 10000; //custom cost range end

    final int WIDTH = 1900; //width of the output PDF file. Units unknown.
    final int HEIGHT = 940; //width of the output PDF file. Units unknown.
    final int MARGIN = 100; //space between content and edges of page. Units unknown.
    final int END_WIDTH = WIDTH - MARGIN * 2; //x location where everything should end

    final int RECT_HEIGHT = 20; //height of each horizontal bar
    final int VERTICAL_SPACING = RECT_HEIGHT + 30; //spacing between each horizontal bar
    final int MARKER_SIZE = RECT_HEIGHT - 5; //diameter of circle to mark a model version

    final int GRID_STEP = 500; //spacing *in dollars* between vertical grid lines

    final Color GRID_VERTICAL_COLOR = Color.decode("0xbbbbbb");
    final Color BAR_BACKGROUND_COLOR = Color.gray;
    final int RECT_RADIUS = 0;

    Vector<Bike> allBikes = new Vector<>(); //holds every bike model
    int numHistogramBins;
    PDFGraphics2D g;


    public Diagram(){
        g = new PDFGraphics2D(0.0, 0.0, WIDTH, HEIGHT);
    }

    public void init(String file_input){
        readAllBikes(file_input);

        numHistogramBins = 3;
//        printHistograms(numHistogramBins);
//        System.out.println("----------------");
//        printRanges();
//        System.exit(0); //stop here for testing

        drawGrid(g);
        drawBikes(g);
    }

    public void writeToFile(String file_output) throws IOException {
        try (FileOutputStream file = new FileOutputStream(file_output)) {
            file.write(g.getBytes());
        }
    }

    /**
     * Opens the input file and reads all bikes in the file.
     * Called by main().
     */
    private void readAllBikes(String file_input) {

        //open the file
        try {
            fileScan = new Scanner(new File(file_input));
        } catch (FileNotFoundException e) {
            System.err.println("File " + file_input + " not found.");
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
    private Bike readOneBike() {

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
    private void drawGrid(Graphics2D g) {
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
    private void drawBikes(Graphics2D g) {
        Bike currentBike;
        int barYPos, barWidth;
        float barXStart, barXEnd; // x positions of start and end of a bar
        Font fontModelName = new Font("Arial", Font.BOLD, 14);
        initColors(numHistogramBins);

        // iterate over all the bike models
        for (int i = 0; i < allBikes.size(); i++) {
            currentBike = allBikes.get(i);

            // colored rectangle bar
            g.setColor(BAR_BACKGROUND_COLOR);
            barYPos = i * VERTICAL_SPACING + 20;
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

    private Color[] colors;

    private void initColors(int numHistogramBins) {
        colors = new Color[numHistogramBins];
        float n = 0.4f;
        colors[0] = new Color(n, n, n);
        for (int i = 1; i < numHistogramBins; i++) {
            colors[i] = colors[i - 1].brighter();
        }
    }


    /**
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
        return (int) ((((float) cost - globalMinCost) / globalCostRange) * END_WIDTH + MARGIN);
    }


        /**
     * Prints histogram info for each bike.
     */
    public void printHistograms(int numHistogramBins) {
        for (Bike bike : allBikes) {
            bike.printHistogram(numHistogramBins);
        }
    }

    /**
     * Prints range info for each bike.
     */
    public void printRanges() {
        System.out.println("model\tabsolute range\tfactor"); //header for use in csv file
        for (Bike bike : allBikes) {
            bike.printRange();
        }
    }


}
