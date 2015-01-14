package exception;

/**
 * Created by Leonardo on 14/01/2015.
 */
public class SimilarTypeNotFoundException extends RuntimeException {

    public SimilarTypeNotFoundException() {
        super("Cannot use Similarity method withing different type of Objects");
    }
}
