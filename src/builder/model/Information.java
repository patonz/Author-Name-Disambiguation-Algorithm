package builder.model;

import exception.SimilarTypeNotFoundException;

/**
 * Created by Leonardo on 14/01/2015.
 */
public abstract class Information extends Builder {


    @Override
    public double Similarity(Object obj) {

        if(!(obj instanceof Information)){
            throw new SimilarTypeNotFoundException();
        }

        return 0;
    }

    public abstract String getValue();

}
