package oop.backend.utils.fix;

public class QueryFixUtil {
    public static String fix(String selection) {
        final String query1 = "#";
        final String queryReplace1 = "%23";
        final String query2 = " ";
        final String queryReplace2 = "%20";
        selection = selection.replaceAll(query1, queryReplace1);
        selection = selection.replaceAll(query2, queryReplace2);
        return selection;
    }
}
