package builder.model;

import Skelethon.Similarity;
import configuration.Option;

/**
 * Created by Leonardo on 14/01/2015.
 */
public abstract class BuildersHandler implements Similarity {

    public String delimiter;
    public Double weight;




    public abstract void setOption(Option opt);



}
