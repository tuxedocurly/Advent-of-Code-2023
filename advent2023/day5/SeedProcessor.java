package advent2023.day5;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class SeedProcessor {

    private static ArrayList<Long> seeds;
    private static ArrayList<long[]> seedToSoilList, soilToFertilizerList, fertilizerToWaterList, waterToLightList,
            lightToTemperatureList, temperatureToHumidityList, humidityToLocationList;
    private final long minSeedSingleSeeds;
    private final long minSeedUsingRange;


    /**
     * Constructor for the SeedProcessor class.
     * Processes the seed input file and calculates the minimum seed using single seeds and seed ranges.
     *
     * @param inputFileLocation The location of the input file containing seed data.
     * @throws IOException If an I/O error occurs.
     */
    public SeedProcessor(String inputFileLocation) throws IOException {
        seeds = new ArrayList<>();
        seedToSoilList = new ArrayList<>();
        soilToFertilizerList = new ArrayList<>();
        fertilizerToWaterList = new ArrayList<>();
        waterToLightList = new ArrayList<>();
        lightToTemperatureList = new ArrayList<>();
        temperatureToHumidityList = new ArrayList<>();
        humidityToLocationList = new ArrayList<>();
        processSeedInput(inputFileLocation);
        // TODO: cleanup the methods requiring passing maps. This is no longer required with the new class design.
        minSeedSingleSeeds = minimumSeedUsingSingleSeeds(seeds, seedToSoilList, soilToFertilizerList, fertilizerToWaterList,
                waterToLightList, lightToTemperatureList, temperatureToHumidityList, humidityToLocationList);
        // TODO: improve the efficiency of this (and associated) methods). Current time complexity is too high.
        minSeedUsingRange = minimumSeedUsingSeedRanges(seeds);

    }

    /**
     * Processes the seed input file and stores the data into the appropriate data structures.
     *
     * @param fileLocation The location of the input file.
     * @throws IOException If an I/O error occurs.
     */
    void processSeedInput(String fileLocation) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(fileLocation));

        String line;
        while ((line = reader.readLine()) != null) {
            if (line.startsWith("seeds:")) {
                String[] seedStrings = line.substring(7).split(" ");
                for (String seed : seedStrings) {
                    seeds.add(Long.parseLong(seed));
                }
            }
            if (line.startsWith("seed-to-soil map:")) {
                while ((line = reader.readLine()) != null && !line.isEmpty()) {
                    addRuleToList(line, seedToSoilList);
                }
            } else if (line.startsWith("soil-to-fertilizer map:")) {
                while ((line = reader.readLine()) != null && !line.isEmpty()) {
                    addRuleToList(line, soilToFertilizerList);
                }
            } else if (line.startsWith("fertilizer-to-water map:")) {
                while ((line = reader.readLine()) != null && !line.isEmpty()) {
                    addRuleToList(line, fertilizerToWaterList);
                }
            } else if (line.startsWith("water-to-light map:")) {
                while ((line = reader.readLine()) != null && !line.isEmpty()) {
                    addRuleToList(line, waterToLightList);
                }
            } else if (line.startsWith("light-to-temperature map:")) {
                while ((line = reader.readLine()) != null && !line.isEmpty()) {
                    addRuleToList(line, lightToTemperatureList);
                }
            } else if (line.startsWith("temperature-to-humidity map:")) {
                while ((line = reader.readLine()) != null && !line.isEmpty()) {
                    addRuleToList(line, temperatureToHumidityList);
                }
            } else if (line.startsWith("humidity-to-location map:")) {
                while ((line = reader.readLine()) != null && !line.isEmpty()) {
                    addRuleToList(line, humidityToLocationList);
                }
            }
        }
        reader.close();
    }

    /**
     * Adds a rule to the given rule list. These rules represent a cypher mapping from a source number to a destination
     * number, and for how long that series applies (i.e. the range).
     *
     * @param line     The line from the input file containing the rule.
     * @param ruleList The list to add the rule to.
     */
    void addRuleToList(String line, ArrayList<long[]> ruleList) {
        long[] rule = new long[3];
        String[] parts = line.split(" ");
        rule[0] = Long.parseLong(parts[0]); // destination start
        rule[1] = Long.parseLong(parts[1]); // source start
        rule[2] = Long.parseLong(parts[2]); // range length
        ruleList.add(rule);
    }

    /**
     * Converts a seed to its destination using the given rule list.
     *
     * @param seed     The seed to convert.
     * @param ruleList The list of rules to use for the conversion.
     * @return The destination of the seed.
     */
    long seedToDestination(long seed, ArrayList<long[]> ruleList) {
        long destinationStart;
        long sourceStart;
        long rangeLength;

        for (long[] rule : ruleList) {
            destinationStart = rule[0];
            sourceStart = rule[1];
            rangeLength = rule[2];

            if (seed >= sourceStart && seed < sourceStart + rangeLength) {
                seed = destinationStart + (seed - sourceStart);
                break;
            }
        }
        return seed;
    }

    /**
     * Processes a seed through all maps.
     *
     * @param seed                      The seed to process.
     * @param seedToSoilList            The seed-to-soil map.
     * @param soilToFertilizerList      The soil-to-fertilizer map.
     * @param fertilizerToWaterList     The fertilizer-to-water map.
     * @param waterToLightList          The water-to-light map.
     * @param lightToTemperatureList    The light-to-temperature map.
     * @param temperatureToHumidityList The temperature-to-humidity map.
     * @param humidityToLocationList    The humidity-to-location map.
     * @return The final destination of the seed.
     */
    //TODO: refactor the passing of the maps to this method. It is no longer needed with the current class design.
    long processSeedThroughAllMaps(long seed,
                                   ArrayList<long[]> seedToSoilList,
                                   ArrayList<long[]> soilToFertilizerList,
                                   ArrayList<long[]> fertilizerToWaterList,
                                   ArrayList<long[]> waterToLightList,
                                   ArrayList<long[]> lightToTemperatureList,
                                   ArrayList<long[]> temperatureToHumidityList,
                                   ArrayList<long[]> humidityToLocationList) {
        seed = seedToDestination(seed, seedToSoilList);
        seed = seedToDestination(seed, soilToFertilizerList);
        seed = seedToDestination(seed, fertilizerToWaterList);
        seed = seedToDestination(seed, waterToLightList);
        seed = seedToDestination(seed, lightToTemperatureList);
        seed = seedToDestination(seed, temperatureToHumidityList);
        seed = seedToDestination(seed, humidityToLocationList);
        return seed;
    }


    /**
     * Finds the minimum seed using single seeds.
     *
     * @param seeds                     The list of seeds.
     * @param seedToSoilList            The seed-to-soil map.
     * @param soilToFertilizerList      The soil-to-fertilizer map.
     * @param fertilizerToWaterList     The fertilizer-to-water map.
     * @param waterToLightList          The water-to-light map.
     * @param lightToTemperatureList    The light-to-temperature map.
     * @param temperatureToHumidityList The temperature-to-humidity map.
     * @param humidityToLocationList    The humidity-to-location map.
     * @return The minimum seed.
     */
    //TODO: refactor the passing of the maps to this method. It is no longer needed with the current class design.
    long minimumSeedUsingSingleSeeds(ArrayList<Long> seeds, ArrayList<long[]> seedToSoilList,
                                     ArrayList<long[]> soilToFertilizerList, ArrayList<long[]> fertilizerToWaterList,
                                     ArrayList<long[]> waterToLightList, ArrayList<long[]> lightToTemperatureList,
                                     ArrayList<long[]> temperatureToHumidityList, ArrayList<long[]> humidityToLocationList) {
        if (seeds.isEmpty()) {
            return 0;
        }
        long minSeed = seeds.getFirst();
        for (long seed : seeds) {
            seed = processSeedThroughAllMaps(seed, seedToSoilList, soilToFertilizerList, fertilizerToWaterList,
                    waterToLightList, lightToTemperatureList, temperatureToHumidityList, humidityToLocationList);
            if (seed < minSeed) {
                minSeed = seed;
            }
        }
        return minSeed;
    }

    /**
     * Finds the minimum seed using seed ranges.
     *
     * @param seeds The list of seeds.
     * @return The minimum seed.
     */
    long minimumSeedUsingSeedRanges(ArrayList<Long> seeds) {
        if (seeds.isEmpty()) {
            return 0;
        }

        // grab all the range pairs from the seeds
        ArrayList<long[]> seedRanges = transformSeedsIntoRanges(seeds);

        long minSeed = Long.MAX_VALUE;

        for (long[] range : seedRanges) {
            minSeed = Math.min(minimumSeedInRange(range), minSeed);
        }
        return minSeed;
    }

    /**
     * Transforms a list of seeds into a list of seed ranges.
     *
     * @param seeds The list of seeds.
     * @return The list of seed ranges.
     */
    ArrayList<long[]> transformSeedsIntoRanges(ArrayList<Long> seeds) {
        // turn seeds into an ArrayList of seed ranges, where range[0] is the start of the range and
        // range[1] is the length of the range

        ArrayList<long[]> seedRanges = new ArrayList<>();

        if (seeds.isEmpty()) {
            return seedRanges;
        }
        for (int i = 0; i < seeds.size() - 1; i += 2) {
            long[] range = new long[2];
            range[0] = seeds.get(i);
            range[1] = seeds.get(i + 1);
            seedRanges.add(range);
        }
        return seedRanges;
    }

    /**
     * Finds the minimum seed in a range.
     *
     * @param range The range of seeds.
     * @return The minimum seed in the range.
     */
    long minimumSeedInRange(long[] range) {
        // process all numbers in the range through minimumSeed() and return the smallest result
        long minSeed = Long.MAX_VALUE;
        for (long i = range[0]; i < range[0] + range[1]; i++) {
            long currentSeed = processSeedThroughAllMaps(i, seedToSoilList, soilToFertilizerList, fertilizerToWaterList,
                    waterToLightList, lightToTemperatureList, temperatureToHumidityList, humidityToLocationList);
            minSeed = Math.min(currentSeed, minSeed);

        }
        return minSeed;

    }

    /**
     * GETTERS
     */
    public long getMinSeedSingleSeeds() {
        return minSeedSingleSeeds;
    }

    public long getMinSeedUsingRange() {
        return minSeedUsingRange;
    }

}
