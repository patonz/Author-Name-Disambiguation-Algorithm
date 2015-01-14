package semantic;

import Skelethon.Similarity;

/**
 * Created by Leonardo on 08/01/2015.
 */
public abstract class Key implements Similarity {

    public String type;
    public String value;

    public Key() {

    }

    public Key(String value) {
        this.type = "literal";
        this.value = value;
    }
}
