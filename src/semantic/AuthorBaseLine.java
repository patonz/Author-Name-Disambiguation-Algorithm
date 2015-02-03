package semantic;

import Skelethon.Similarity;
import builder.model.FamilyNameBaseLine;
import builder.model.GivenNameBaseLine;
import builder.model.PersonBaseLine;
import exception.SimilarTypeNotFoundException;
import util.MathUtils;

/**
 * Created by Leonardo on 03/02/2015.
 */
public class AuthorBaseLine implements Similarity {

    public FamilyNameBaseLine family_name;
    public GivenNameBaseLine given_name;
    public PersonBaseLine person;
    public FamilyNameBaseLine publications;
    public FamilyNameBaseLine publications_titles;
    public FamilyNameBaseLine publications_pub_years;
    public FamilyNameBaseLine publications_venues;
    public FamilyNameBaseLine publications_affiliations;
    public FamilyNameBaseLine coauthors_list;


    public AuthorBaseLine(){

    }

    //["family_name","given_name","full_name","person","publications","publications_titles","publications_pub_years","publications_venues","publications_affiliations","coauthors_list"]}


    @Override
    public Object Similarity(Object o) {
        if (!(o instanceof AuthorBaseLine)) {
            throw new SimilarTypeNotFoundException();
        }

        if ((boolean) family_name.Similarity(((AuthorBaseLine) o).family_name) && (boolean) given_name.Similarity(((AuthorBaseLine) o).given_name)) {
            return MathUtils.getRandomBoolean(); //flip a coin.
        } else return false;


    }
}
