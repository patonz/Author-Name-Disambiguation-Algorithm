package console;

import builder.BuilderManager;
import core.DataManager;
import core.ElaborateManager;
import remote.RemoteManager;
import semantic.Author;
import util.Chronometer;

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

        System.out.println("Processing...");
        DataManager.getInstance().loadDataFromJson(path);

    }


    public void executeQueryTry() {
        RemoteManager.executeQueryTry();
    }

    public void execute() {
        System.out.println("Insert the Query");
        Scanner scan = new Scanner(System.in);
        RemoteManager.executeQuery(scan.nextLine());

    }

    public void remoteSetup(){
        try {
            DataManager.getInstance().loadDataFromURL();
        } catch (Exception e) {
            e.printStackTrace();
        }
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
            double threshold = Double.parseDouble(input);
            if (threshold >= 0 && threshold <= 100) {
                System.out.println("processing...");
                Chronometer chronometer = new Chronometer();
                chronometer.start();
                ElaborateManager.getInstance().elaborateDisambiguationOnData(threshold);
                chronometer.stop();
                System.out.println("operation terminated in " + chronometer.getMinutes() + " min");
            } else {
                System.out.println("the Threshold input must be a number between 0 and 100");
            }
        } catch (Exception e) {
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
