package builder.model;

import Skelethon.Similarity;
import configuration.Option;

/**
 * Created by Leonardo on 14/01/2015.
 */
public abstract class Builder implements Similarity {


    public double weight;
    public String value;
    public String type;
    public Option option;


    public Builder() {

    }

    public Builder(String value) {
        this.value = value;

    }


    public Builder setValue(String value) {
        this.value = value;
        return this;
    }

    public void setOption(Option option) {
        this.option = option;
    }
}
