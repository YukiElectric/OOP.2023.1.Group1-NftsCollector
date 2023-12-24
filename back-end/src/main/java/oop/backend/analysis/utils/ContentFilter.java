package oop.backend.analysis.utils;

import oop.backend.analysis.dtos.Positivity;

import java.util.Arrays;



public class ContentFilter {

    private static final String LINE_SEPARATOR = System.getProperty("line.separator");

    public static Positivity countRating(String collection, String response) {
        int positiveCount = countOccurrence("positive", response);
        int negativeCount = countOccurrence("negative", response);
        int neutralCount = countOccurrence("neutral", response);

        return new Positivity(collection, positiveCount, negativeCount, neutralCount);
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

    public static void main(String[] args) {
        String collection = "Collection1";
        String response = "(1.) Neutral\n" +
                "(2.) Negative\n" +
                "(3.) Positive\n" +
                "(4.) Neutral\n" +
                "(5.) Neutral\n" +
                "(6.) Neutral\n" +
                "(7.) Neutral\n" +
                "(8.) Neutral\n" +
                "(9.) Positive\n" +
                "(10.) Positive";

        Positivity result = countRating(collection, response);

        System.out.println("Collection: " + result.getCollection());
        System.out.println("Positive count: " + result.getPositive());
        System.out.println("Negative count: " + result.getNegative());
        System.out.println("Neutral count: " + result.getNeutral());
        String str = "line1\nline2\nline3";
        String eol = System.getProperty("line.separator");
        System.out.printf(Arrays.toString(str.split(eol)));
        String t = "Test remove line   s eparator \n !@$#%^^&$#)%(#*$#&^*!&@　預かって";
         t ="(1.)Some examples of popular on-chain NFTs: - CryptoPunks: http://cryptopunks.app - Bored Ape Yacht Club: http://boredapeyachtclub.com - CryptoKitties: http://cryptokitties.co(2.)Pink Ape Adventures is a coloring book that follows two legendary @BoredApeYC friends, Jason the Ape and Grateful Ape, as they trek across the world to 12 unique locations. This is the first book in a series of coloring books featuring members of the Bored Ape Yacht Club IP.(3.)A BATHING APE® Bored Ape Yacht Club!! #BAPE #スニーカー #キックス(4.)this is probably the best investment since Bored Ape Yacht Club So @MKBHD just awarded the Solana Saga Phone the \"Worst Phone of the Year\". This phone is probably the best investment anyone could make in a phone, honestly. But I'm not knocking him, he's judging it from a software standpoint. Thoughts? @0xGumshoe @0xjaypeg @blknoiz06 $SOL(5.)2/8 Did you know that NFTs have been around for 6 years now? From CryptoKitties to Bored Ape Yacht Club, NFTs have shown incredible resilience and scalability. We believe that NFTs will create a market worth trillions of dollars in the future! #NFTMarket(6.)The next bored ape yacht club is minting right now and nobody even knows it.(7.)Who here’s like me and thinks way more about the Bored Ape Yacht Club than about the Roman Empire?(8.)Bored Ape Yacht Club - 800 ETH ($1,840,000 USD) - Mint Price: 0.08 ETH (eth was ~$2300 at the time) - Launched: April 2021 (8/10)(9.)Platforma NFT Trader a reusit sa recupereze NFT-urile furate pe 16 Decembrie cu un bounty de 267K $. Printre NFT-urile furate initial erau cateva Bored Ape Yacht Club si Mutant Ape Yacht Club, iar valoarea lor era estimata la aproximativ 3 milioane de dolari.(10.)The Bored Ape Yacht Club, along with their native token $APE, is poised to become the next billion-dollar industry, set to play a significant role in the Metaverse. Keep an eye on #APEusdt #BTC #ETH #BitcoinETF as they navigate this exciting space.";
        System.out.println(removeSpecialCharacters(t));
    }
}





