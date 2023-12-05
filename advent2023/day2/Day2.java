package advent2023.day2;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The Day2 class represents a game with various rounds and colors.
 * It provides methods to process game data from a text file and calculate various game statistics.
 * The class maintains a gamesMap where each game ID is mapped to another HashMap.
 * The inner HashMap maps color names to the maximum count of each color across all rounds.
 * The class also calculates and stores the sum of game IDs for all possible games that can be played based on the
 * maximum available cubes of each color,
 * and the sum of the products of the minimum required cubes of each color for each game.
 */
public class Day2 {

    private final HashMap<Integer, HashMap<String, Integer>> gamesMap = new HashMap<>();
    private final int sumOfPossibleGames;

    private final int totalGamePower;

    public Day2(HashMap<String, Integer> cubeStock, File gameFile) {
        gameListToGameMap(fileToList(gameFile));
        this.totalGamePower = calculateGamePower(this.gamesMap);
        this.sumOfPossibleGames = getPossibleGameSum(this.gamesMap, cubeStock);
    }

    /**
     * Method that takes an ArrayList of Strings, where each String represents a line from a game file,
     * and adds each game to the gamesMap HashMap.
     *
     * @param gameList The ArrayList of Strings to be processed.
     */
    private void gameListToGameMap(ArrayList<String> gameList) {
        for (String line : gameList) {
            addGameToMap(line);
        }
    }

    /**
     * This method reads a text file and returns its content as an ArrayList of Strings, where each String represents a
     * line in the file.
     *
     * @param inputFile The File object representing the text file to be read.
     * @return An ArrayList of Strings, where each String is a line from the input file.
     */
    private ArrayList<String> fileToList(File inputFile) {
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

    /**
     * This method takes a string representing a game's details and adds it to the gamesMap HashMap.
     * The input string should be in the format "Game X: Y color1, Z color2, W color3; ...", where X is the game number,
     * Y, Z, W are the counts of each color in each round, and color1, color2, color3 are the colors.
     * The method extracts the game number and the maximum count of each color across all rounds, and stores them in
     * the gamesMap.
     *
     * @param input The input string representing a game's details.
     */
    private void addGameToMap(String input) {
        HashMap<String, Integer> colorMap = new HashMap<>();

        // Extract game number
        Pattern gamePattern = Pattern.compile("Game (\\d+):");
        Matcher gameMatcher = gamePattern.matcher(input);
        if (!gameMatcher.find()) {
            System.out.println("Invalid input format.");
            return;
        }
        int gameNumber = Integer.parseInt(gameMatcher.group(1));

        // Initialize max counts
        int maxGreen = 0, maxBlue = 0, maxRed = 0;

        // Split input into segments
        String[] draws = input.substring(gameMatcher.end()).split(";");
        for (String draw : draws) {
            // Process each segment
            String[] colors = draw.trim().split(",");
            for (String color : colors) {
                String[] parts = color.trim().split(" ");
                int count = Integer.parseInt(parts[0]);
                switch (parts[1]) {
                    case "green":
                        maxGreen = Math.max(maxGreen, count);
                        break;
                    case "blue":
                        maxBlue = Math.max(maxBlue, count);
                        break;
                    case "red":
                        maxRed = Math.max(maxRed, count);
                        break;
                }
            }
        }

        colorMap.put("green", maxGreen);
        colorMap.put("blue", maxBlue);
        colorMap.put("red", maxRed);

        if (!gamesMap.containsKey(gameNumber)) {
            gamesMap.put(gameNumber, colorMap);
        }
    }

    /**
     * This method checks if a game can be played based on the maximum available cubes of each color.
     * It takes two HashMaps as input: one representing the game entry with the required number of cubes of each color,
     * and the other representing the maximum available cubes of each color.
     *
     * @param gameEntry A HashMap representing the game entry with keys as color names and values as the required
     *                  number of cubes of each color.
     * @param maxCubes A HashMap representing the maximum available cubes with keys as color names and values as the
     *                 number of available cubes of each color.
     * @return A boolean value indicating whether the game can be played. Returns true if the game can be played,
     * false otherwise.
     */
    private boolean isGamePossible(HashMap<String, Integer> gameEntry, HashMap<String, Integer> maxCubes) {
        int green = gameEntry.get("green");
        int red = gameEntry.get("red");
        int blue = gameEntry.get("blue");

        int maxGreen = maxCubes.get("green");
        int maxRed = maxCubes.get("red");
        int maxBlue = maxCubes.get("blue");

        return green <= maxGreen && red <= maxRed && blue <= maxBlue;
    }

    /**
     * This method calculates the sum of the game IDs for all possible games that can be played based on the maximum
     * available cubes of each color.
     * It takes two HashMaps as input: one representing the gamesMap with game IDs as keys and another HashMap as
     * values (which contains color names as keys and required number of cubes as values), and the other representing
     * the maximum available cubes of each color.
     *
     * @param gamesMap A HashMap representing the gamesMap with game IDs as keys and another HashMap as values
     *                 (which contains color names as keys and required number of cubes as values).
     * @param maxCubes A HashMap representing the maximum available cubes with keys as color names and values as the
     *                 number of available cubes of each color.
     * @return An integer value indicating the sum of the game IDs for all possible games that can be played.
     */
    private int getPossibleGameSum(HashMap<Integer, HashMap<String, Integer>> gamesMap, HashMap<String, Integer> maxCubes) {
        int sumOfPossibleGameId = 0;

        for (Map.Entry<Integer, HashMap<String, Integer>> game : gamesMap.entrySet()) {
            if (isGamePossible(game.getValue(), maxCubes)) {
                sumOfPossibleGameId += game.getKey();
            }
        }
        return sumOfPossibleGameId;
    }

    /**
     * This method calculates the sum of the products of the minimum required cubes of each color for each game.
     * It takes a HashMap as input, which represents the gamesMap with game IDs as keys and another HashMap as values.
     * The inner HashMap contains color names as keys and required number of cubes as values.
     *
     * @param gamesMap A HashMap representing the gamesMap with game IDs as keys and another HashMap as values
     *                 (which contains color names as keys and required number of cubes as values).
     * @return An integer value indicating the sum of the products of the minimum required cubes of each color for each
     * game.
     */
    private int calculateGamePower(HashMap<Integer, HashMap<String, Integer>> gamesMap) {
        int sumOfGamePower = 0;

        for (Map.Entry<Integer, HashMap<String, Integer>> game : gamesMap.entrySet()) {
            HashMap<String, Integer> currentGameMap = new HashMap<>(game.getValue());

            // set the minimum required value for each block color in the map. If the value is 0, set 1 as the result
            // will be multiplied and should not affect the outcome.
            int green = currentGameMap.get("green") == 0 ? 1 : currentGameMap.get("green");
            int blue = currentGameMap.get("blue") == 0 ? 1 : currentGameMap.get("blue");
            int red = currentGameMap.get("red") == 0 ? 1 : currentGameMap.get("red");

            sumOfGamePower += green * red * blue;
        }
        return sumOfGamePower;
    }

    public int getSumOfPossibleGames() {
        return this.sumOfPossibleGames;
    }

    public int getTotalGamePower() {
        return this.totalGamePower;
    }

}
