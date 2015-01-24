package builder.model;

import core.Result;
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
    public Object Similarity(Object obj) {
        if (!(obj instanceof InformationsHandler)) {
            throw new SimilarTypeNotFoundException();
        }



        if(((InformationsHandler) obj).informations.isEmpty() || informations.isEmpty()){
            return new Result(0.0,"information not avaiable");
        }
        Information infomatchedA = informations.get(0);
        Information infomatchedB = ((InformationsHandler) obj).informations.get(0);

        double max = (double)infomatchedA.Similarity(infomatchedB);
        double current = 0.0;

        outerloop:
        for (int i = 0; i < this.informations.size(); i++) {
            Information infoA = this.informations.get(i);
            for (int k = 0; k < ((InformationsHandler) obj).informations.size(); k++) {

                Information infoB = ((InformationsHandler) obj).informations.get(k);

                 current = (double) infoA.Similarity(infoB);
                if (current == 100.0) {
                    infomatchedA = infoA;
                    infomatchedB = infoB;

                    break outerloop;
                }
                 if(current > max){
                    infomatchedA = infoA;
                    infomatchedB = infoB;
                    max = current;
                }

            }

        }

        return new Result((max >= 0.0) ? max : 0.0,"max match found: \"" + infomatchedA.value + "\" & \"" + infomatchedB.value + "\"" );
    }


}
