package builder.model;

import exception.SimilarTypeNotFoundException;

import java.util.ArrayList;

/**
 * Created by Leonardo on 14/01/2015.
 */
public  class ResourcesHandler extends BuildersHandler {


    public ArrayList<Resource> resources;




    public ResourcesHandler(ArrayList resources, Double weight){
        this.resources = resources;
        this.weight = weight;
    }



    @Override
    public double Similarity(Object obj) {
        if(!(obj instanceof ResourcesHandler)){
            throw new SimilarTypeNotFoundException();
        }

        int check = 0;
        double totalcheck = resources.size() * ((ResourcesHandler) obj).resources.size();

        for(Resource resA : resources){
            for(Resource resB : ((ResourcesHandler) obj).resources){
              check +=   resA.Similarity(resB);
            }
        }



        return (check * 100)/ totalcheck;
    }



}
