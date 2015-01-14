package builder;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;

import java.lang.reflect.Method;

/**
 * Created by Leonardo on 14/01/2015.
 */
public class BuilderManager {

    Class c;

    private static BuilderManager ourInstance = new BuilderManager();

    public static BuilderManager getInstance() {
        return ourInstance;
    }

    private BuilderManager() {
    }




    public void newClassThenInvoke() throws Exception{


        ClassPool pool = ClassPool.getDefault();
        CtClass cc = pool.makeClass("Bracci");

        CtMethod m = CtMethod.make("public void daje(){ System.out.println(\"Sono dio, zio pera\"); }", cc);
        cc.addMethod(m);
        Class c = cc.toClass();
        Object obj = c.newInstance();
        Method method = c.getDeclaredMethod("daje");
        method.invoke(obj);
    }
}
