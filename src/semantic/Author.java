package semantic;

import Skelethon.Similarity;
import builder.BuilderManager;
import builder.model.*;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import configuration.Setting;
import configuration.Settings;
import core.Result;
import exception.SimilarTypeNotFoundException;
import util.MathUtils;
import util.Monomial;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Leonardo on 07/01/2015.
 */
public class Author implements Similarity, Runnable {


    public Map<String, ResourcesHandler> resources;
    public Map<String, InformationsHandler> informations;
    public Map<String, PeriodsHandler> periods;
    public Author authorBrunnable;


    public Author() {
        resources = new HashMap<>();
        informations = new HashMap<>();
        periods = new HashMap<>();
    }

    @Override
    public Object Similarity(Object o) {
        if (!(o instanceof Author)) {
            throw new SimilarTypeNotFoundException();
        }


        //ArrayList<Boolean> global_check = new ArrayList<>();




        ArrayList<Result> results = new ArrayList<>();


        for (Settings set : BuilderManager.getInstance().settings) {
            JsonObject jsonObject = new JsonObject();
            JsonArray identifier = new JsonArray();
            JsonObject params = new JsonObject();
            Double gradewighted = 0.0;
            Double weights = 0.0;
            String authorresult = "";

            boolean check = true;
            ArrayList<Monomial> polinomial = new ArrayList<>();
            Monomial x = null;
            int countglobalcheck = 0;
            int maxglobalcheck = 0;

            for (Setting s : set.param) {

                Double partialgrade = 0.0;
                if (this.resources.get(s.key) != null) {
                    this.resources.get(s.key).setOption(s.options);
                    ((Author) o).resources.get(s.key).setOption(s.options);
                    partialgrade = (double) this.resources.get(s.key).Similarity(((Author) o).resources.get(s.key));

                    if (partialgrade < Double.parseDouble(s.threshold) || partialgrade > Double.parseDouble(s.limit)) {

                        check = false;
                        break;
                    }
                    if (Double.parseDouble(s.weight) > 0) {

                        maxglobalcheck = maxglobalcheck + 1;
                        if (partialgrade >= Double.parseDouble(set.global_setting.global_threshold))
                            countglobalcheck = countglobalcheck + 1;
                    }


                    authorresult += s.key + " grade = " + partialgrade + " with weight= " + s.weight + "\n";
                    JsonObject temp = new JsonObject();
                    temp.addProperty("grade", partialgrade);
                    temp.addProperty("weight", s.weight);
                    JsonArray valuesA = new JsonArray();

                    for (Resource r : this.resources.get(s.key).resources) {
                        valuesA.add(new JsonPrimitive(r.value));
                        if (BuilderManager.getInstance().configuration.getIdentifier(s.key))
                            identifier.add(new JsonPrimitive(r.value));

                    }
                    JsonArray valuesB = new JsonArray();
                    for (Resource r : ((Author) o).resources.get(s.key).resources) {
                        valuesB.add(new JsonPrimitive(r.value));
                        if (BuilderManager.getInstance().configuration.getIdentifier(s.key))
                            identifier.add(new JsonPrimitive(r.value));
                    }


                    JsonObject values = new JsonObject();
                    values.add("valuesA", valuesA);
                    values.add("valuesB", valuesB);

                    temp.add("values", values);


                    params.add(s.key, temp);
                    if (s.options.arw) {
                        x = new Monomial(Double.parseDouble(s.weight), partialgrade);

                    } else {

                        polinomial.add(new Monomial(Double.parseDouble(s.weight), partialgrade));

                    }

                    weights += Double.parseDouble(s.weight);
                    gradewighted += ((partialgrade * Double.parseDouble(s.weight)));

                } else if (this.informations.get(s.key) != null) {

                    this.informations.get(s.key).setOption(s.options);
                    ((Author) o).informations.get(s.key).setOption(s.options);

                    Result result = (Result) this.informations.get(s.key).Similarity(((Author) o).informations.get(s.key));
                    partialgrade = result.grade;
                    if (partialgrade < Double.parseDouble(s.threshold) || partialgrade > Double.parseDouble(s.limit)) {

                        check = false;
                        break;
                    }
                    if (Double.parseDouble(s.weight) > 0) {

                        maxglobalcheck = maxglobalcheck + 1;
                        if (partialgrade >= Double.parseDouble(set.global_setting.global_threshold))
                            countglobalcheck = countglobalcheck + 1;
                    }
                    authorresult += s.key + " grade = " + partialgrade + " with weight= " + s.weight + " " + result.description + "\n";


                    JsonObject temp = new JsonObject();
                    temp.addProperty("grade", partialgrade);
                    temp.addProperty("weight", s.weight);
                    temp.addProperty("description", result.description);
                    JsonArray valuesA = new JsonArray();

                    for (Information r : this.informations.get(s.key).informations) {
                        valuesA.add(new JsonPrimitive(r.value));
                        if (BuilderManager.getInstance().configuration.getIdentifier(s.key))
                            identifier.add(new JsonPrimitive(r.value));

                    }
                    JsonArray valuesB = new JsonArray();
                    for (Information r : ((Author) o).informations.get(s.key).informations) {
                        valuesB.add(new JsonPrimitive(r.value));
                        if (BuilderManager.getInstance().configuration.getIdentifier(s.key))
                            identifier.add(new JsonPrimitive(r.value));
                    }


                    JsonObject values = new JsonObject();
                    values.add("valuesA", valuesA);
                    values.add("valuesB", valuesB);

                    temp.add("values", values);
                    params.add(s.key, temp);
                    if (s.options.arw) {
                        x = new Monomial(Double.parseDouble(s.weight), partialgrade);

                    } else {

                        polinomial.add(new Monomial(Double.parseDouble(s.weight), partialgrade));

                    }

                    weights += Double.parseDouble(s.weight);
                    gradewighted += (partialgrade * Double.parseDouble(s.weight));

                } else if (this.periods.get(s.key) != null) {

                    this.periods.get(s.key).setOption(s.options);
                    ((Author) o).periods.get(s.key).setOption(s.options);
                    partialgrade = (double) this.periods.get(s.key).Similarity(((Author) o).periods.get(s.key));

                    if (partialgrade < Double.parseDouble(s.threshold) || partialgrade > Double.parseDouble(s.limit)) {

                        check = false;
                        break;
                    }
                    if (Double.parseDouble(s.weight) > 0) {

                        maxglobalcheck = maxglobalcheck + 1;
                        if (partialgrade >= Double.parseDouble(set.global_setting.global_threshold))
                            countglobalcheck = countglobalcheck + 1;
                    }
                    authorresult += s.key + " grade = " + partialgrade + " with weight= " + s.weight + "\n";


                    JsonObject temp = new JsonObject();
                    temp.add("grade", new JsonPrimitive(partialgrade));
                    temp.add("weight", new JsonPrimitive(s.weight));
                    JsonArray valuesA = new JsonArray();

                    for (Period r : this.periods.get(s.key).periods) {
                        valuesA.add(new JsonPrimitive(r.value));
                        if (BuilderManager.getInstance().configuration.getIdentifier(s.key))
                            identifier.add(new JsonPrimitive(r.value));

                    }
                    JsonArray valuesB = new JsonArray();
                    for (Period r : ((Author) o).periods.get(s.key).periods) {
                        valuesB.add(new JsonPrimitive(r.value));
                        if (BuilderManager.getInstance().configuration.getIdentifier(s.key))
                            identifier.add(new JsonPrimitive(r.value));
                    }

                    JsonObject values = new JsonObject();
                    values.add("valuesA", valuesA);
                    values.add("valuesB", valuesB);

                    temp.add("values", values);
                    params.add(s.key, temp);

                    if (s.options.arw) {
                        x = new Monomial(Double.parseDouble(s.weight), partialgrade);

                    } else {

                        polinomial.add(new Monomial(Double.parseDouble(s.weight), partialgrade));

                    }

                    weights += Double.parseDouble(s.weight);
                    gradewighted += (partialgrade * Double.parseDouble(s.weight));

                }

            }


            if (x != null) {
                weights = weights - x.variable;
                gradewighted = gradewighted - x.getProduct();
                x.variable = MathUtils.getDynamicWeight(polinomial, x, Double.parseDouble(set.global_setting.global_threshold) - 0.01);
                weights = weights + x.variable;
                gradewighted = gradewighted + x.getProduct();

            }

            // global check condition


            if ((gradewighted / weights) < Double.parseDouble(set.global_setting.global_threshold) || (maxglobalcheck > 0 && ((100 / maxglobalcheck) * countglobalcheck) < Double.parseDouble(set.global_setting.global_checks))) {

                check = false;
            }


            ////
            jsonObject.add("identifier", identifier);
            jsonObject.add("params", params);
            JsonObject finalvalue = new JsonObject();


            authorresult += "Similarity of Calculated SimilarityCascade " + (gradewighted / weights) + "\n";
            Result result = new Result(gradewighted / weights, authorresult, check);
            result.shortoutput = identifier;
            finalvalue.addProperty("grade", result.grade);
            finalvalue.addProperty("sum_weights", weights);
            finalvalue.addProperty("sum_grades", gradewighted);
            finalvalue.addProperty("description", authorresult);
            jsonObject.add("similarity", finalvalue);
            result.output = jsonObject;
            if (gradewighted < 0.0) {
                result.grade = 0.0;
            }
            results.add(result);
        }
        return results;
    }

    public void printAuthor() {

        for (Settings set : BuilderManager.getInstance().settings) {
            for (Setting s : set.param) {
                System.out.println("-----------------------------------------------------------------------------------");
                if (this.resources.get(s.key) != null) {
                    System.out.println("| " + s.key + " | ");
                    for (Resource res : this.resources.get(s.key).resources) {
                        System.out.println("| - " + res.value);
                    }


                }
                if (this.informations.get(s.key) != null) {
                    System.out.println("| " + s.key + " | ");
                    for (Information info : this.informations.get(s.key).informations) {
                        System.out.println("| - " + info.value);
                    }
                }
                if (this.periods.get(s.key) != null) {
                    System.out.println("| " + s.key + " | ");
                    for (Period per : this.periods.get(s.key).periods) {
                        System.out.println("| - " + per.value);
                    }
                }
            }
        }
    }

    public void setAuthorBrunnable(Author authorBrunnable) {
        this.authorBrunnable = authorBrunnable;
    }

    @Override
    public void run() {


        Similarity(authorBrunnable);
    }
}
