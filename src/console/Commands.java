package console;

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
        DataManager.getInstance().loadDataFromJson(scan.nextLine());
    }


    public void executeQueryTry() {
        RemoteManager.executeQueryTry();
    }

    public void execute() {
        System.out.println("Insert the Query");
        Scanner scan = new Scanner(System.in);
        RemoteManager.executeQuery(scan.nextLine());

    }


    public void setup(){
        DataManager.getInstance().loadDataFromJson("data.json");
    }



}
