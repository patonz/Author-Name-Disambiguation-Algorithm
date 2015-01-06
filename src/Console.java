/**
 * Created by Leonardo on 06/01/2015.
 */
public class Console {
    private static Console ourInstance = new Console();

    public static Console getInstance() {
        return ourInstance;
    }

    private Console() {
    }
}
