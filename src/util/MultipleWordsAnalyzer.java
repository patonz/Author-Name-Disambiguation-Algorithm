package util;

import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * Created by Leonardo on 02/03/2015.
 */
public class MultipleWordsAnalyzer {


    public static int mwa(String valueA, String valueB) {
        ArrayList<String> A = SplitUsingTokenizer(WordsUtil.removeDots(valueA), " ");
        ArrayList<String> B = SplitUsingTokenizer(WordsUtil.removeDots(valueB), " ");

        int count = 0;

        for (int i = 0; i < A.size(); i++) {
            for (int j = 0; j < B.size(); j++) {

                if (A.get(i).equals(B.get(j)) || Diminutive.isDiminutive(getMaxString(A.get(i), B.get(j)), getMinString(A.get(i), B.get(j)))) {
                    count++;
                }


            }
        }

        return count;


    }


    public static ArrayList<String> SplitUsingTokenizer(String value, String delimiter) {

        StringTokenizer strTkn = new StringTokenizer(value, delimiter);
        ArrayList<String> arrLis = new ArrayList<>();


        while (strTkn.hasMoreTokens()) {


            arrLis.add(strTkn.nextToken());


        }


        return arrLis;
    }


    public static String getMaxString(String s1, String s2) {


        return (s1.length() >= s2.length()) ? s1 : s2;

    }

    public static String getMinString(String s1, String s2) {


        return (s1.length() < s2.length()) ? s1 : s2;

    }
}
