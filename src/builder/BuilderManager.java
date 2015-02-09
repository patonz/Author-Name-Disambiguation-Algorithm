package builder;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import configuration.Configuration;
import configuration.Setting;
import configuration.Settings;
import javassist.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by Leonardo on 14/01/2015.
 */
public class BuilderManager {

    private static BuilderManager ourInstance = new BuilderManager();
    public ArrayList<Settings> settings = new ArrayList<>();
    public Configuration configuration;


    public ArrayList<Class> mypool = new ArrayList<Class>();

    private BuilderManager() {

        //   CtClass.debugDump = "src\\builder\\debug";
    }

    public static BuilderManager getInstance() {
        return ourInstance;
    }

    public void injectionNewParam(Setting setting) {

        ClassPool pool = ClassPool.getDefault();
        try {

            CtClass clazz = pool.makeClass(setting.key);

            CtClass superclass = pool.getCtClass("builder.model." + setting.type);

            clazz.setSuperclass(superclass);


            clazz.addField(
                    CtField.make("public String " + setting.key.toLowerCase() + ";", clazz));

            clazz.addMethod(CtMethod.make("public String getValue(){ return " + setting.key.toLowerCase() + "; }", clazz));


            CtClass authorClass = pool.getCtClass("semantic.Author");
            authorClass.addField(CtField.make("public " + setting.key + " " + setting.key.toLowerCase() + ";", authorClass));
            clazz.writeFile("C:\\Users\\Leonardo\\Google Drive\\Tesi Triennale\\Class Injections");


            authorClass.addMethod(CtMethod.make("public " + setting.key + " " + "get" + setting.key + "(){ return " + setting.key.toLowerCase() + "; }", authorClass));
            authorClass.setGenericSignature("runtime.Author");
            mypool.add(clazz.getClass());

            Loader loader = new Loader(ClassPool.getDefault());
            loader.loadClass("semantic.Author");

            System.out.println(clazz.toString());
            System.out.println(authorClass.toString());
        } catch (NotFoundException e) {
            e.printStackTrace();
        } catch (CannotCompileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


    }

    public void newClassThenInvoke() throws Exception {


        ClassPool pool = ClassPool.getDefault();
        CtClass cc = pool.makeClass("MyClass");
        CtClass superclass = pool.getCtClass("builder.model.Resource");
        cc.setSuperclass(superclass);
        CtMethod m = CtMethod.make("public void provaMethod(){ System.out.println(\"prova prova\"); }", cc);
        cc.addMethod(m);
        cc.addMethod(CtMethod.make("public String getValue(){ return value; }", cc));
        Class c = cc.toClass();
        Object obj = c.newInstance();
        Method method = c.getDeclaredMethod("provaMethod");
        method.invoke(obj);
    }


    public void createDynamicClassFromConfig(String filepath) {

        String str = new String();
        System.out.println("Reading file '" + filepath + "'");
        try {
            Scanner scan = new Scanner(new File(filepath));

            while (scan.hasNext())
                str += scan.nextLine();
            scan.close();


        } catch (IOException e) {
            System.out.println(e.toString());
        }
        Gson gson = new Gson();

        JSONObject config = new JSONObject(str);
        this.configuration = gson.fromJson(config.get("configuration").toString(), Configuration.class);

        JSONArray arrayj = config.getJSONArray("settings");


        for (int i = 0; i < arrayj.length(); i++) {
            JSONObject obj = (JSONObject) arrayj.get(i);

            Gson gsonfoset = new Gson();
            Settings set = gsonfoset.fromJson(obj.toString(), Settings.class);


            JsonElement jelem = gsonfoset.fromJson(obj.toString(), JsonElement.class);

            set.configuration = jelem.getAsJsonObject();


            settings.add(set);


        }

        configuration.checkIdentifier();
        for (int k = 0; k < configuration.data_structure.size(); k++) {

            String id = "";
            if (configuration.data_structure.get(k).identifier)
                id = " as Identifier";

            System.out.println("key: " + configuration.data_structure.get(k).key + id);


        }


    }


}
