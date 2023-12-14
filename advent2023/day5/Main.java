package advent2023.day5;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        String seedInputFileLocation = "advent2023/day5/input.txt";

        SeedProcessor seedProcessor = new SeedProcessor(seedInputFileLocation);
        // part 1 solution
        System.out.println(seedProcessor.getMinSeedSingleSeeds());

        // part 2 solution
        System.out.println(seedProcessor.getMinSeedUsingRange());

    }

}
