package builder.model;

import exception.SimilarTypeNotFoundException;

/**
 * Created by Leonardo on 14/01/2015.
 */
public abstract class Resource extends Builder {


    public String value;
    public String type;



    @Override
    public double Similarity(Object obj) {

        if(!(obj instanceof Resource)){
            throw new SimilarTypeNotFoundException();
        }

        return 0;
    }


    public abstract String getValue();
}
