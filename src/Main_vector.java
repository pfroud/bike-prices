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
    static final String FILE_OUTPUT = "bikes.pdf"; //location of PDF file to write diagram to
    static Scanner fileScan; //Scanner to read input text file

    static int globalMinCost = 999999; //cost of the least expensive bike in the input file
    static int globalMaxCost = 0;//cost of the most expensive bike in the input file
    static float globalCostRange;

    static final int WIDTH = 1900; //width of the output PDF file. Units unknown.
    static final int HEIGHT = 940; //width of the output PDF file. Units unknown.
    static final int MARGIN = 100; //space between content and edges of page
    static final int END_WIDTH = WIDTH - MARGIN * 2; //x location where everything should end

    static final int RECT_HEIGHT = 20; //height of all the colored bars
    static final int VERTICAL_SPACING = RECT_HEIGHT + 30; //spacing between colored bars
    static final int MARKER_SIZE = RECT_HEIGHT - 5; //diameter of circle to mark a model version

    static final int GRID_STEP = 500; //spacing *in dollars* between vertical grid lines

    static Vector<Bike> allBikes = new Vector<>(); //holds every bike model


    /**
     * Program entry point.
     * Opens a text file with information about and writes a PDF file showing range and gradiation of prices.
     */
    public static void main(String[] args) throws IOException {

        //PDFGraphics2D extends Graphics2D so has the same interface.
        PDFGraphics2D g = new PDFGraphics2D(0.0, 0.0, WIDTH, HEIGHT);

        readAllBikes();
        drawGrid(g);
        drawBikes(g);

        //write to PDF file
        try (FileOutputStream file = new FileOutputStream(FILE_OUTPUT)) {
            file.write(g.getBytes());
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

            //this works but if terrible
            if (fileScan.hasNextLine()) {
                fileScan.nextLine();
            } else {
                break;
            }
        }

		/*globalMaxCost = 3500;
        globalMinCost = 500;*/

        //set the cost range
        globalCostRange = globalMaxCost - globalMinCost;
    }


    /**
     * Reads a bike model and its versions, then returns a Bike object.
     * Called by readAllBikes().
     *
     * (1) reads the header, which contains the model name and number of versions.
     *     Example: "Specialized_Diverge 7"
     *
     * (2) Does three for loops to read:
     *     (a) version names
     *     (b) version costs
     *     (c) version materials
     *
     * @return Bike object containing all of the version names, costs, and materials
     */
    private static Bike readOneBike() {


        Scanner sc = new Scanner(fileScan.nextLine());
        Bike bike = new Bike(sc.next());
        int numModels = sc.nextInt();
        String carb;

        // add version names
        for (int i = 0; i < numModels; i++) {
            bike.versions.add(fileScan.nextLine());
        }

        // add version costs
        int cst;
        for (int i = 0; i < numModels; i++) {
            cst = fileScan.nextInt();

            //update global min and max cost
            if (cst > globalMaxCost) {
                globalMaxCost = cst;
            }
            if (cst < globalMinCost) {
                globalMinCost = cst;
            }

            bike.addCost(cst);
        }

        // add version materials
        for (int i = 0; i < numModels; i++) {
            carb = fileScan.next();

            switch (carb) {
                case "all":
                    bike.carbons.add(Carbon.ALL);
                    break;
                case "fork":
                    bike.carbons.add(Carbon.FORK);
                    break;
                case "none":
                    bike.carbons.add(Carbon.NONE);
                    break;
                default:
                    System.err.println("unrecognized carbon value \"" + carb + "\"");
            }
        }

        sc.close();
        return bike;
    }


    /**
     * Draws
     * Called by main().
     *
     * @param g graphics context
     */
    private static void drawBikes(Graphics2D g) {

        Bike currentBike;
        int barVertPos, barWidth;
        float barXStart, barXEnd;

        // iterate over all the bike models
        for (int i = 0; i < allBikes.size(); i++) {
            currentBike = allBikes.get(i);

            // colored rectangle bar
            g.setColor(getColor(i));
            barVertPos = i * VERTICAL_SPACING + 20;
            barXStart = getPosition(currentBike.minCost);
            barXEnd = getPosition(currentBike.maxCost);
            barWidth = (int) (barXEnd - barXStart);
            g.fillRoundRect((int) barXStart, barVertPos, barWidth + MARKER_SIZE, RECT_HEIGHT, 10, 10);


            // draw dots for each version
            int currentCost, dotX, dotY, smallerSize;
            Carbon carb;
            for (int j = 0; j < currentBike.costs.size(); j++) {
                currentCost = currentBike.costs.get(j);

                // get position for the cost dot
                dotX = getPosition(currentCost);
                dotY = barVertPos + RECT_HEIGHT / 2 - MARKER_SIZE / 2;

                // draw dot with carbon color
                carb = currentBike.carbons.get(j);
                switch (carb) {
                    case ALL:
                        g.setColor(Color.black);
                        g.fillOval(dotX, dotY, MARKER_SIZE, MARKER_SIZE);
                        break;
                    case FORK:
                        g.setColor(Color.lightGray);
                        g.fillOval(dotX, dotY, MARKER_SIZE, MARKER_SIZE);
                        g.setColor(Color.black);
                        smallerSize = (int) (MARKER_SIZE / 1.5);
                        g.fillOval(dotX + (MARKER_SIZE - smallerSize) / 2, dotY + (MARKER_SIZE - smallerSize) / 2, smallerSize, smallerSize);
                        break;
                    case NONE:
                        g.setColor(Color.lightGray);
                        g.fillOval(dotX, dotY, MARKER_SIZE, MARKER_SIZE);
                        break;
                }

                // draw cost and model name
                g.setColor(Color.black);
                g.drawString("$" + currentCost, dotX, dotY - 3);
                g.drawString(currentBike.versions.get(j), dotX, dotY + 30);
            }

            // black background for model name on left of page
            g.setColor(Color.black);
            g.fillRoundRect(8, barVertPos + RECT_HEIGHT - 25, 150, 30, 3, 3);

            // draw model name over black background
            g.setColor(getColor(i));
            g.setFont(new Font("Arial", Font.BOLD, 14));
            g.drawString(currentBike.modelName, 10, barVertPos + RECT_HEIGHT - 6);

            //reset font?
            g.setFont(new Font("Arial", Font.PLAIN, 14));
        }
    }


    /**
     * Draws the horizontal axis, vertical grid, and labels.
     * Called by main().
     *
     * @param g graphics context
     */
    private static void drawGrid(Graphics2D g) {
        g.drawLine(MARGIN, HEIGHT - MARGIN, WIDTH - MARGIN, HEIGHT - MARGIN); // bottom axis

        // min and max labels for bottom axis
        g.setFont(new Font("Arial", Font.BOLD, 22));
        g.drawString("$" + globalMinCost, MARGIN, HEIGHT - MARGIN + 20);
        g.drawString("$" + globalMaxCost, WIDTH - MARGIN - 60, HEIGHT - MARGIN + 20);

        //setup for vertical lines
        g.setFont(new Font("Arial", Font.PLAIN, 14));
        g.setColor(Color.decode("0xbbbbbb"));
        int xPos;

        //draw vertical lines
        for (int cost = globalMinCost; cost <= globalMaxCost; cost += GRID_STEP) {
            xPos = getPosition(cost);
            g.drawLine(xPos, 0, xPos, HEIGHT - MARGIN + 60);

            // Draw labels for vertical lines. Skip if at min and max because already drawn in bigger font.
            if (globalMinCost != cost && globalMaxCost != cost) {
                g.drawString("$" + cost, xPos, HEIGHT - MARGIN + 15);
            }

        }

    }

    /**
     * Given the cost of a model version, returns the x position to draw it at.
     * Called by drawBikes() and drawGrid().
     *
     * @param c cost in dollars
     * @return x position for that cost
     */
    private static int getPosition(int c) {
        float cost = c; // to get floating-point division
        return (int) (((cost - globalMinCost) / globalCostRange) * END_WIDTH + MARGIN);
    }


    /**
     * Given the index of a bike model in the vector allBikes, return the color to draw the bar.
     * This is just to look pretty.
     * Called by drawBikes().
     *
     * @param index sequential index
     * @return the color for that bar
     */
    private static Color getColor(int index) {
        switch (index % 4) {
            case 0:
                return Color.decode("0x65B870"); // green
            case 1:
                return Color.decode("0x41AEF2"); // blue
            case 2:
                return Color.decode("0xF2BD41"); // orange
            case 3:
                return Color.decode("0xDBA2B2"); // purple
        }
        return null;
    }


}
