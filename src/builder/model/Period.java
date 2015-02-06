package builder.model;

import exception.SimilarTypeNotFoundException;

/**
 * Created by Leonardo on 14/01/2015.
 */
public class Period extends Builder {


    public Period(String value) {
        super(value);
    }


    public Period() {

    }

    @Override
    public Object Similarity(Object obj) {

        if (!(obj instanceof Period)) {
            throw new SimilarTypeNotFoundException();
        }

        return 0.0;
    }


}
