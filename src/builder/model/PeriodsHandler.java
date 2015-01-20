package builder.model;

import exception.SimilarTypeNotFoundException;

import java.util.ArrayList;

/**
 * Created by Leonardo on 17/01/2015.
 */
public class PeriodsHandler extends BuildersHandler {

    public ArrayList<Period> periods;


    public PeriodsHandler(ArrayList periods, Double weight) {
        this.periods = periods;
        this.weight = weight;
    }


    @Override
    public Object Similarity(Object obj) {
        if (!(obj instanceof PeriodsHandler)) {
            throw new SimilarTypeNotFoundException();
        }


        return 0.0;
    }
}
