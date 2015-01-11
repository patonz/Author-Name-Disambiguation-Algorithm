package console;

import core.DataAuthor;
import core.DataJournalArticle;
import core.DataManager;
import remote.RemoteManager;

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

    public void print() {
        System.out.println("a Print");
    }

    public void lf() {
        System.out.println("Insert the Path of File");
        Scanner scan = new Scanner(System.in);
        String path = scan.nextLine();
        System.out.println("Insert the class of Data (core.DataAuthor, core.DataJournalArticle)");
        try {
            Class c = Class.forName(scan.nextLine());
            System.out.println("Processing...");
            DataManager.getInstance().loadDataFromJson(path, c);
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
        DataManager.getInstance().loadDataFromJson("DataLimited.json", DataAuthor.class);
        DataManager.getInstance().loadDataFromJson("JournalArticleLimited.json", DataJournalArticle.class);
    }

    public void count() {
        System.out.println("Authors count: " + DataManager.getInstance().dataAuthor.bindings.size());
    }

}
