package builder.model;

import core.Levenshtein;
import exception.SimilarTypeNotFoundException;

/**
 * Created by Leonardo on 14/01/2015.
 */
public class Information extends Builder {



    public Information(String value){
        super(value);
    }


    public Information(){

    }



    @Override
    public double Similarity(Object obj) {

        if(!(obj instanceof Information)){
            throw new SimilarTypeNotFoundException();
        }







        return Levenshtein.distance(this.value, ((Information) obj).value);
    }



}
