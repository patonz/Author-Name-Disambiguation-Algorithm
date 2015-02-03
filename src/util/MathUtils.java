package util;

import java.math.BigInteger;
import java.util.Random;

/**
 * Created by Leonardo on 02/02/2015.
 */
public class MathUtils {


    public static int factorial(int number) {

        int n = number;
        BigInteger inc = new BigInteger("1");
        BigInteger fact = new BigInteger("1");


        for (int c = 1; c <= n; c++) {
            fact = fact.multiply(inc);
            inc = inc.add(BigInteger.ONE);
        }
        return fact.intValue();
    }


    public static int binomial(int n, int k) {
        return factorial(n) / (factorial(n - k) * factorial(k));
    }


    public static boolean getRandomBoolean() {
        Random rnd = new Random();
        return rnd.nextBoolean();
    }

}
