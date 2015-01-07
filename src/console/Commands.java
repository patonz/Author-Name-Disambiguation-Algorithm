package console;

import javax.annotation.PostConstruct;
import java.lang.reflect.Method;

/**
 * Created by Leonardo on 07/01/2015.
 */
public class Commands {



    public void print(){
        System.out.println("Gesu");
    }

    public void exit(){
        System.exit(0);
    }


    public void help(){
    Console.getInstance().help();
    }
}
