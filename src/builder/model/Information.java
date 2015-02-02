package builder.model;

import core.Levenshtein;
import exception.SimilarTypeNotFoundException;

/**
 * Created by Leonardo on 14/01/2015.
 */
public class Information extends Builder {


    public Information(String value) {
        super(value);
    }


    public Information() {

    }


    @Override
    public Object Similarity(Object obj) {

        if (!(obj instanceof Information)) {
            throw new SimilarTypeNotFoundException();
        }


        String a = this.value.replace(".", "");
        a = a.replaceAll("\\s", "");
        String b = ((Information) obj).value.replace(".", "");
        b = b.replaceAll("\\s", "");

        String max;
        String min;
        if (a.length() >= b.length()) {
            max = a;
            min = b;
        } else {
            max = b;
            min = a;
        }

        //diminuitive check
        if (max.length() > 1 && min.length() == 1) {
            if (max.charAt(0) == min.charAt(0)) {
                return 50.0; // F. of Fabio, can be the same name or not, just flip a coin.
            } else return 0.0; // A. of Fabio cant be the same name.
        }

        int distance = Levenshtein.distance(min, max);
        int miss = max.length() - min.length();
        int change = distance - miss;
        double grade = 100.0 - (100.0 / max.length() * (distance + change));


        return (grade >= 0 && grade != Double.NaN) ? grade : 0.0;
    }


}
