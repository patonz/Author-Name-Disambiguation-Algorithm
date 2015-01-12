package exception;

/**
 * Created by Leonardo on 12/01/2015.
 */
public class ResultSearchNullException extends RuntimeException{



    public ResultSearchNullException(){
        super("Result in Search Not Found, start a search before.");
    }
}
