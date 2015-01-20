package core;


import builder.BuilderManager;
import builder.model.*;
import com.google.gson.Gson;
import configuration.Setting;
import org.json.JSONArray;
import org.json.JSONObject;
import semantic.Author;

import java.io.File;
import java.io.IOException;
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
    // public DataJournalArticle dataJournalArticle;
    private JSONObject jsonAuthor;
    private JSONObject jsonJournalArticle;

    private DataManager() {

        dataAuthor = new DataAuthor();
    }

    public static DataManager getInstance() {
        return ourInstance;
    }

    public void loadDataFromJson(String filepath) {
        String str = new String();
        System.out.println("Reading file '" + filepath + "'");
        try {
            Scanner scan = new Scanner(new File(filepath));

            while (scan.hasNext())
                str += scan.nextLine();
            scan.close();
            System.out.println("data loaded");


        } catch (IOException e) {
            System.out.println(e.toString());
        }

        JSONObject obj = new JSONObject(str);


        Gson gson = new Gson();

        jsonAuthor = obj.getJSONObject("results");

        JSONArray jarray = jsonAuthor.getJSONArray("bindings");

        for (int i = 0; i < jarray.length(); i++) {
            JSONObject temp = (JSONObject) jarray.get(i);
            Author a = new Author();
            for (Setting setting : BuilderManager.getInstance().settings.configuration) {

                switch (setting.type) {


                    case "Resource":
                        Resource res = gson.fromJson(temp.get(setting.key).toString(), Resource.class);
                        ArrayList<String> stringresources = SplitUsingTokenizer(res, setting);
                        ArrayList<Resource> resourceslist = new ArrayList<>();
                        for (String s : stringresources) {
                            resourceslist.add(new Resource(s));
                        }
                        a.resources.put(setting.key, new ResourcesHandler(resourceslist, Double.parseDouble(setting.weight)));
                        break;
                    case "Information":
                        Information info = gson.fromJson(temp.get(setting.key).toString(), Information.class);
                        ArrayList<String> stringinformation = SplitUsingTokenizer(info, setting);
                        ArrayList<Information> informationlist = new ArrayList<>();
                        for (String s : stringinformation) {
                            informationlist.add(new Information(s));
                        }
                        a.informations.put(setting.key, new InformationsHandler(informationlist, Double.parseDouble(setting.weight)));
                        break;
                    case "Period":
                        Period per = gson.fromJson(temp.get(setting.key).toString(), Period.class);
                        ArrayList<String> stringperiod = SplitUsingTokenizer(per, setting);
                        ArrayList<Period> periodlist = new ArrayList<>();
                        for (String s : stringperiod) {
                            periodlist.add(new Period(s));
                        }
                        a.periods.put(setting.key, new PeriodsHandler(periodlist, Double.parseDouble(setting.weight)));
                        break;
                    default:
                        break;

                }
                temp.get(setting.key).toString();


            }

            this.dataAuthor.authorlist.add(a);

        }


    }


    public void valueAuthorFormatter() {

      /*  System.out.println("generating Author list...");
        for (Author author : dataAuthor.bindings) {
            author.setCoauthorlist(SplitUsingTokenizer(author.coauthors.value, " ; "));
            author.setCreatorlist(SplitUsingTokenizer(author.creators.value, " ; "));
            author.setRolelist(SplitUsingTokenizer(author.roles.value, " ; "));
            author.setRelateslist(SplitUsingTokenizer(author.relates.value, " ; "));
            author.setRealizationlist(SplitUsingTokenizer(author.realizations.value, " ; "));
        }*/


        System.out.println("done");


    }

    public void valueJournalArticleFormatter() {

        System.out.println("generating JournalArticle list...");
     /*   for (JournalArticle journalArticle : dataJournalArticle.bindings) {
            journalArticle.setCiteslist(SplitUsingTokenizer(journalArticle.cites.value, " ; "));

        }*/


        System.out.println("done");


    }

    public ArrayList<String> SplitUsingTokenizer(Builder builder, Setting setting) {

        StringTokenizer strTkn = new StringTokenizer(builder.value, setting.delimiter);
        ArrayList<String> arrLis = new ArrayList<>();


        while (strTkn.hasMoreTokens()) {


            arrLis.add(strTkn.nextToken());


        }


        return arrLis;
    }

    public void clean() {

        this.jsonAuthor = null;
        this.jsonJournalArticle = null;
        this.dataAuthor = null;
        System.gc();
    }


}
