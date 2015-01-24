package core;

import semantic.Author;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Leonardo on 12/01/2015.
 */
public class ElaborateManager {
    private static ElaborateManager ourInstance = new ElaborateManager();
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

    public void elaborateDisambiguationOnData(Double threshold) {
        ArrayList<Author> localauthorlist;
        localauthorlist = (ArrayList<Author>) DataManager.getInstance().dataAuthor.authorlist.clone();

        int maxsize = localauthorlist.size();

        for (int i = 0; i < maxsize; i++) {

            Author a = localauthorlist.get(0);

            localauthorlist.remove(0);

            if (localauthorlist.size() == 0) {
                //condizione di arresto.
                break;
            }
            for (int k = 0; k < localauthorlist.size(); k++) {


                Author b = localauthorlist.get(k);


                Result result = (Result) a.Similarity(b);

                if (result.grade >= threshold) {
                    System.out.println("Author A n°" + i + " : Author B n°" + k);
                    System.out.println(result.description);
                }

            }


            // ciclo solo un autore, testing


        }
    }

    public void elaborateDisambiguationOnDataWithThreadPool() {
        ArrayList<Author> localauthorlist = (ArrayList<Author>) DataManager.getInstance().dataAuthor.authorlist.clone();

        ExecutorService executor = Executors.newFixedThreadPool(50);



        for (int i = 0; i < localauthorlist.size(); i++) {

            Author a = localauthorlist.get(0);


            for (int k = 0; k < localauthorlist.size(); k++) {


                Author b = localauthorlist.get(k);

                a.setAuthorBrunnable(b);
                executor.execute(a);

               /* if (result.grade >= threshold) {
                    System.out.println("Author A n°" + i + " : Author B n°" + k);
                    System.out.println(result.description);
                }*/

            }


            // ciclo solo un autore, testing


        }
        executor.shutdown();

        while (!executor.isTerminated()) {

        }
        System.out.println("Finished all threads");


    }


}
