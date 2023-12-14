package advent2023.day6;

import java.io.IOException;
import java.util.ArrayList;

import static advent2023.day6.CountWaysToWinRace.*;

public class Main {

    public static void main(String[] args) {
        ArrayList<long[]> races1 = new ArrayList<>();
        ArrayList<long[]> races2 = new ArrayList<>();
        long solution1 = 1;
        long solution2 = 0;

        try {
            races1 = parseRaceFile("advent2023/day6/input.txt");
            races2 = parseRaceFileAsSingleRace("advent2023/day6/input.txt");

        } catch (IOException e) {
            System.out.println("Error reading file");
        }

        // part 1 solution
        for (long[] race : races1) {
            solution1 *= countWaysToWin(race);
        }

        // part 2 solution
        for (long[] race : races2) {
            solution2 = countWaysToWin(race);
        }

        System.out.println("Part 1 solution: " + solution1);
        System.out.println("Part 2 solution: " + solution2);

    }
}
