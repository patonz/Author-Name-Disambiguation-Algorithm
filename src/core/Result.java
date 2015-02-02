package core;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

/**
 * Created by Leonardo on 20/01/2015.
 */
public class Result {


    public double grade;
    public String description;
    public boolean check = true;
    public JsonObject output = new JsonObject();
    public JsonArray shortoutput = new JsonArray();


    public Result(double grade, String description) {
        this.grade = grade;
        this.description = description;

    }

    public Result(double grade, String description, boolean check) {
        this.grade = grade;
        this.description = description;
        this.check = check;
    }
}
