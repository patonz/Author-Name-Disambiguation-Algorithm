package builder.model;

import Skelethon.SimilarityBaseLineA;
import Skelethon.SimilarityBaseLineB;
import exception.SimilarTypeNotFoundException;

/**
 * Created by Leonardo on 03/02/2015.
 */
public class GivenNameBaseLine implements SimilarityBaseLineA, SimilarityBaseLineB {


    public String type;
    public String value;

    @Override
    public Object SimilarityA(Object obj, int prob) {
        if (!(obj instanceof GivenNameBaseLine)) {
            throw new SimilarTypeNotFoundException();
        }
        char a = value.charAt(0);
        char b = ((GivenNameBaseLine) obj).value.charAt(0);


        return (a == b);


    }

    public Object SimilarityB(Object obj, int prob) {
        if (!(obj instanceof GivenNameBaseLine)) {
            throw new SimilarTypeNotFoundException();
        }
        String arr[] = this.value.split(" ", 2);
        String a = arr[0];
        a = a.replace(".", "");

        String arrb[] = ((GivenNameBaseLine) obj).value.split(" ", 2);
        String b = arrb[0];
        b = b.replace(".", "");


        return (a.equals(b));


    }
}
