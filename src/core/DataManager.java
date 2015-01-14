package core;


import com.google.gson.Gson;
import org.json.JSONObject;
import semantic.Author;
import semantic.JournalArticle;
import semantic.Key;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

/**
 * Created by Leonardo on 07/01/2015.
 */
public class DataManager {

    private static DataManager ourInstance = new DataManager();
    public Author[] author_list;
    public DataAuthor dataAuthor;
    public DataJournalArticle dataJournalArticle;
    private JSONObject jsonAuthor;
    private JSONObject jsonJournalArticle;

    private DataManager() {

    }

    public static DataManager getInstance() {
        return ourInstance;
    }

    public void loadDataFromJson(String filepath, Class c) {
        String str = new String();
        System.out.println("Reading file '" + filepath + "'");
        try {
            Scanner scan = new Scanner(new File(filepath));

            while (scan.hasNext())
                str += scan.nextLine();
            scan.close();
            System.out.println("data loaded");
            scan = null;

        } catch (IOException e) {
            System.out.println(e.toString());
        }

        JSONObject obj = new JSONObject(str);


        Gson gson = new Gson();
        if (c == DataAuthor.class) {
            jsonAuthor = obj.getJSONObject("results");
            dataAuthor = gson.fromJson(jsonAuthor.toString(), DataAuthor.class);

            valueAuthorFormatter();
        }
        if (c == DataJournalArticle.class) {
            jsonJournalArticle = obj.getJSONObject("results");
            dataJournalArticle = gson.fromJson(jsonJournalArticle.toString(), DataJournalArticle.class);

            valueJournalArticleFormatter();
        }


        gson = null;

    }


    public void valueAuthorFormatter() {

        System.out.println("generating Author list...");
        for (Author author : dataAuthor.bindings) {
            author.setCoauthorlist(SplitUsingTokenizer(author.coauthors.value, " ; "));
            author.setCreatorlist(SplitUsingTokenizer(author.creators.value, " ; "));
            author.setRolelist(SplitUsingTokenizer(author.roles.value, " ; "));
            author.setRelateslist(SplitUsingTokenizer(author.relates.value, " ; "));
            author.setRealizationlist(SplitUsingTokenizer(author.realizations.value, " ; "));
        }


        System.out.println("done");


    }

    public void valueJournalArticleFormatter() {

        System.out.println("generating JournalArticle list...");
        for (JournalArticle journalArticle : dataJournalArticle.bindings) {
            journalArticle.setCiteslist(SplitUsingTokenizer(journalArticle.cites.value, " ; "));

        }


        System.out.println("done");


    }

    public ArrayList SplitUsingTokenizer(String subject, String delimiters) {

        StringTokenizer strTkn = new StringTokenizer(subject, delimiters);
        ArrayList arrLis = new ArrayList(subject.length());

        while (strTkn.hasMoreTokens()) {
            arrLis.add(new Key(strTkn.nextToken()) {
            });
        }

        return arrLis;
    }

    public void clean(){

        this.jsonAuthor = null;
        this.jsonJournalArticle = null;
        this.dataAuthor = null;
        this.dataJournalArticle = null;
        System.gc();
    }


}
