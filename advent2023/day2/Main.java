package advent2023.day2;

import java.io.File;
import java.util.HashMap;

public class Main {
    public static void main(String[] args) {
        HashMap<String, Integer> cubeStockMap = new HashMap<>();
        File input = new File("advent2023/day2/input.txt");


        cubeStockMap.put("green", 13);
        cubeStockMap.put("blue", 14);
        cubeStockMap.put("red", 12);
        Day2 games = new Day2(cubeStockMap, input);

        System.out.println("Sum of the IDs of all possible games in file: " + games.getSumOfPossibleGames());

        System.out.println("Sum of the products of the minimum cubes required for each game: " + games.getTotalGamePower());

    }

}
