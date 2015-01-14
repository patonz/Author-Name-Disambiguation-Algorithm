package builder;

/**
 * Created by Leonardo on 14/01/2015.
 */
public class BuilderManager {
    private static BuilderManager ourInstance = new BuilderManager();

    public static BuilderManager getInstance() {
        return ourInstance;
    }

    private BuilderManager() {
    }
}
