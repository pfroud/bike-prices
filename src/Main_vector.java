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

    static Scanner fileScan; //Scanner to read input text file
    static int globalMinCost = 999999; //cost of the least expensive bike in the input file
    static int globalMaxCost = 0;//cost of the most expensive bike in the input file

    static final int WIDTH = 1900; //width of the output PDF file. Units unknown.
    static final int HEIGHT = 940; //width of the output PDF file. Units unknown.
    static final int MARGIN = 100; //space between content and edges of page
    static final int END_WIDTH = WIDTH - MARGIN * 2; //x location where everything should end

    static final int RECT_HEIGHT = 20; //height of all the colored bars
    static final int VERTICAL_SPACING = RECT_HEIGHT + 30; //spacing between colored bars
    static final int MARKER_SIZE = RECT_HEIGHT - 5; //diameter of circle to mark a model version


    static final int GRID_STEP = 500; //horizontal spacing of
    static float globalCostRange;

    static Vector<Bike> allBikes = new Vector<>(); //holds every bike model

    /**
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        PDFGraphics2D g = new PDFGraphics2D(0.0, 0.0, WIDTH, HEIGHT);


        init();
        draw(g);

        try (FileOutputStream file = new FileOutputStream("bikes.pdf")) {
            file.write(g.getBytes());
        }

        // System.out.println("globalCostRange is " + (int) globalCostRange);

    }

    /**
     * @param g
     */
    private static void draw(Graphics2D g) {
        drawGrid(g);

        Bike currentBike;
        int verticalPosition, rectWidth;
        float startX, endX;

        // iterate over major bike models
        for (int i = 0; i < allBikes.size(); i++) {
            currentBike = allBikes.get(i);

            // background rectangle bar
            g.setColor(getColor(i));
            verticalPosition = i * VERTICAL_SPACING + 20;
            startX = posFromCost(currentBike.minCost);
            endX = posFromCost(currentBike.maxCost);
            rectWidth = (int) (endX - startX);
            g.fillRoundRect((int) startX, verticalPosition, rectWidth + MARKER_SIZE, RECT_HEIGHT, 10, 10);

            int currentCost, x, y, smallerSize;
            Carbon carb;
            // iterate over sub-models
            for (int j = 0; j < currentBike.costs.size(); j++) {
                currentCost = currentBike.costs.get(j);

                // get position for the cost dot
                x = posFromCost(currentCost);
                y = verticalPosition + RECT_HEIGHT / 2 - MARKER_SIZE / 2;

                // draw dot with carbon color
                carb = currentBike.carbons.get(j);
                switch (carb) {
                    case ALL:
                        g.setColor(Color.black);
                        g.fillOval(x, y, MARKER_SIZE, MARKER_SIZE);
                        break;
                    case FORK:
                        g.setColor(Color.lightGray);
                        g.fillOval(x, y, MARKER_SIZE, MARKER_SIZE);
                        g.setColor(Color.black);
                        smallerSize = (int) (MARKER_SIZE / 1.5);
                        g.fillOval(x + (MARKER_SIZE - smallerSize) / 2, y + (MARKER_SIZE - smallerSize) / 2, smallerSize, smallerSize);
                        break;
                    case NONE:
                        g.setColor(Color.lightGray);
                        g.fillOval(x, y, MARKER_SIZE, MARKER_SIZE);
                        break;
                }

                // draw cost and model name
                g.setColor(Color.black);
                g.drawString("$" + currentCost, x, y - 3);
                g.drawString(currentBike.versions.get(j), x, y + 30); // change
                // cost
                // alignment
                // here
            }

            // black background for model name
            g.setColor(Color.black);
            g.fillRoundRect(8, verticalPosition + RECT_HEIGHT - 25, 150, 30, 3, 3);

            // draw model name
            g.setColor(getColor(i));
            g.setFont(new Font("Arial", Font.BOLD, 14));
            g.drawString(currentBike.modelName, 10, verticalPosition + RECT_HEIGHT - 6); // change
            // name
            // alignment
            // here

            g.setFont(new Font("Arial", Font.PLAIN, 14));
        }
    }


    /**
     * @param g
     */
    private static void drawGrid(Graphics2D g) {
        // bottom line
        g.drawLine(MARGIN, HEIGHT - MARGIN, WIDTH - MARGIN, HEIGHT - MARGIN);

        // min and max labels
        g.setFont(new Font("Arial", Font.BOLD, 22));
        g.drawString("$" + globalMinCost, MARGIN, HEIGHT - MARGIN + 20);
        g.drawString("$" + globalMaxCost, WIDTH - MARGIN - 60, HEIGHT - MARGIN + 20);

        g.setFont(new Font("Arial", Font.PLAIN, 14));
        int x;

        // draw vertical lines
        for (int cost = globalMinCost; cost <= globalMaxCost; cost += GRID_STEP) {
            x = posFromCost(cost);
            g.setColor(Color.decode("0xbbbbbb"));
            g.drawLine(x, 0, x, HEIGHT - MARGIN + 60);

            // skip labels at min and max, already drawn in bigger font
            if (globalMinCost != cost && globalMaxCost != cost) {
                g.drawString("$" + cost, x, HEIGHT - MARGIN + 15);
            }

        }

    }

    /**
     * @param c
     * @return
     */
    private static int posFromCost(int c) {
        float cost = c; // to get floating-point division
        return (int) (((cost - globalMinCost) / globalCostRange) * END_WIDTH + MARGIN);
    }

    /**
     * @param index
     * @return
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


    /**
     *
     */
    private static void init() {

        try {
            fileScan = new Scanner(new File("bikesInput.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        }

        Bike current = null;

        while (fileScan.hasNextLine()) {
            try {
                current = readBike();
            } catch (Exception e) {
                e.printStackTrace();
                System.exit(1);
            }
            // System.out.println(current.modelName);
            allBikes.add(current);

            // System.out.println();
            fileScan.nextLine();
            if (fileScan.hasNextLine()) {
                fileScan.nextLine();
            } else {
                break;
            }
        }

		/*globalMaxCost = 3500;
        globalMinCost = 500;*/

        globalCostRange = globalMaxCost - globalMinCost;
        // System.out.println("min is " + globalMinCost + " and max is " +
        // globalMaxCost);
    }


    /**
     * @return
     * @throws Exception
     */
    private static Bike readBike() throws Exception {
        String bikeHeader = fileScan.nextLine();
        // System.out.println(">\"" + bikeHeader + "\":");

        Scanner sc = new Scanner(bikeHeader);
        Bike bike = new Bike(sc.next());
        int numModels = sc.nextInt();
        int c;
        String carb;

        // add name
        for (int i = 0; i < numModels; i++) {
            bike.versions.add(fileScan.nextLine());
        }

        // add cost
        for (int i = 0; i < numModels; i++) {
            c = fileScan.nextInt();
            if (c > globalMaxCost) {
                globalMaxCost = c;
            }
            if (c < globalMinCost) {
                globalMinCost = c;
            }
            bike.addCost(c);
        }

        // add carbon
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
                    sc.close();
                    throw new Exception("unknown carbon value \"" + carb + "\"");
            }
        }
        sc.close();
        return bike;
    }

}
