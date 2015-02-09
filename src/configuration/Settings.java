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


}
