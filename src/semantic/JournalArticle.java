package semantic;

/**
 * Created by Leonardo on 11/01/2015.
 */
public class JournalArticle {


    public SemanticUri article;
    public PublicationYear publicationYear;
    public Title title;
    public Cites cites;

    private String[] citeslist;

    public JournalArticle() {
    }


    public String[] getCiteslist() {
        return citeslist;
    }

    public void setCiteslist(String[] citeslist) {
        this.citeslist = citeslist;
    }
}
