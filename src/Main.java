import console.Commands;
import console.Console;

/**
 * Created by Leonardo on 07/01/2015.
 */
public class Main {


    public static void main(String[] args) {
        long maxHeapSize = Runtime.getRuntime().maxMemory();
        long freeHeapSize = Runtime.getRuntime().freeMemory();
        long totalHeapSize = Runtime.getRuntime().totalMemory();
        System.out.println("Max Heap Size = " + maxHeapSize + " byte");
        System.out.println("Free Heap Size = " + freeHeapSize + " byte");
        System.out.println("Total Heap Size = " + totalHeapSize + " byte");

        System.out.println("Processing the first setup..");
        new Commands().setup();
        Console.getInstance().run();
    }
}
