package console;

import builder.BuilderManager;
import core.DataManager;
import core.ElaborateManager;
import core.Result;
import remote.RemoteManager;
import semantic.Author;
import util.Chronometer;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
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
        DataManager.getInstance().createDatasetFromFile(path);

        count();
    }


    public void executeQueryTry() {
        RemoteManager.executeQueryTry();
    }

    public void execute() {
        System.out.println("Insert the Query");
        Scanner scan = new Scanner(System.in);
        RemoteManager.executeQuery(scan.nextLine());

    }

    public void remoteSetup() {
        try {
            DataManager.getInstance().createDatasetFromEndpoint();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void setup() {
        try {
            BuilderManager.getInstance().createDynamicClassFromConfig("src/configuration/config.json");

        } catch (Exception e) {
            System.err.println("config.json file missing / corrupted");
        }


       // DataManager.getInstance().createDatasetFromEndpoint();
         DataManager.getInstance().createDatasetFromFile("src/configuration/output.json");

        count();
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

    public void printResult() {
        System.out.println(ElaborateManager.getInstance().results.size());
        int i = 0;
        for (Result r : ElaborateManager.getInstance().results) {
            System.out.println(r.description + r.check + "\n");
            i++;
            if (i == 99) {
                break;
            }
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
                DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
                Date dateobj = new Date();

                System.out.println(df.format(dateobj) + ": processing...");
                Chronometer chronometer = new Chronometer();
                chronometer.start();
                ElaborateManager.getInstance().elaborateDisambiguationOnData(threshold);
                chronometer.stop();
                System.out.println(df.format(new Date()) + ": operation terminated in " + chronometer.getSeconds() + "sec | " + chronometer.getMinutes() + " min | " + chronometer.getHours() + "h");
            } else {
                System.out.println("the Threshold input must be a number between 0 and 100");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("the Threshold input must be a double");
        }


    }

    public void et() {


        DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
        Date dateobj = new Date();

        System.out.println(df.format(dateobj) + ": processing...");
        Chronometer chronometer = new Chronometer();
        chronometer.start();
        ElaborateManager.getInstance().elaborateDisambiguationOnDataWithThreadPool();
        chronometer.stop();
        System.out.println(df.format(new Date()) + ": operation terminated in " + chronometer.getSeconds() + "sec | " + chronometer.getMinutes() + " min | " + chronometer.getHours() + "h");


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
