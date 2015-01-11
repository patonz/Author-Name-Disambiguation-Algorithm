package console;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Scanner;


/**
 * Created by Leonardo on 06/01/2015.
 */
public class Console {

    private static Console ourInstance = new Console();
    Commands commands = new Commands();

    private Console() {


    }

    public static Console getInstance() {
        return ourInstance;
    }

    public void run() {
        Scanner scan = new Scanner(System.in);

        while (true) {

            System.out.print(">:");
            command(scan.nextLine());


        }
    }

    public void print() {
        System.out.println();
    }

    private void command(String command) {


        try {
            Method method = commands.getClass().getMethod(command);
            method.invoke(commands);
        } catch (NoSuchMethodException e) {
            System.out.println("Command not found, type 'help' for more information");
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }


    }

    public void help() {

        System.out.println("list of Commands:");
        for (Method method : commands.getClass().getDeclaredMethods()) {
            // if (method.getAnnotation(PostConstruct.class) != null) {
            System.out.println("-" + method.getName());
            //  }
        }
    }


}
