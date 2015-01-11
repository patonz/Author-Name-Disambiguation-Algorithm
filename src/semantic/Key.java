package semantic;

/**
 * Created by Leonardo on 08/01/2015.
 */
public class Key {

    public String type;
    public String value;

    public Key() {

    }

    public Key(String value) {
        this.type = "literal";
        this.value = value;
    }
}
