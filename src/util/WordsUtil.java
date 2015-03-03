package util;

/**
 * Created by Leonardo on 02/03/2015.
 */
public class WordsUtil {


    public static String removeDots(String a) {
        a = a.replace(".", "");
        return a;
    }

    public static String removeSpaces(String a) {

        a = a.replaceAll("\\s", "");

        return a;
    }
}
