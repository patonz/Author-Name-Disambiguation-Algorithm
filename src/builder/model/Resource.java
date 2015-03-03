package builder.model;

import util.Levenshtein;
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
    public Object Similarity(Object obj) {

        if(!(obj instanceof Resource)){
            throw new SimilarTypeNotFoundException();
        }



        return Levenshtein.distance(this.value, ((Resource) obj).value) == 0 ? 1 : 0;
    }



}
