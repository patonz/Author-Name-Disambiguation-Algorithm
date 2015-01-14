package semantic;

import Skelethon.Similarity;

/**
 * Created by Leonardo on 07/01/2015.
 */
public class Author implements Similarity {

    @Override
    public double Similarity(Key obj) {
        return 0;
    }

    public SemanticUri person;
    public Coauthor coauthors;
    public Label label;
    public FamilyName familyName;
    public GivenName givenName;
    public Creator creators;
    public Role roles;
    public Relates relates;
    public Realization realizations;

    private String[] coauthorlist;
    private String[] creatorlist;
    private String[] rolelist;
    private String[] relateslist;
    private String[] realizationlist;


    public Author() {

    }

    public String[] getCoauthorlist() {
        return coauthorlist;
    }

    public void setCoauthorlist(String[] coauthorlist) {
        this.coauthorlist = coauthorlist;
    }

    public String[] getCreatorlist() {
        return creatorlist;
    }

    public void setCreatorlist(String[] creatorlist) {
        this.creatorlist = creatorlist;
    }

    public String[] getRolelist() {
        return rolelist;
    }

    public void setRolelist(String[] rolelist) {
        this.rolelist = rolelist;
    }

    public String[] getRelateslist() {
        return relateslist;
    }

    public void setRelateslist(String[] relateslist) {
        this.relateslist = relateslist;
    }

    public String[] getRealizationlist() {
        return realizationlist;
    }

    public void setRealizationlist(String[] realizationlist) {
        this.realizationlist = realizationlist;
    }
}
