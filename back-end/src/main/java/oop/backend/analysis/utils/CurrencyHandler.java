package oop.backend.analysis.utils;


import lombok.Getter;

/** Tiện ích dùng để chuyển giá trị nft từ string sang double **/
public class CurrencyHandler {

    @Getter
    private static class NFTInfo {
        private final String currency;
        private final String value;
        private final double multiplier;

        public NFTInfo(String currency, String value, double multiplier) {
            this.currency = currency;
            this.value = value;
            this.multiplier = multiplier;
        }
    }

    private static NFTInfo parseNFTValue(String nftValue) {
        String currency = "";
        String value = "";
        double multiplier = 1.0;


        if (nftValue.contains("ETH")) {
            value = extractValue(nftValue);
            currency = "ETH";
        } else if (nftValue.contains("BNB")) {
            value = extractValue(nftValue);
            currency = "BNB";
        } else if (nftValue.contains("USD")) {
            value = extractValue(nftValue);
            currency = "USD";
        } else if (nftValue.contains("$")) {
            value = extractValue(nftValue.substring(1));
            currency = "USD";
        }
        if (nftValue.contains("M")) {
            multiplier = 1e6;
        } else if (nftValue.contains("K")) {
            multiplier = 1e3;
        }

        return new NFTInfo(currency, value, multiplier);
    }

    private static String extractValue(String nftValue) {
        StringBuilder numericPart = new StringBuilder();
        for (char c : nftValue.toCharArray()) {
            if (Character.isDigit(c) || c == '.' || c == ',') {
                numericPart.append(c);
            }
        }
        return numericPart.toString();
    }


    public static double getValue(String value) {
        NFTInfo nftInfo = parseNFTValue(value);
        String cleanedValue = nftInfo.getValue().replaceAll(",", "");
        return Double.parseDouble(cleanedValue) * nftInfo.getMultiplier();
    }
    public static String getCurrency(String value) {
        NFTInfo nftInfo = parseNFTValue(value);
        return nftInfo.getCurrency();
    }

/*test*/
//    public static void main(String[] args) {
//        // Sample
//        String[] nftValues = {
//                "1,664   ETH",
//                "2.60   BNB",
//                "$1,349.00",
//                "2.9M   ETH",
//                "944.1K   ETH",
//                "1,172   ETH"
//        };
//        for (String nftValue : nftValues) {
//            NFTInfo nftInfo = parseNFTValue(nftValue);
//            System.out.println("Original: " + nftValue);
//            System.out.println("Currency: " + nftInfo.getCurrency());
//            System.out.println("Value: " + getValue(nftValue));
//            System.out.println();
//        }
//    }
}
