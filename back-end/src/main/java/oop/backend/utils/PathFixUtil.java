package oop.backend.utils;

public class PathFixUtil {
    public static String fix(String path){
        final String pathFault1 ="%20";
        final String pathReplace1 = " ";
        final String pathFault2 = "target/classes";
        final String pathReplace2 = "src/main/resources";
        final String pathFault3 ="/";
        final String pathReplace3 = "";
        path = path.replaceAll(pathFault1,pathReplace1);
        path = path.replace(pathFault2,pathReplace2);
        path = path.replaceFirst(pathFault3,pathReplace3);
        return path;
    }
}
