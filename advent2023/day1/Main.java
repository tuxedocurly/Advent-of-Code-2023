package advent2023.day1;

import advent2023.day1.Day1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {

        int intAnswer = 0;
        int intAndStringAnswer = 0;
        ArrayList<String> input = new ArrayList<>();
        ArrayList<Integer> intArr = new ArrayList<>();
        ArrayList<Integer> intAndStringArr = new ArrayList<>();

        File file = new File("advent2023/day1/input.txt");
        Scanner scanner = new Scanner(file);

        while (scanner.hasNextLine()) {
            input.add(scanner.nextLine());
        }
        scanner.close();

        for (String s : input) {
            intAndStringArr.add(Day1.getCalibrationValueIntAndSpelling(s));
            intArr.add(Day1.getCalibrationValueInt(s));
        }
        for (int currentInt : intArr) {
            intAnswer += currentInt;
        }
        for (int currentInt : intAndStringArr) {
            intAndStringAnswer += currentInt;
        }

        System.out.println(input.size());

        System.out.println("Part 1: " + intAnswer);

        System.out.println("Part 2: " + intAndStringAnswer);


    }
}
