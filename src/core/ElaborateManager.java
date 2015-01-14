package core;

import semantic.Author;

import java.util.ArrayList;

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

    public ArrayList<Search> getSearchlist() {
        return searchlist;
    }

    public void setSearchlist(ArrayList<Search> searchlist) {
        this.searchlist = searchlist;
    }


    public void printAllSearchResult() {
        if(searchlist.isEmpty()){
            System.out.println("no searches started\n");
            return;
        }
        System.out.println("Search in memory: " + searchlist.size()+"\n");
        for (int i = 0; i < searchlist.size(); i++) {
            System.out.println("Search N°"+(i+1)+" started at "+searchlist.get(i).getTimestamp());
            searchlist.get(i).printResult();
        }

    }

    public void newSearch(String input){
        searchlist.add(new Search());
        searchlist.get(searchlist.size()-1).searchByString(input);
        searchlist.get(searchlist.size()-1).printResult();

    }


    public void elaborateDisambiguationOnData(){
        ArrayList<Author> localauthorlist = (ArrayList<Author>)DataManager.getInstance().dataAuthor.bindings;

        for(int i = 0; i <localauthorlist.size(); i++){

                Author a = localauthorlist.get(535);

                localauthorlist.remove(i);

            for(int k = 0; k< localauthorlist.size();k++){
                Author b = localauthorlist.get(k);

                ArrayList<Combination> combinations = new ArrayList<Combination>();


               // combinations.add(new Combination(a.familyName, b.familyName,10));
               // combinations.add(new Combination(a.givenName, b.givenName, 4));
               // combinations.add(new Combination(a.label,b.label, 1));


             //   System.out.println("A = "+a.label.value+" B = "+b.label.value);
                calculateSimilarity(combinations);




            }

            break; // ciclo solo un autore, testing


        }
    }

    public void calculateSimilarity(ArrayList<Combination> combination){



        Double totalgrade = 0.0;
        Double totalweight = 0.0;
        for(int i = 0; i< combination.size(); i++){

            totalgrade += combination.get(i).getFinalgrade();
            totalweight += combination.get(i).getWeight();
            System.out.println(combination.get(i).A.getClass()+" "+combination.get(i).getGrade()+"% with Weight = "+combination.get(i).getWeight());
        }

        System.out.println("Similarity of "+totalgrade / totalweight+"%");

        System.out.println();

    }


    public static int maxChar(String a, String b){

        return (a.length() >=b.length()) ? a.length() : b.length();

    }

}
