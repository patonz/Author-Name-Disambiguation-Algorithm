package semantic;

import Skelethon.SimilarityBaseLineA;
import Skelethon.SimilarityBaseLineB;
import builder.model.FamilyNameBaseLine;
import builder.model.GivenNameBaseLine;
import builder.model.PersonBaseLine;
import exception.SimilarTypeNotFoundException;
import util.MathUtils;

/**
 * Created by Leonardo on 03/02/2015.
 */
public class AuthorBaseLine implements SimilarityBaseLineA, SimilarityBaseLineB {

    public FamilyNameBaseLine family_name;
    public GivenNameBaseLine given_name;
    public PersonBaseLine person;
    public FamilyNameBaseLine publications;
    public FamilyNameBaseLine publications_titles;
    public FamilyNameBaseLine publications_pub_years;
    public FamilyNameBaseLine publications_venues;
    public FamilyNameBaseLine publications_affiliations;
    public FamilyNameBaseLine coauthors_list;


    public AuthorBaseLine() {

    }

    //["family_name","given_name","full_name","person","publications","publications_titles","publications_pub_years","publications_venues","publications_affiliations","coauthors_list"]}


    @Override
    public Object SimilarityA(Object o, int prob) {
        if (!(o instanceof AuthorBaseLine)) {
            throw new SimilarTypeNotFoundException();
        }

        if ((boolean) family_name.SimilarityA(((AuthorBaseLine) o).family_name,0) && (boolean) given_name.SimilarityA(((AuthorBaseLine) o).given_name,0)) {
            return MathUtils.getBooleanFromProbability(prob); //flip a coin.
        } else return false;


    }

    @Override
    public Object SimilarityB(Object o, int prob) {
        if (!(o instanceof AuthorBaseLine)) {
            throw new SimilarTypeNotFoundException();
        }

        if ((boolean) family_name.SimilarityB(((AuthorBaseLine) o).family_name, 0) && (boolean) given_name.SimilarityB(((AuthorBaseLine) o).given_name, 0)) {
            return MathUtils.getBooleanFromProbability(prob); //flip a coin.
        } else return false;


    }
}
