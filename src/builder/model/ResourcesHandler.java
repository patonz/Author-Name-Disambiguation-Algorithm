package builder.model;

import exception.SimilarTypeNotFoundException;

import java.util.ArrayList;

/**
 * Created by Leonardo on 14/01/2015.
 */
public  class ResourcesHandler extends BuildersHandler {


    public ArrayList<Resource> resources;




    public ResourcesHandler(ArrayList resources){
        this.resources = resources;
    }



    @Override
    public double Similarity(Object obj) {
        if(!(obj instanceof ResourcesHandler)){
            throw new SimilarTypeNotFoundException();
        }


        return 0;
    }



}
