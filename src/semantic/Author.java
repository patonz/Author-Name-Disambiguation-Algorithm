package semantic;

import Skelethon.Similarity;
import builder.BuilderManager;
import builder.model.InformationsHandler;
import builder.model.PeriodsHandler;
import builder.model.ResourcesHandler;
import configuration.Setting;

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
            return 0;
        }
        Double grade = 0.0;
        Double resourcesgrade = 0.0;
        Double informationsgrade = 0.0;
        Double periodsgrade = 0.0;
        for (Setting s : BuilderManager.getInstance().settings.configuration) {
            if (this.resources.get(s.key) != null)
                resourcesgrade += this.resources.get(s.key).Similarity(((Author) o).resources.get(s.key));
            if (this.informations.get(s.key) != null)
                informationsgrade += this.informations.get(s.key).Similarity(((Author) o).informations.get(s.key));
            if (this.periods.get(s.key) != null)
                periodsgrade += this.periods.get(s.key).Similarity(((Author) o).periods.get(s.key));
        }


        return 0;
    }


}
