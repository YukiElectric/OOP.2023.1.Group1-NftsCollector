package oop.backend.analysis.utils;

import oop.backend.analysis.positivity.PositivityData;


/* Tiện ích phục vụ cho */
public class ContentFilter {

    private static final String LINE_SEPARATOR = System.getProperty("line.separator");

    public static PositivityData countRating(String collection, String response) {
        int positiveCount = countOccurrence("positive", response);
        int negativeCount = countOccurrence("negative", response);
        int neutralCount = countOccurrence("neutral", response);

        return new PositivityData(collection, positiveCount, negativeCount, neutralCount);
    }

    private static int countOccurrence(String word, String response) {
        int count = 0;
        String[] lines = response.split(LINE_SEPARATOR);
        for (String line : lines) {
            count += countWords(line.toLowerCase(), word.toLowerCase());
        }
        return count;
    }

    public static String removeSpecialCharacters(String response) {
        String[] lines = response.split(LINE_SEPARATOR);
        StringBuilder result = new StringBuilder();
        for (String line : lines) {
            result.append(line);
        }
        return result.toString().replaceAll("[^a-zA-Z0-9#%@!$\s]", "");
    }

    private static int countWords(String line, String word) {
        int count = 0;
        int index = line.indexOf(word);
        while (index != -1) {
            count++;
            index = line.indexOf(word, index + word.length());
        }
        return count;
    }
}





