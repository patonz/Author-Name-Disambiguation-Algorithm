package builder.model;

import exception.SimilarTypeNotFoundException;

import java.util.Date;

/**
 * Created by Leonardo on 14/01/2015.
 */
public abstract class Period extends Builder {



    @Override
    public double Similarity(Object obj) {

        if(!(obj instanceof Period)){
            throw new SimilarTypeNotFoundException();
        }

        return 0;
    }


    public abstract Date getValue();
}
