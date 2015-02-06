package util;

/**
 * Created by Leonardo on 05/02/2015.
 */
public class Monomial {

    public double variable;
    public double constant;


    public Monomial(double variable, double constant) {
        this.variable = variable;
        this.constant = constant;
    }

    public double getProduct(){
        return variable * constant;
    }

}
