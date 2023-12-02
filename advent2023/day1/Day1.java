package advent2023.day1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day1 {

    /**
     * This method takes a string as input and returns an integer formed by the first and last digit found in the
     * string. The search for the first digit starts from the beginning of the string, and the search for the last
     * digit starts from the end of the string. The returned integer is formed by concatenating the first and last
     * digits found. This method will only search for numbers in a string where Characters.isDigit(digit) == true.
     *
     * @param s The input string to be processed.
     * @return The integer formed by the first and last digit found in the input string.
     */
    public static int getCalibrationValueInt(String s) {
        // parse the string, finding the first integer to appear from the beginning and the end
        int[] answer = new int[2];
        int finalAnswer;
        int low = 0;
        int high = s.length() - 1;
        boolean lowFound = false;
        boolean highFound = false;

        // search for the first number, starting from index 0 in the string
        while (!lowFound) {
            if (Character.isDigit(s.charAt(low))) {
                answer[0] = Character.getNumericValue(s.charAt(low));
                lowFound = true;
                //System.out.printf("Low of %s is %d\n", s, answer[0]);
            } else { low++; }
        }
        // search for the first number, starting from index s.length() - 1
        while (!highFound) {
            if (Character.isDigit(s.charAt(high))) {
                answer[1] = Character.getNumericValue(s.charAt(high));
                highFound = true;
                //System.out.printf("High of %s is %d\n\n", s, answer[1]);
            } else { high--; }
        }

        finalAnswer = answer[0] * 10 + answer[1];

        return finalAnswer;
    }

    /**
     * This method takes a string as input and returns an integer formed by the first and last digit found in the
     * string - this includes digits such as "1" as well as "one". The search for the first digit starts from the
     * beginning of the string, and the search for the last digit starts from the end of the string. The returned
     * integer is formed by concatenating the first and last digits found.
     *
     *
     * @param input The input string to be processed.
     * @return The integer formed by the first and last digit found in the input string.
     */
    public static int getCalibrationValueIntAndSpelling(String input) {
        int finalAnswer = 0;
        Map<String, Integer> numberMap = new HashMap<>();
        numberMap.put("one", 1);
        numberMap.put("two", 2);
        numberMap.put("three", 3);
        numberMap.put("four", 4);
        numberMap.put("five", 5);
        numberMap.put("six", 6);
        numberMap.put("seven", 7);
        numberMap.put("eight", 8);
        numberMap.put("nine", 9);

        for (Map.Entry<String, Integer> entry : numberMap.entrySet()) {
            String key = entry.getKey();
            int value = entry.getValue();
            // replace "seven" with "s7n". This handles the case of overlapping numbers: "eightwo", "eighthree", etc
            String replacement = key.charAt(0) +
                    String.valueOf(value) +
                    key.charAt(key.length() - 1);

            input = input.replaceAll(key, replacement);
        }

        Pattern pattern = Pattern.compile("[0-9]");
        Matcher matcher = pattern.matcher(input);

        int firstNumber = -1;
        int lastNumber = -1;
        while (matcher.find()) {
            if (firstNumber == -1) {
                firstNumber = Integer.parseInt(matcher.group());
            }
            lastNumber = Integer.parseInt(matcher.group());
        }
        finalAnswer = firstNumber * 10 + lastNumber;

        return finalAnswer;
    }

}

