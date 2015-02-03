package builder.model;

import exception.SimilarTypeNotFoundException;

/**
 * Created by Leonardo on 03/02/2015.
 */
public class FamilyNameBaseLine extends BuildersHandler {

    public String type;
    public String value;


    @Override
    public Object Similarity(Object obj) {
        if (!(obj instanceof FamilyNameBaseLine)) {
            throw new SimilarTypeNotFoundException();
        }


        return this.value.equals(((FamilyNameBaseLine) obj).value) ? true : false;
    }
}
