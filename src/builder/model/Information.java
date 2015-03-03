package builder.model;

import util.Levenshtein;
import exception.SimilarTypeNotFoundException;
import util.MultipleWordsAnalyzer;

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
        String a = this.value;
        String b = ((Information) obj).value;


        if(option.mwa){
            int words = MultipleWordsAnalyzer.mwa(a,b);
            if(words>0){
                return 50.0;

            }else return 0.0;
        }


        if (option.dots) {
            a = a.replace(".", "");
            b = b.replace(".", "");
        }




        if (option.spaces) {
            a = a.replaceAll("\\s", "");
            b = b.replaceAll("\\s", "");
        }

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
                return 50.0; // F. of Fabio, can be the same name or not.
            } else return 0.0; // A. of Fabio cant be the same name.
        }

        int distance = Levenshtein.distance(min, max);
        int miss = max.length() - min.length();
        int change = distance - miss;
        double grade = 100.0 - (100.0 / max.length() * (distance + change));


        return (grade >= 0 && grade != Double.NaN) ? grade : 0.0;
    }


}
