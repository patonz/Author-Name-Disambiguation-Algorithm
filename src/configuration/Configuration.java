package configuration;

import java.util.ArrayList;

/**
 * Created by Leonardo on 08/02/2015.
 */
public class Configuration {


    public String query;
    public String endpoint;
    public ArrayList<DataStructure> data_structure;
    public String identifier;


    public boolean getIdentifier(String key) {
        for (DataStructure ds : data_structure) {
            if (ds.key.equals(key)) {
                return ds.identifier;
            }
        }

        return false;

    }


    public void checkIdentifier() {


        for (DataStructure ds : data_structure) {
            if (ds.identifier) {
                ds.identifier = true;
                this.identifier = ds.key;
                return;
            }
        }
        if (!data_structure.isEmpty()) {


            this.identifier = data_structure.get(0).key;
            System.err.println("identifier not found: \'" + identifier + "\' automatically defined");
        }


    }

}
