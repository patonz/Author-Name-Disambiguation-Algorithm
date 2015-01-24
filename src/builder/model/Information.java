package builder.model;

import core.ElaborateManager;
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
    public Object Similarity(Object obj) {

        if(!(obj instanceof Information)){
            throw new SimilarTypeNotFoundException();
        }



        int distance = Levenshtein.distance(this.value, ((Information) obj).value);
        int miss = Math.max(this.value.length(),((Information) obj).value.length())- Math.min(this.value.length(), ((Information) obj).value.length());
              int change = distance - miss;
        double grade =  100.0 - (100.0 / (Math.max(this.value.length(), ((Information) obj).value.length()) )* distance + change);






        return (grade >= 0 && grade != Double.NaN) ? grade : 0.0;
    }



}
