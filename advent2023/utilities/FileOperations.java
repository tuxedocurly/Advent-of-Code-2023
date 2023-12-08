package advent2023.utilities;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class FileOperations {

    /**
     * This method reads a text file and returns its content as an ArrayList of Strings, where each String represents a
     * line in the file.
     *
     * @param inputFile The File object representing the text file to be read.
     * @return An ArrayList of Strings, where each String is a line from the input file.
     */
    public static ArrayList<String> fileToList(File inputFile) {
        ArrayList<String> fileLines = new ArrayList<>();

        try {
            BufferedReader br = new BufferedReader(new FileReader(inputFile));
            String line;
            while ((line = br.readLine()) != null) {
                fileLines.add(line);
            }
        } catch (IOException e) {
            System.out.println("Error: file not found -> " + e.getMessage());
        }
        return fileLines;
    }
}
