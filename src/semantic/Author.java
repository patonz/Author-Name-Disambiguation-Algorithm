package semantic;

import Skelethon.Similarity;

import java.util.ArrayList;

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

    private ArrayList<Coauthor> coauthorlist;
    private ArrayList<Creator> creatorlist;
    private ArrayList<Role> rolelist;
    private ArrayList<Relates> relateslist;
    private ArrayList<Realization> realizationlist;


    public Author() {

    }

    public ArrayList<Coauthor> getCoauthorlist() {
        return coauthorlist;
    }

    public void setCoauthorlist(ArrayList<Coauthor> coauthorlist) {
        this.coauthorlist = coauthorlist;
    }

    public ArrayList<Creator> getCreatorlist() {
        return creatorlist;
    }

    public void setCreatorlist(ArrayList<Creator> creatorlist) {
        this.creatorlist = creatorlist;
    }

    public ArrayList<Role> getRolelist() {
        return rolelist;
    }

    public void setRolelist(ArrayList<Role> rolelist) {
        this.rolelist = rolelist;
    }

    public ArrayList<Relates> getRelateslist() {
        return relateslist;
    }

    public void setRelateslist(ArrayList<Relates> relateslist) {
        this.relateslist = relateslist;
    }

    public ArrayList<Realization> getRealizationlist() {
        return realizationlist;
    }

    public void setRealizationlist(ArrayList<Realization> realizationlist) {
        this.realizationlist = realizationlist;
    }
}
