package builder;

import com.google.gson.Gson;
import configuration.Setting;
import configuration.Settings;
import javassist.*;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by Leonardo on 14/01/2015.
 */
public class BuilderManager {

    private static BuilderManager ourInstance = new BuilderManager();
    public Settings settings;
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

        JSONObject obj = new JSONObject(str);


        Gson gson = new Gson();


        settings = gson.fromJson(obj.toString(), Settings.class);


        try {
            settings.endpoint = obj.getString("endpoint");
            settings.query = obj.getString("query");
        } catch (Exception e) {
            e.printStackTrace();
        }

        settings.checkIdentifier();
        for (int i = 0; i < settings.param.size(); i++) {

            String id = "";
            if (settings.param.get(i).identifier)
                id = " as Identifier";
            System.out.println("key: " + settings.param.get(i).key + id);


            // NON CREO PIU CLASSI DINAMICHE
            //  injectionNewParam(settings.configuration.get(i));

        }


    }


    public Object getField(Object a, String field) throws NotFoundException {
        ClassPool pool = new ClassPool();
        pool.appendClassPath("src\\builder\\debug");

        Object value = new Object();
        try {
          /*  CtClass cc = pool.getCtClass("semantic.Author");
            System.out.println(cc.toString());
            for(CtMethod m : cc.getDeclaredMethods()){
                System.out.println(m.getName());
            }*/

            for (Method m : a.getClass().getDeclaredMethods()) {
                System.out.println(m.getName());

                if (m.getName() == "getPerson") {
                    Method method = m;
                    value = method.invoke(a);
                }
            }

        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }


        return value;
    }
}
