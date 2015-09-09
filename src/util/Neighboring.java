package util;

/**
 * Created by Leonardo on 06/02/2015.
 */
public class Neighboring {


    static String charsENG = "1234567890-=qwertyuiop[]asdfghjkl;'\\\\zxcvbnm,./";
    static String charsITA = "1234567890'ìqwertyuiopè+asdfghjklòàù<zxcvbnm,.-";
    static String charsDEU = "1234567890ß´qwertzuiopü+asdfghjklöä#<yxcvbnm,.-";


    public static String getNeighboringKeys(char key) {
        StringBuffer result = new StringBuffer();
        for (char c : charsENG.toCharArray()) {
            if (c != key && distance(c, key) < 2) {
                result.append(c);
            }
        }
        return result.toString();
    }

    public static double distance(char c1, char c2) {
        return Math.sqrt(Math.pow(colOf(c2) - colOf(c1), 2) + Math.pow(rowOf(c2) - rowOf(c1), 2));
    }

    public static int rowOf(char c) {
        return charsENG.indexOf(c) / 12;
    }

    public static int colOf(char c) {
        return charsENG.indexOf(c) % 12;
    }


    public static boolean isNeighboringKey(char rightkey, char wrongkey) {

        if (getNeighboringKeys(wrongkey).contains("" + rightkey))
            return true;
        else
            return false;
    }
}
