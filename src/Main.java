import console.Commands;
import console.Console;

/**
 * Created by Leonardo on 07/01/2015.
 */
public class Main {


    public static void main(String[] args) {
        System.out.println("Processing the first setup..");
        new Commands().setup();
        Console.getInstance().run();
    }
}
