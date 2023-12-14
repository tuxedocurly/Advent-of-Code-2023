package advent2023.day6;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class CountWaysToWinRace {

    /**
     * Parses the race file and returns a list of time and distance pairs for each race.
     *
     * @param filePath The location of the race file.
     * @return A list of time and distance pairs for each race.
     * @throws IOException If an I/O error occurs.
     */
    public static ArrayList<long[]> parseRaceFile(String filePath) throws IOException {
        ArrayList<long[]> timeDistanceList = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\s+");
                if (parts[0].equals("Time:")) {
                    String[] distanceParts = reader.readLine().split("\\s+");
                    for (int i = 1; i < parts.length; i++) {
                        long time = Long.parseLong(parts[i]);
                        long distance = Long.parseLong(distanceParts[i]);
                        timeDistanceList.add(new long[]{time, distance});
                    }
                }
            }
        }
        return timeDistanceList;
    }

    /**
     * Parses the race file and returns a list of time and distance pairs for a single race.
     *
     * @param filePath The location of the race file.
     * @return A list of time and distance pairs for a single race.
     * @throws IOException If an I/O error occurs.
     */
    public static ArrayList<long[]> parseRaceFileAsSingleRace(String filePath) throws IOException {
        ArrayList<long[]> timeDistanceList = new ArrayList<>();
        StringBuilder time = new StringBuilder();
        StringBuilder distance = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\s+");
                if (parts[0].equals("Time:")) {
                    String[] distanceParts = reader.readLine().split("\\s+");
                    for (int i = 1; i < parts.length; i++) {
                        time.append(parts[i]);
                        distance.append(distanceParts[i]);

                    }
                }
            }
            timeDistanceList.add(new long[]{
                    Long.parseLong(String.valueOf(time)),
                    Long.parseLong(String.valueOf(distance))
            });
        }
        return timeDistanceList;
    }

    /**
     * Counts the number of ways to win a race given the time and record distance.
     *
     * @param input An array containing the time and record distance.
     * @return The number of ways to win the race.
     */
    public static long countWaysToWin(long[] input) {
        long T = input[0];
        long recordDistance = input[1];
        double discriminant = Math.pow(T, 2) - 4 * recordDistance;
        if (discriminant < 0) {
            return 0;
        }

        double sqrtDiscriminant = Math.sqrt(discriminant);
        double root1 = (T + sqrtDiscriminant) / 2;
        double root2 = (T - sqrtDiscriminant) / 2;

        long lowerBound = (long) Math.max(Math.ceil(root2), 1);
        long upperBound = (long) Math.min(Math.floor(root1), T - 1);

        if (upperBound >= lowerBound) {
            return upperBound - lowerBound + 1;
        } else {
            return 0;
        }
    }

}
