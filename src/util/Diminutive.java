package util;

/**
 * Created by Leonardo on 02/03/2015.
 */
public class Diminutive {


    public static boolean isDiminutive(String max, String min) {


        if (max.length() > 1 && min.length() == 1)

        {
            if (max.charAt(0) == min.charAt(0)) {
                return true; // F. of Fabio, can be the same name or not.
            } else return false; // A. of Fabio cant be the same name.
        }

        return false;
    }
}

