package semantic;

import Skelethon.Similarity;
import builder.BuilderManager;
import builder.model.InformationsHandler;
import builder.model.PeriodsHandler;
import builder.model.ResourcesHandler;
import configuration.Setting;
import exception.SimilarTypeNotFoundException;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Leonardo on 07/01/2015.
 */
public class Author implements Similarity {


    public Map<String, ResourcesHandler> resources;
    public Map<String, InformationsHandler> informations;
    public Map<String, PeriodsHandler> periods;


    public Author() {
        resources = new HashMap<>();
        informations = new HashMap<>();
        periods = new HashMap<>();
    }

    @Override
    public double Similarity(Object o) {
        if (!(o instanceof Author)) {
            throw new SimilarTypeNotFoundException();
        }
        Double grade = 0.0;
        Double resourcesgrade = 0.0;
        Double informationsgrade = 0.0;
        Double periodsgrade = 0.0;
        for (Setting s : BuilderManager.getInstance().settings.configuration) {
            Double partialgrade = 0.0;
            if (this.resources.get(s.key) != null) {
                partialgrade = this.resources.get(s.key).Similarity(((Author) o).resources.get(s.key));
                System.out.println(s.key + " grade = " + partialgrade);
                resourcesgrade += partialgrade;
            }
            if (this.informations.get(s.key) != null) {
                partialgrade = this.informations.get(s.key).Similarity(((Author) o).informations.get(s.key));
                System.out.println(s.key + " grade = " + partialgrade);
                informationsgrade += partialgrade;
            }
            if (this.periods.get(s.key) != null) {
                partialgrade = this.periods.get(s.key).Similarity(((Author) o).periods.get(s.key));
                System.out.println(s.key + " grade = " + partialgrade);
                periodsgrade += partialgrade;
            }
        }


        return 0;
    }


}
