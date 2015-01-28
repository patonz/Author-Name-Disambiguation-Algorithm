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
        a = a.replaceAll("\\s","");
        String b = ((Information) obj).value.replace(".", "");
        b = b.replaceAll("\\s","");

        int distance = Levenshtein.distance(a, b);
        int miss = Math.max(a.length(), b.length()) - Math.min(a.length(), b.length());
        int change = distance - miss;
        double grade = 100.0 - (100.0 / (Math.max(a.length(), b.length())) * distance + change);


        return (grade >= 0 && grade != Double.NaN) ? grade : 0.0;
    }


    public String method(String str) {
        if (this.value.charAt(this.value.length() - 1) == '.') {

        }
        if (str.length() > 0 && str.charAt(str.length() - 1) == 'x') {
            str = str.substring(0, str.length() - 1);
        }
        return str;
    }


}
