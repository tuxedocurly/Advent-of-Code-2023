package advent2023.day4;

import java.util.*;

public class ScratchcardGame {

    static class Scratchcard {
        Set<Integer> winningNumbers;
        List<Integer> yourNumbers;

        Scratchcard(String input) {
            String[] parts = input.split(" \\| ");
            winningNumbers = new HashSet<>();
            for (String num : parts[0].split(" ")) {
                if (isNumeric(num)) {
                    winningNumbers.add(Integer.parseInt(num));
                }
            }
            yourNumbers = new ArrayList<>();
            for (String num : parts[1].split(" ")) {
                if (isNumeric(num)) {
                    yourNumbers.add(Integer.parseInt(num));
                }
            }
        }

        boolean isNumeric(String str) {
            return str.matches("-?\\d+(\\.\\d+)?");
        }

        int countMatches() {
            int matches = 0;
            for (int num : yourNumbers) {
                if (winningNumbers.contains(num)) {
                    matches++;
                }
            }
            return matches;
        }
    }

    static int processCards(List<Scratchcard> cards) {
        int totalCards = 0;
        List<Integer> counts = new ArrayList<>(Collections.nCopies(cards.size(), 1));

        for (int i = 0; i < cards.size(); i++) {
            int matches = cards.get(i).countMatches();
            for (int j = i + 1; j <= i + matches && j < cards.size(); j++) {
                counts.set(j, counts.get(j) + counts.get(i));
            }
        }

        for (int count : counts) {
            totalCards += count;
        }
        return totalCards;
    }
}
