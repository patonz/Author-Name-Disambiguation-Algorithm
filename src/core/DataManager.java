package core;


import builder.BuilderManager;
import builder.model.*;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.squareup.okhttp.Headers;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import configuration.DataStructure;
import exception.TypeNotFoundException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import java.util.StringTokenizer;

/**
 * Created by Leonardo on 07/01/2015.
 */
public class DataManager {

    private static DataManager ourInstance = new DataManager();
    private final OkHttpClient client = new OkHttpClient();
    public semantic.Author[] author_list;
    public DataAuthor dataAuthor;
    public DataAuthorBaseLine dataAuthorBaseLine;


    private JSONObject jsonAuthor;
    private JSONObject jsonJournalArticle;

    private DataManager() {

        dataAuthor = new DataAuthor();
    }

    public static DataManager getInstance() {
        return ourInstance;
    }


    public String downloadDataFromURL() throws IOException {

        System.out.println("reading from endpoint...");

        String endpoint = BuilderManager.getInstance().configuration.endpoint;
        String query = URLEncoder.encode(BuilderManager.getInstance().configuration.query, "UTF-8");
        String uri = endpoint + "/query?query=" + query + "&output=json&stylesheet=";


        Request request = new Request.Builder()
                .url(uri)
                .build();

        Response response = client.newCall(request).execute();
        // System.out.println(response.body().string());

        if (!response.isSuccessful())
            throw new IOException("Unexpected code " + response);


        Headers responseHeaders = response.headers();
        for (int i = 0; i < responseHeaders.size(); i++) {
            //System.out.println(responseHeaders.name(i) + ": " + responseHeaders.value(i));
        }

        System.out.println("done");
        return response.body().string();
    }


    public String loadFile(String filepath) {
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
        return str;

    }

    public void loadDataFromJsonDebug(String string) {
 /*
        JSONObject obj = new JSONObject(string);
        JSONObject objconverted = new JSONObject();
        Gson gson = new Gson();

        JSONArray vars = new JSONArray();
        for (Setting s : set.param)
            vars.put(s.key);


        objconverted.put("head", new JSONObject().put("vars", vars));


        JSONArray author = new JSONArray();


        System.out.println(objconverted);
        Iterator<String> iter = obj.keys();
        while (iter.hasNext()) {
            String key = iter.next();
            try {
                Object value = obj.get(key);


            } catch (JSONException e) {
                // Something went wrong!
                System.err.println("problem with jsoniterator");
            }
        }
        */
    }

    public void loadDataFromJson(String string) {


        JSONObject obj = new JSONObject(string);


        Gson gson = new Gson();

        jsonAuthor = obj.getJSONObject("results");

        JSONArray jarray = jsonAuthor.getJSONArray("bindings");

        for (int i = 0; i < jarray.length(); i++) {
            JSONObject temp = (JSONObject) jarray.get(i);
            semantic.Author a = new semantic.Author();
            for (DataStructure structure : BuilderManager.getInstance().configuration.data_structure) {

                switch (structure.type) {


                    case "Resource":

                        ArrayList<Resource> resourceslist = new ArrayList<>();
                        if (temp.has(structure.key)) {
                            Resource res = gson.fromJson(temp.get(structure.key).toString(), Resource.class);
                            ArrayList<String> stringresources = SplitUsingTokenizer(res, structure);

                            for (String s : stringresources) {
                                Resource r = new Resource(s);

                                resourceslist.add(r);
                            }
                        }
                        a.resources.put(structure.key, new ResourcesHandler(resourceslist));


                        break;
                    case "Information":
                        ArrayList<Information> informationlist = new ArrayList<>();
                        if (temp.has(structure.key)) {
                            Information info = gson.fromJson(temp.get(structure.key).toString(), Information.class);
                            ArrayList<String> stringinformation = SplitUsingTokenizer(info, structure);

                            for (String s : stringinformation) {
                                Information in = new Information(s);

                                informationlist.add(in);
                            }
                        }
                        a.informations.put(structure.key, new InformationsHandler(informationlist));
                        break;
                    case "Period":
                        ArrayList<Period> periodlist = new ArrayList<>();
                        if (temp.has(structure.key)) {


                            Period per = gson.fromJson(temp.get(structure.key).toString(), Period.class);
                            ArrayList<String> stringperiod = SplitUsingTokenizer(per, structure);

                            for (String s : stringperiod) {
                                Period p = new Period(s);
                                periodlist.add(p);
                            }
                        }
                        a.periods.put(structure.key, new PeriodsHandler(periodlist));
                        break;
                    default:
                        throw new TypeNotFoundException(structure.key);


                }
                //temp.get(setting.key).toString();


            }

            this.dataAuthor.authorlist.add(a);

        }


    }

    public void createDatasetFromFile(String pathfile) {
        loadDataFromJson(loadFile(pathfile));

    }

    public void createDatasetFromDebugFile(String pathfile) {
        loadDataFromJsonDebug(loadFile(pathfile));
    }

    public void createDatasetFromEndpoint() {


        try {
            loadDataFromJson(downloadDataFromURL());
        } catch (IOException e) {
            System.err.println("Cant download data from the Endpoint, check \"config.json\"");
            // e.printStackTrace();
        } catch (JSONException e) {
            System.err.println("different keys found: keys on config.json and vars on query must be the same");
        }
    }


    public ArrayList<String> SplitUsingTokenizer(Builder builder, DataStructure structure) {

        StringTokenizer strTkn = new StringTokenizer(builder.value, structure.delimiter);
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


    public void writeJson(JsonElement json, String namefile) {
        try {

            File dir = new File("output");
            if (!dir.exists()) {
                dir.mkdir();
            }
            DateFormat df = new SimpleDateFormat("dd-MM-yy_HH.mm.ss");
            Date dateobj;


            String ext = ".json";
            String directory = "output/";
            File f = new File(directory + namefile + ext);
            if (f.exists()) {
                f.setWritable(true);

                dateobj = new Date(f.lastModified());
                File newfile = new File(directory + namefile + "_" + df.format(dateobj) + ext);
                if (!f.renameTo(newfile))
                    System.err.println("File rename operation failed !");
            }
            FileWriter file = new FileWriter(directory + namefile + ext);

            BufferedWriter bw = new BufferedWriter(file);
            bw.write(json.toString());
            File newfile = new File(directory + namefile + ext);
            System.out.println("Successfully Copied JSON Object to File " + newfile.getAbsolutePath());
            //System.out.println("\nJSON Object: " + json);
            //  bw.flush();
            bw.close();

        } catch (IOException e) {
            e.printStackTrace();

        }
    }


    public void loadBaseLine(String string) {
        if (string == null || string.isEmpty()) System.err.println("json empty");
        JSONObject obj = new JSONObject(string);


        Gson gson = new Gson();

        JSONObject results = obj.getJSONObject("results");

        this.dataAuthorBaseLine = gson.fromJson(results.toString(), DataAuthorBaseLine.class);

        if (this.dataAuthorBaseLine == null) {
            System.err.print("dataAuthor null");
        }


    }

}
