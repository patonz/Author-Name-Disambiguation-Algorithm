package semantic;

import java.util.ArrayList;

/**
 * Created by Leonardo on 11/01/2015.
 */
public class JournalArticle {


    public SemanticUri article;
    public PublicationYear publicationYear;
    public Title title;
    public Cites cites;

    private ArrayList<Cites> citeslist;

    public JournalArticle() {
    }


    public ArrayList<Cites> getCiteslist() {
        return citeslist;
    }

    public void setCiteslist(ArrayList<Cites> citeslist) {
        this.citeslist = citeslist;
    }
}
