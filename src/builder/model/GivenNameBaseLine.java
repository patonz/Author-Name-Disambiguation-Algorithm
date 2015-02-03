package builder.model;

import Skelethon.Similarity;
import exception.SimilarTypeNotFoundException;

/**
 * Created by Leonardo on 03/02/2015.
 */
public class GivenNameBaseLine implements Similarity {


    public String type;
    public String value;

    @Override
    public Object Similarity(Object obj) {
        if (!(obj instanceof GivenNameBaseLine)) {
            throw new SimilarTypeNotFoundException();
        }
        char a = value.charAt(0);
        char b = ((GivenNameBaseLine) obj).value.charAt(0);


        return (a == b);


    }
}
