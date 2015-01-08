package core;


import com.google.gson.Gson;
import org.json.JSONObject;
import semantic.Author;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

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



    public void loadDataFromJson(String filepath){
        String  str = new String();

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


     


    }




}
