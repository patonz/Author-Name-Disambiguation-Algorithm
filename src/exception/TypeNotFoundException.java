package exception;

/**
 * Created by Leonardo on 27/01/2015.
 */
public class TypeNotFoundException extends RuntimeException {
    public TypeNotFoundException(String variable) {
        super("Wrong \"type\" on config.json:\""+variable+"\"" );
    }
}
