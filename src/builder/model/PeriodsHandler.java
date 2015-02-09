package builder.model;

import configuration.Option;
import exception.SimilarTypeNotFoundException;

import java.util.ArrayList;

/**
 * Created by Leonardo on 17/01/2015.
 */
public class PeriodsHandler extends BuildersHandler {

    public ArrayList<Period> periods;


    public PeriodsHandler(ArrayList periods) {
        this.periods = periods;

    }

    @Override
    public void setOption(Option opt) {
        for(Period per : periods){
            per.option = opt;
        }
    }

    @Override
    public Object Similarity(Object obj) {
        if (!(obj instanceof PeriodsHandler)) {
            throw new SimilarTypeNotFoundException();
        }


        return 0.0;
    }
}
