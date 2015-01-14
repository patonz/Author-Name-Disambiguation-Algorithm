package builder.model;

import exception.SimilarTypeNotFoundException;

import java.util.ArrayList;

/**
 * Created by Leonardo on 14/01/2015.
 */
public abstract class ResourcesHandler extends BuildersHandler {

    @Override
    public double Similarity(Object obj) {
        if(!(obj instanceof ResourcesHandler)){
            throw new SimilarTypeNotFoundException();
        }


        return 0;
    }


    public abstract ArrayList getValue();
}
