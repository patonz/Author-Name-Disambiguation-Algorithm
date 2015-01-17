package builder.model;

import exception.SimilarTypeNotFoundException;

/**
 * Created by Leonardo on 14/01/2015.
 */
public class Resource extends Builder {


    public Resource(String value){
        super(value);
    }


    public Resource(){

    }


    @Override
    public double Similarity(Object obj) {

        if(!(obj instanceof Resource)){
            throw new SimilarTypeNotFoundException();
        }

        return 0;
    }



}
