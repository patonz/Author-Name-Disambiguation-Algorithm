package core;

/**
 * Created by Leonardo on 20/01/2015.
 */
public class Result {


    public double grade;
    public String description;
    public boolean check = true;


    public Result(double grade, String description) {
        this.grade = grade;
        this.description = description;

    }

    public Result(double grade, String description, boolean check){
        this.grade = grade;
        this.description = description;
        this.check = check;
    }
}
