package console;

import builder.BuilderManager;
import core.DataManager;
import core.ElaborateManager;
import remote.RemoteManager;
import semantic.Author;

import java.util.Scanner;

/**
 * Created by Leonardo on 07/01/2015.
 */
public class Commands {

    public void exit() {
        System.exit(0);
    }

    public void help() {
        Console.getInstance().help();
    }

    public void printSearch() {
        ElaborateManager.getInstance().printAllSearchResult();
    }

    public void lf() {
        System.out.println("Insert the Path of File");
        Scanner scan = new Scanner(System.in);
        String path = scan.nextLine();
        System.out.println("Insert the class of Data (core.DataAuthor, core.DataJournalArticle)");
        try {
            Class c = Class.forName(scan.nextLine());
            System.out.println("Processing...");
            DataManager.getInstance().loadDataFromJson(path);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }


    public void executeQueryTry() {
        RemoteManager.executeQueryTry();
    }

    public void execute() {
        System.out.println("Insert the Query");
        Scanner scan = new Scanner(System.in);
        RemoteManager.executeQuery(scan.nextLine());

    }


    public void setup() {
        BuilderManager.getInstance().createDynamicClassFromConfig("src/configuration/config.json");
        DataManager.getInstance().loadDataFromJson("src/configuration/data.json");
    }

    public void count() {
        System.out.println("Authors count: " + DataManager.getInstance().dataAuthor.authorlist.size());
    }

    public void countAndPrint() {
        count();
        int index = 000;
        for (Author a : DataManager.getInstance().dataAuthor.authorlist) {
            System.out.println("*********************************");
            System.out.println("_________________");
            System.out.println("| Author n°" + index + " | ");
            a.printAuthor();

            index++;
        }
    }

    public void newSearch() {
        System.out.println("Insert the parameter");
        Scanner scan = new Scanner(System.in);
        ElaborateManager.getInstance().newSearch(scan.nextLine());
    }

    public void elaborate() {

        System.out.println("Insert the Threshold( must be a Double number between 0 and 100");
        Scanner scan = new Scanner(System.in);
        String input = scan.nextLine();
        try {

            ElaborateManager.getInstance().elaborateDisambiguationOnData(Double.parseDouble(input));
        } catch (Exception e){
            System.out.println("the Threshold input must be a double");
        }


    }

    public void clean() {
        DataManager.getInstance().clean();
    }


    public void classTry() {
        try {
            BuilderManager.getInstance().newClassThenInvoke();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
