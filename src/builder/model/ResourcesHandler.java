package builder.model;

import configuration.Option;
import exception.SimilarTypeNotFoundException;

import java.util.ArrayList;

/**
 * Created by Leonardo on 14/01/2015.
 */
public class ResourcesHandler extends BuildersHandler {


    public ArrayList<Resource> resources;


    public ResourcesHandler(ArrayList resources) {
        this.resources = resources;

    }

    @Override
    public void setOption(Option opt) {
        for(Resource res : resources){
            res.option = opt;
        }
    }

    @Override
    public Object Similarity(Object obj) {
        if (!(obj instanceof ResourcesHandler)) {
            throw new SimilarTypeNotFoundException();
        }


        ArrayList<Resource> min;
        ArrayList<Resource> max;
        if (resources.size() >= ((ResourcesHandler) obj).resources.size()) {
            min = ((ResourcesHandler) obj).resources;
            max = resources;
        } else {
            min = resources;
            max = ((ResourcesHandler) obj).resources;
        }


        int check = 0;
        double totalcheck = max.size();

        for (Resource resA : min) {
            for (Resource resB : max) {
                if ((int) resA.Similarity(resB) == 1) {
                    check += (int) resA.Similarity(resB);
                    break;
                }

            }
        }

        Double grade = (check * 100) / (totalcheck + (min.size() - check));

        return (!Double.isNaN(grade)) ? grade : 0.0;
    }


}
