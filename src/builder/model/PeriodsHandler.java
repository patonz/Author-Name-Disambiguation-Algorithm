package builder.model;

import exception.SimilarTypeNotFoundException;

import java.util.ArrayList;

/**
 * Created by Leonardo on 17/01/2015.
 */
public class PeriodsHandler extends BuildersHandler {

    public ArrayList<Information> periods;


    public PeriodsHandler(ArrayList periods) {
        this.periods = periods;
    }


    @Override
    public double Similarity(Object obj) {
        if (!(obj instanceof PeriodsHandler)) {
            throw new SimilarTypeNotFoundException();
        }


        return 0;
    }
}
