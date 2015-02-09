package core;

import builder.BuilderManager;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import configuration.Settings;
import org.json.JSONArray;
import semantic.Author;
import semantic.AuthorBaseLine;
import util.MathUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Leonardo on 12/01/2015.
 */
public class ElaborateManager {
    private static ElaborateManager ourInstance = new ElaborateManager();
    public JsonObject outputjson;
    public ArrayList<Result> results = new ArrayList<>();
    private ArrayList<Search> searchlist = new ArrayList<Search>();
    private ArrayList<Author> authorlistelaborated;

    private ElaborateManager() {
    }

    public static ElaborateManager getInstance() {
        return ourInstance;
    }

    public static int maxChar(String a, String b) {

        return (a.length() >= b.length()) ? a.length() : b.length();

    }

    public ArrayList<Search> getSearchlist() {
        return searchlist;
    }

    public void setSearchlist(ArrayList<Search> searchlist) {
        this.searchlist = searchlist;
    }

    public void printAllSearchResult() {
        if (searchlist.isEmpty()) {
            System.out.println("no searches started\n");
            return;
        }
        System.out.println("Search in memory: " + searchlist.size() + "\n");
        for (int i = 0; i < searchlist.size(); i++) {
            System.out.println("Search N°" + (i + 1) + " started at " + searchlist.get(i).getTimestamp());
            searchlist.get(i).printResult();
        }

    }

    public void newSearch(String input) {
        searchlist.add(new Search());
        searchlist.get(searchlist.size() - 1).searchByString(input);
        searchlist.get(searchlist.size() - 1).printResult();

    }

    public void elaborateBaseLine(String type, int prob) {

        try {
            Method method;

            ArrayList<AuthorBaseLine> localauthorlist = (ArrayList<AuthorBaseLine>) DataManager.getInstance().dataAuthorBaseLine.bindings.clone();

            switch (type) {
                case "A":
                    method = AuthorBaseLine.class.getMethod("SimilarityA", Object.class, int.class);
                    break;
                case "B":
                    method = AuthorBaseLine.class.getMethod("SimilarityB", Object.class, int.class);
                    break;
                default:
                    System.err.print("BaseLine not found");
                    return;

            }

            int maxsize = localauthorlist.size();
            JsonArray shortoutputs = new JsonArray();
            for (int i = 0; i < maxsize; i++) {

                AuthorBaseLine a = localauthorlist.get(0);

                localauthorlist.remove(0);
                if (localauthorlist.size() == 0) {

                    break;
                }

                for (int k = 0; k < localauthorlist.size(); k++) {


                    AuthorBaseLine b = localauthorlist.get(k);
                    method.invoke(a, b, prob);

                    if ((boolean) method.invoke(a, b, prob)) {
                        System.out.println("AuthorBaseLine A n°" + i + " : AuthorBaseLine B n°" + (k + i + 1));
                        JsonPrimitive aPrimitive = new JsonPrimitive(a.person.value);
                        JsonPrimitive bPrimitive = new JsonPrimitive(b.person.value);
                        JsonArray comb = new JsonArray();
                        comb.add(aPrimitive);
                        comb.add(bPrimitive);
                        shortoutputs.add(comb);


                    }


                }


            }

            DataManager.getInstance().writeJson(shortoutputs, "result_short.json");
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }


    public void elaborateDisambiguationOnData(Double threshold) {
        ArrayList<Author> localauthorlist;
        localauthorlist = (ArrayList<Author>) DataManager.getInstance().dataAuthor.authorlist.clone();

        int countResult = 0;
        int countCombination = 0;
        int maxsize = localauthorlist.size();
        int authorA = -1;
        int authorB = -1;

        JsonArray shortoutputs = new JsonArray();
        JsonObject output = new JsonObject();
        JsonArray results = new JsonArray();


        for (int i = 0; i < maxsize; i++) {

            Author a = localauthorlist.get(0);

            localauthorlist.remove(0);

            if (localauthorlist.size() == 0) {

                break;
            }

            for (int k = 0; k < localauthorlist.size(); k++) {


                Author b = localauthorlist.get(k);


                ArrayList<Result> resultlist = (ArrayList<Result>) a.Similarity(b);

                for (Result res : resultlist) {
                    if (res.check) {
                        if (i != authorA || (k + 1 + i) != authorB) {
                            System.out.println("Author A n°" + i + " : Author B n°" + (i + 1 + k));


                            results.add(res.output);
                            shortoutputs.add(res.shortoutput);
                            authorA = i;
                            authorB = k + 1 + i;
                            countCombination++;
                            break;
                        }
                    }


                    countResult++;

                }
            }


        }

        JsonObject metainfo = new JsonObject();
        JsonArray settingsjson = new JsonArray();
        for (Settings settings : BuilderManager.getInstance().settings) {
            settingsjson.add(settings.configuration);
        }
        metainfo.add("configuration", settingsjson);
        metainfo.addProperty("combination_count", countResult);
        metainfo.addProperty("result_count", countCombination);
        metainfo.addProperty("identifier", BuilderManager.getInstance().configuration.identifier);
        output.add("meta_info", metainfo);
        output.add("results", results);

        this.outputjson = output;

        System.out.println(countCombination + " of " + countResult + " match founds");


        DataManager.getInstance().writeJson(this.outputjson, "result_full");
        DataManager.getInstance().writeJson(shortoutputs, "result_short");
    }

    public void elaborateDisambiguationOnDataWithThreadPool() {
        ArrayList<Author> localauthorlist = DataManager.getInstance().dataAuthor.authorlist;

        ExecutorService executor = Executors.newFixedThreadPool(1);


        for (int i = 0; i < localauthorlist.size(); i++) {

            Author a = localauthorlist.get(0);


            for (int k = 0; k < localauthorlist.size(); k++) {


                Author b = localauthorlist.get(k);

                a.setAuthorBrunnable(b);
                executor.execute(a);

            }

        }
        executor.shutdown();

        while (!executor.isTerminated()) {

        }
        System.out.println("Finished all threads");


    }


    public String precisionRecall() {


        try {

            JSONArray result = new JSONArray(DataManager.getInstance().loadFile("output/result_short.json"));
            JSONArray answer = new JSONArray(DataManager.getInstance().loadFile("src/builder/debug/answers_dataset.json"));

            int rilevantauthor = 0;
            int retrievedauthor = result.length();
            int intersection = 0;
            for (int i = 0; i < answer.length(); i++) {
                JSONArray ans = answer.getJSONArray(i);
                rilevantauthor += MathUtils.binomial(ans.length(), 2);
            }


            for (int i = 0; i < result.length(); i++) {


                JSONArray res = result.getJSONArray(i);
                String idA = res.getString(0);
                String idB = res.getString(1);


                for (int k = 0; k < answer.length(); k++) {

                    boolean checkA = false;
                    boolean checkB = false;
                    String idAcheck = "";
                    String idBcheck = "";
                    JSONArray ans = answer.getJSONArray(k);
                    for (int j = 0; j < ans.length(); j++) {
                        if (ans.get(j).equals(idA)) {
                            checkA = true;
                            idAcheck = idA;
                        }
                        if (ans.get(j).equals(idB)) {
                            checkB = true;

                            idBcheck = idB;

                        }

                        if (checkA && checkB) {
                            intersection += 1;
                            checkA = false;
                            checkB = false;
                            System.out.println("A n°" + i + " : B n°" + k + "\nintersection found in: " + idAcheck + " & " + idBcheck);
                        }

                    }
                }


            }

            double precision = (double) intersection / retrievedauthor;
            double recall = (double) intersection / rilevantauthor;

            System.out.println("retrievedeauthor = " + retrievedauthor + "\nrilevantauthor = " + rilevantauthor + "\nintersection = " + intersection + "\nprecision = " + precision + "\nrecall = " + recall);


        } catch (Exception e) {
            System.err.println("files not found or corrupted");
        }


        return "";
    }

}
