package oop.backend.utils.fix;

public class QueryFixUtil {
    public static String fix(String selection) {
        final String prefix = "#";
        final String prefixReplace = "%23";
        final String space = " ";
        final String spaceReplace = "%20";
        selection = selection.replaceAll(prefix, prefixReplace);
        selection = selection.replaceAll(space, spaceReplace);
        return selection;
    }
}
