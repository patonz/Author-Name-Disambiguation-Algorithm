package core;


import com.google.gson.Gson;
import org.json.JSONObject;
import semantic.Author;
import semantic.Coauthor;
import semantic.Key;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;
import java.util.StringTokenizer;

/**
 * Created by Leonardo on 07/01/2015.
 */
public class DataManager {

    private JSONObject json;
    public Author[] author_list;
    public Data data;
    private static DataManager ourInstance = new DataManager();

    public static DataManager getInstance() {
        return ourInstance;
    }

    private DataManager() {

    }


    public void loadDataFromJson(String filepath) {
        String str = new String();

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

        json = obj.getJSONObject("results");


        Gson gson = new Gson();
        data = gson.fromJson(json.toString(), Data.class);

        valueFormatter();

    }


    public void valueFormatter(){

        System.out.println("generating list...");
        for(Author author : data.bindings){
            author.setCoauthorlist(SplitUsingTokenizer(author.coauthors.value, " ; "));
            author.setCreatorlist(SplitUsingTokenizer(author.creators.value, " ; "));
            author.setRolelist(SplitUsingTokenizer(author.roles.value, " ; "));
            author.setRelateslist(SplitUsingTokenizer(author.relates.value, " ; "));
        }


        System.out.println("done");


    }

    public  String[] SplitUsingTokenizer(String subject, String delimiters) {
        StringTokenizer strTkn = new StringTokenizer(subject, delimiters);
        ArrayList<String> arrLis = new ArrayList<String>(subject.length());

        while (strTkn.hasMoreTokens()){
            arrLis.add(new String(strTkn.nextToken()));
        }

        return arrLis.toArray(new String[0]);
    }


}
