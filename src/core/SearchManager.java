package core;

import java.util.ArrayList;

/**
 * Created by Leonardo on 12/01/2015.
 */
public class SearchManager {
    private static SearchManager ourInstance = new SearchManager();
    private ArrayList<Search> searchlist = new ArrayList<Search>();

    private SearchManager() {
    }

    public static SearchManager getInstance() {
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


}
