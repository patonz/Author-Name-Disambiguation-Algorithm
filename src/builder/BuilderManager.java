package builder;

import builder.model.Builder;
import builder.model.Resource;
import com.google.gson.Gson;
import com.hp.hpl.jena.sparql.sse.builders.BuilderExpr;
import configuration.Setting;
import configuration.Settings;
import javassist.*;
import javassist.bytecode.SignatureAttribute;
import org.json.JSONObject;
import semantic.Author;
import sun.net.www.content.text.Generic;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by Leonardo on 14/01/2015.
 */
public class BuilderManager {

    Class c;
    ArrayList<Class> mypool = new ArrayList<Class>();

    private static BuilderManager ourInstance = new BuilderManager();

    public static BuilderManager getInstance() {
        return ourInstance;
    }

    private BuilderManager() {

        CtClass.debugDump = "src\\builder\\debug";
    }


    public void injectionNewParam(Setting setting) {

        ClassPool pool = ClassPool.getDefault();
        try {

            CtClass clazz = pool.makeClass(setting.key);

            CtClass superclass = pool.getCtClass("builder.model."+setting.type);

            clazz.setSuperclass(superclass);





            clazz.addField(
                    CtField.make("public String "+setting.key.toLowerCase()+";", clazz));

            clazz.addMethod(CtMethod.make("public String getValue(){ return "+setting.key.toLowerCase()+"; }", clazz));


            CtClass authorClass = pool.getCtClass("semantic.Author");
            authorClass.addField(CtField.make("public " + setting.key + " " + setting.key.toLowerCase() + ";", authorClass));
            clazz.writeFile("C:\\Users\\Leonardo\\Google Drive\\Tesi Triennale\\Class Injections");



            authorClass.addMethod(CtMethod.make("public "+setting.key + " "+"get"+setting.key+"(){ return "+setting.key.toLowerCase()+"; }", authorClass));
            mypool.add(clazz.getClass());



            System.out.println(clazz.toString());
            System.out.println(authorClass.toString());
        } catch (NotFoundException e) {
            e.printStackTrace();
        } catch (CannotCompileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public void newClassThenInvoke() throws Exception{


        ClassPool pool = ClassPool.getDefault();
        CtClass cc = pool.makeClass("Bracci");
        CtClass superclass = pool.getCtClass("builder.model.Resource");
        cc.setSuperclass(superclass);
        CtMethod m = CtMethod.make("public void daje(){ System.out.println(\"Sono dio, zio pera\"); }", cc);
        cc.addMethod(m);
        cc.addMethod(CtMethod.make("public String getValue(){ return value; }", cc));
        Class c = cc.toClass();
        Object obj = c.newInstance();
        Method method = c.getDeclaredMethod("daje");
        method.invoke(obj);
    }





    public void createDynamicClassFromConfig(String filepath){

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



        Settings settings = gson.fromJson(obj.toString(), Settings.class);

        for(int i =0; i< settings.configuration.size(); i++){


            System.out.println(settings.configuration.get(i).key);


            injectionNewParam(settings.configuration.get(i));












        }



    }


    public Object getField(Object a, String field) throws NotFoundException {
        ClassPool pool = new ClassPool();
        pool.appendClassPath("src\\builder\\debug");

        Object value = new Object();
        try {
            CtClass cc = pool.getCtClass("semantic.Author");
            System.out.println(cc.toString());
            for(CtMethod m : cc.getDeclaredMethods()){
                System.out.println(m.getName());
            }


            //Class temp = cc.toClass();
        Method method = cc.getClass().getDeclaredMethod("getPerson");
       value = method.invoke(a);

        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NotFoundException e) {
            e.printStackTrace();
        }


        return value;
    }
}
