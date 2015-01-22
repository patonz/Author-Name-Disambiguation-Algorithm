package core;


import builder.BuilderManager;
import builder.model.*;
import com.google.gson.Gson;
import com.squareup.okhttp.Headers;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import configuration.Setting;
import org.json.JSONArray;
import org.json.JSONObject;
import semantic.Author;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

/**
 * Created by Leonardo on 07/01/2015.
 */
public class DataManager {

    private static DataManager ourInstance = new DataManager();
    private final OkHttpClient client = new OkHttpClient();
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


    public void loadDataFromURL() throws Exception {


        String query = "PREFIX foaf: <http://xmlns.com/foaf/0.1/> \n" +
                "PREFIX pro: <http://purl.org/spar/pro/> \n" +
                "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> \n" +
                "PREFIX frbr: <http://purl.org/vocab/frbr/core#> \n" +
                "PREFIX fabio: <http://purl.org/spar/fabio/> \n" +
                "SELECT DISTINCT \n" +
                " ?person \n" +
                "(GROUP_CONCAT(DISTINCT ?label; separator = \" ; \") AS ?labelList)\n" +
                "(GROUP_CONCAT(DISTINCT ?givenName; separator = \" ; \") AS ?givenNameList)\n" +
                "(GROUP_CONCAT(DISTINCT ?familyName; separator = \" ; \") AS ?familyNameList)\n" +
                "(GROUP_CONCAT(DISTINCT ?coauthor; separator = \" ; \") AS ?coauthorList)\n" +
                "(GROUP_CONCAT(DISTINCT ?publicationYear; separator = \" ; \") AS ?publicationYearList)\n" +
                "WHERE {\n" +
                " ?person a foaf:Person ;\n" +
                " OPTIONAL { ?person foaf:givenName ?givenName } .\n" +
                " OPTIONAL {?person foaf:familyName ?familyName } .\n" +
                "  OPTIONAL {?person rdfs:label ?label } .\n" +
                "\n" +
                " OPTIONAL { ?person pro:holdsRoleInTime ?role }  .\n" +
                "OPTIONAL { ?role pro:relatesToDocument ?relate } .\n" +
                "\n" +
                " OPTIONAL {?relate frbr:realization ?realization } .\n" +
                "\n" +
                " OPTIONAL {?coauthor pro:relatesToDocument ?relate } .\n" +
                "\n" +
                " OPTIONAL {?realization a fabio:JournalArticle } .\n" +
                "\n" +
                "OPTIONAL {?realization fabio:hasPublicationYear ?publicationYear } .\n" +
                "\n" +
                "\n" +
                "\n" +
                "}\n" +
                "GROUP BY ?person";


        // MediaType sparql = MediaType.parse();

        Request request = new Request.Builder()
                .url("http://two.eelst.cs.unibo.it:8181/data/query?query=" + URLEncoder.encode(query, "UTF-8") + "&output=json&stylesheet=")

                .build();

        Response response = client.newCall(request).execute();
        System.out.println(response.body().string());

        if (!response.isSuccessful())
            throw new IOException("Unexpected code " + response);


        Headers responseHeaders = response.headers();
        for (int i = 0; i < responseHeaders.size(); i++) {
            System.out.println(responseHeaders.name(i) + ": " + responseHeaders.value(i));
        }


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
