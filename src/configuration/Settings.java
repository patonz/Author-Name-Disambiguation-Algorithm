package configuration;

import com.google.gson.JsonObject;

import java.util.List;

/**
 * Created by Leonardo on 15/01/2015.
 */
public class Settings {

    public String endpoint;
    public String query;
    public GlobalSetting global_setting;

    public Setting identifier;
    public JsonObject configuration;


    public List<Setting> param;


    public void checkIdentifier() {


        for (Setting s : param) {
            if (s.identifier) {
                s.identifier = true;
                this.identifier = s;
                return;
            }
        }
        if (!param.isEmpty()){


            this.identifier = param.get(0);
            System.err.println("identifier not found: \'"+identifier.key+"\' automatically defined");
        }


    }
}
