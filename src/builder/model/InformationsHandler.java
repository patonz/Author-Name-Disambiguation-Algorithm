package builder.model;

import exception.SimilarTypeNotFoundException;

import java.util.ArrayList;

/**
 * Created by Leonardo on 17/01/2015.
 */
public class InformationsHandler extends BuildersHandler {


    public ArrayList<Information> informations;


    public InformationsHandler(ArrayList informations, Double weight) {
        this.informations = informations;
        this.weight = weight;
    }


    @Override
    public double Similarity(Object obj) {
        if (!(obj instanceof InformationsHandler)) {
            throw new SimilarTypeNotFoundException();
        }


        return 0;
    }


}
