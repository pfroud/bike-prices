import de.erichseifert.vectorgraphics2d.PDFGraphics2D;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;
import java.util.Vector;

/**
 * Makes a PDF of bikes from input file.
 * There is also Main_applet which is old and not on git.
 */
public class Main {

    static final String FILE_INPUT = "bikesInput.txt"; //location of text file containing bike information
    static final String FILE_OUTPUT = "testing.pdf"; //location of PDF file to write diagram to


    /**
     * Program entry point.
     * Opens a text file with information about and writes a PDF file showing range and gradiation of prices.
     */
    public static void main(String[] args) throws IOException {

        Diagram d = new Diagram();
        d.init(FILE_INPUT);

        d.writeToFile(FILE_OUTPUT);

    }



}
