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


        Information infomatchedA = informations.get(0);
        Information infomatchedB = ((InformationsHandler) obj).informations.get(0);

        int min = (int)infomatchedA.Similarity(infomatchedB);
        int distance = 0;

        outerloop:
        for (int i = 0; i < this.informations.size(); i++) {
            Information infoA = this.informations.get(i);
            for (int k = 0; k < ((InformationsHandler) obj).informations.size(); k++) {

                Information infoB = ((InformationsHandler) obj).informations.get(k);

                 distance = (int) infoA.Similarity(infoB);
                if (distance == 0) {
                    infomatchedA = infoA;
                    infomatchedB = infoB;

                    break outerloop;
                }
                 if(distance < min){
                    infomatchedA = infoA;
                    infomatchedB = infoB;
                    min = distance;
                }

            }

        }
        int maxchar = Math.max(infomatchedA.value.length(), infomatchedB.value.length());
                System.out.println("max match found: \"" + infomatchedA.value + "\" & \"" + infomatchedB.value + "\" with distance = " + distance);
        return (maxchar >= distance) ? ((maxchar-distance) * 100)/ maxchar : 0.0;
    }


}
