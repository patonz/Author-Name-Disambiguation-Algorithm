package core;

import builder.model.Builder;
import semantic.Key;

/**
 * Created by Leonardo on 14/01/2015.
 */
public class Combination {

    public Builder A;
    public Builder B;

    private double weight;
    private double grade;
    private double finalgrade;

    public Combination(Builder a, Builder b, double weight){
        A = a;
        B = b;
        this.weight = weight;
        calculateFinalGrade();
    }



    private void calculateFinalGrade(){

        this.grade = (double)A.Similarity(B);
        this.finalgrade = this.grade * this.weight;
    }

    public double getFinalgrade() {
        return finalgrade;
    }

    public double getWeight() {
        return weight;
    }

    public double getGrade() {
        return grade;
    }
}
