package advent2023.day4;

import advent2023.utilities.FileOperations;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        File file = new File("advent2023/day4/input.txt");
        ArrayList<String> input = FileOperations.fileToList(file);

        List<ScratchcardGame.Scratchcard> cards = new ArrayList<>();
        for (String line : input) {
            cards.add(new ScratchcardGame.Scratchcard(line));
        }

        int totalCards = ScratchcardGame.processCards(cards);
        System.out.println("Total scratchcards: " + totalCards);
    }
}
