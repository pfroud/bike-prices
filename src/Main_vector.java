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

public class Main_vector {

    static Scanner scanFile;
    static int globalMinCost = 999999;
    static int globalMaxCost = 0;

    static final int width = 1900;
    static final int height = 940;
    static final int padding = 100;
    static final int rectHeight = 20;
    static final int endWidth = width - padding * 2;
    static final int markerSize = rectHeight - 5;
    static final int gridStep = 500;
    static final int verticalSpacing = rectHeight + 30;
    static float globalCostRange;

    static Vector<Bike> allBikes = new Vector<Bike>();

    public static void main(String[] args) throws IOException {
        PDFGraphics2D g = new PDFGraphics2D(0.0, 0.0, 1900, 940);


        init();
        draw(g);

        try (FileOutputStream file = new FileOutputStream("bikes.pdf")) {
            file.write(g.getBytes());
        }

        // System.out.println("globalCostRange is " + (int) globalCostRange);

    }

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
            verticalPosition = i * verticalSpacing + 20;
            startX = posFromCost(currentBike.minCost);
            endX = posFromCost(currentBike.maxCost);
            rectWidth = (int) (endX - startX);
            g.fillRoundRect((int) startX, verticalPosition, rectWidth + markerSize, rectHeight, 10, 10);

            int currentCost, x, y, smallerSize;
            Carbon carb;
            // iterate over sub-models
            for (int j = 0; j < currentBike.costs.size(); j++) {
                currentCost = currentBike.costs.get(j);

                // get position for the cost dot
                x = posFromCost(currentCost);
                y = verticalPosition + rectHeight / 2 - markerSize / 2;

                // draw dot with carbon color
                carb = currentBike.carbons.get(j);
                switch (carb) {
                    case ALL:
                        g.setColor(Color.black);
                        g.fillOval(x, y, markerSize, markerSize);
                        break;
                    case FORK:
                        g.setColor(Color.lightGray);
                        g.fillOval(x, y, markerSize, markerSize);
                        g.setColor(Color.black);
                        smallerSize = (int) (markerSize / 1.5);
                        g.fillOval(x + (markerSize - smallerSize) / 2, y + (markerSize - smallerSize) / 2, smallerSize, smallerSize);
                        break;
                    case NONE:
                        g.setColor(Color.lightGray);
                        g.fillOval(x, y, markerSize, markerSize);
                        break;
                }

                // draw cost and model name
                g.setColor(Color.black);
                g.drawString("$" + currentCost, x, y - 3);
                g.drawString(currentBike.names.get(j), x, y + 30); // change
                // cost
                // alignment
                // here
            }

            // black background for model name
            g.setColor(Color.black);
            g.fillRoundRect(8, verticalPosition + rectHeight - 25, 150, 30, 3, 3);

            // draw model name
            g.setColor(getColor(i));
            g.setFont(new Font("Arial", Font.BOLD, 14));
            g.drawString(currentBike.lineName, 10, verticalPosition + rectHeight - 6); // change
            // name
            // alignment
            // here

            g.setFont(new Font("Arial", Font.PLAIN, 14));
        }
    }

    private static void drawGrid(Graphics2D g) {
        // bottom line
        g.drawLine(padding, height - padding, width - padding, height - padding);

        // min and max labels
        g.setFont(new Font("Arial", Font.BOLD, 22));
        g.drawString("$" + globalMinCost, padding, height - padding + 20);
        g.drawString("$" + globalMaxCost, width - padding - 60, height - padding + 20);

        g.setFont(new Font("Arial", Font.PLAIN, 14));
        int x;

        // draw vertical lines
        for (int cost = globalMinCost; cost <= globalMaxCost; cost += gridStep) {
            x = posFromCost(cost);
            g.setColor(Color.decode("0xbbbbbb"));
            g.drawLine(x, 0, x, height - padding + 60);

            // skip labels at min and max, already drawn in bigger font
            if (globalMinCost != cost && globalMaxCost != cost) {
                g.drawString("$" + cost, x, height - padding + 15);
            }

        }

    }

    private static int posFromCost(int c) {
        float cost = c; // to get floating-point division
        return (int) (((cost - globalMinCost) / globalCostRange) * endWidth + padding);
    }

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

    private static void init() {

        try {
            scanFile = new Scanner(new File("bikesInput.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        }

        Bike current = null;

        while (scanFile.hasNextLine()) {
            try {
                current = readBike();
            } catch (Exception e) {
                e.printStackTrace();
                System.exit(1);
            }
            // System.out.println(current.lineName);
            allBikes.add(current);

            // System.out.println();
            scanFile.nextLine();
            if (scanFile.hasNextLine()) {
                scanFile.nextLine();
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

    private static Bike readBike() throws Exception {
        String bikeHeader = scanFile.nextLine();
        // System.out.println(">\"" + bikeHeader + "\":");

        Scanner sc = new Scanner(bikeHeader);
        Bike bike = new Bike(sc.next());
        int numModels = sc.nextInt();
        int c;
        String carb;

        // add name
        for (int i = 0; i < numModels; i++) {
            bike.names.add(scanFile.nextLine());
        }

        // add cost
        for (int i = 0; i < numModels; i++) {
            c = scanFile.nextInt();
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
            carb = scanFile.next();
            if (carb.equals("all")) {
                bike.carbons.add(Carbon.ALL);
            } else if (carb.equals("fork")) {
                bike.carbons.add(Carbon.FORK);
            } else if (carb.equals("none")) {
                bike.carbons.add(Carbon.NONE);
            } else {
                sc.close();
                throw new Exception("unknown carbon value \"" + carb + "\"");
            }
        }
        sc.close();
        return bike;
    }

}
