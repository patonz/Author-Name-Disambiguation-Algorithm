package builder.model;

import core.Levenshtein;
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

        Levenshtein.distance(this.value, ((Resource) obj).value);

        return Levenshtein.distance(this.value, ((Resource) obj).value) == 0 ? 1 : 0;
    }



}
