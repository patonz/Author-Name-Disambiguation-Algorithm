package util;

import java.math.BigInteger;
import java.util.ArrayList;
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


    public static boolean getBooleanFromProbability(int probability) {


        Random rand = new Random();
        int totalSum = 100;
        boolean[] check = new boolean[]{true, false};
        int[] prob = new int[]{(100 - probability), probability};

        int index = rand.nextInt(totalSum);
        int sum = 0;
        int i = 0;
        while (sum < index) {
            sum = sum + prob[i++];
        }


        return check[Math.max(0, i - 1)];
    }

    public static boolean getRandomBoolean() {
        Random rnd = new Random();
        return rnd.nextBoolean();
    }


    public static double getDynamicWeight(ArrayList<Monomial> polinomial, Monomial x, double result) {

        double numerator = 0.0;
        double denominator = 0.0;


        for (Monomial monomial : polinomial) {
          //  System.out.println(monomial.constant + " " + monomial.variable);
            numerator += monomial.getProduct();
            denominator += monomial.variable;
        }
       // System.out.println("constant x :" + x.constant);
       // System.out.println("result: " + (numerator - (result * denominator)) / (result - x.constant));
        double weight = (numerator - (result * denominator)) / (result - 0);
        return (weight >= 0.0)? weight : 0.0;


    }

}
