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
    public ArrayList<Result> results = new ArrayList<>();

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

        int countResult = 0;
        int countCombination = 0;
        int maxsize = localauthorlist.size();

        for (int i = 0; i < maxsize; i++) {

            Author a = localauthorlist.get(0);

            localauthorlist.remove(0);

            if (localauthorlist.size() == 0) {
                //condizione di arresto.
                break;
            }
           // System.out.println(localauthorlist.size());
            for (int k = 0; k < localauthorlist.size(); k++) {


                Author b = localauthorlist.get(k);


                Result result = (Result) a.Similarity(b);

                if (result.grade >= threshold && result.check) {
                    System.out.println("Author A n°" + i + " : Author B n°" + k);
                    System.out.println(result.description);
                    countCombination++;
                }


                //this.results.add(result);
                countResult++;


            }


            // ciclo solo un autore, testing


        }

        System.out.println(countCombination+" of "+countResult+" match founds");
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

               /* if (result.grade >= threshold) {
                    System.out.println("Author A n°" + i + " : Author B n°" + k);
                    System.out.println(result.description);
                }*/

            }


            // ciclo solo un autore, testing
           /* if (i == 99) {
                break;
            }*/

        }
        executor.shutdown();

        while (!executor.isTerminated()) {

        }
        System.out.println("Finished all threads");


    }


}
