package core;


import com.google.gson.Gson;
import semantic.Author;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by Leonardo on 07/01/2015.
 */
public class DataManager {

    private String json;
    public Author[] author_list;
    public Data data;
    private static DataManager ourInstance = new DataManager();

    public static DataManager getInstance() {
        return ourInstance;
    }

    private DataManager() {

    }



    public void loadDataFromJson(String filepath){


        try {
            for (String line : Files.readAllLines(Paths.get(filepath))) {

                this.json = json + line;
            }
            System.out.println("data loaded");



        } catch (IOException e) {
            System.out.println(e.toString());
        }

        Gson gson = new Gson();

        data = gson.fromJson(json, Data.class);


    }




}
