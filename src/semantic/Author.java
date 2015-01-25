package semantic;

import Skelethon.Similarity;
import builder.BuilderManager;
import builder.model.*;
import configuration.Setting;
import core.ElaborateManager;
import core.Result;
import exception.SimilarTypeNotFoundException;

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
        boolean check = true;
        Double gradewighted = 0.0;
        Double weights = 0.0;
        String authorresult = "";
        for (Setting s : BuilderManager.getInstance().settings.param) {
            Double partialgrade = 0.0;
            if (this.resources.get(s.key) != null) {
                partialgrade = (double) this.resources.get(s.key).Similarity(((Author) o).resources.get(s.key));

                authorresult += s.key + " grade = " + partialgrade + " with weight= " + s.weight + "\n";
                weights += Double.parseDouble(s.weight);
                gradewighted += ((partialgrade * Double.parseDouble(s.weight)));

                if(partialgrade < Double.parseDouble(s.threshold)){
                    check = false;
                    break;
                }
            } else if (this.informations.get(s.key) != null) {
                Result result = (Result) this.informations.get(s.key).Similarity(((Author) o).informations.get(s.key));
                partialgrade = result.grade;
                authorresult += s.key + " grade = " + partialgrade + " with weight= " + s.weight + " " + result.description + "\n";

                weights += Double.parseDouble(s.weight);
                gradewighted += (partialgrade * Double.parseDouble(s.weight));
                if(partialgrade < Double.parseDouble(s.threshold)){
                    check = false;
                    break;
                }
            } else if (this.periods.get(s.key) != null) {
                partialgrade = (double) this.periods.get(s.key).Similarity(((Author) o).periods.get(s.key));
                authorresult += s.key + " grade = " + partialgrade + " with weight= " + s.weight + "\n";

                weights += Double.parseDouble(s.weight);
                gradewighted += (partialgrade * Double.parseDouble(s.weight));
                if(partialgrade < Double.parseDouble(s.threshold)){
                    check = false;
                    break;
                }
            }
        }


        authorresult += "Similarity of Calculated SimilarityCascade " + (gradewighted / weights) + "\n";
        return (gradewighted > 0.0) ? new Result(gradewighted / weights, authorresult, check) : new Result(0.0, authorresult,check);
    }

    public void printAuthor() {


        for (Setting s : BuilderManager.getInstance().settings.param) {
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

    public void setAuthorBrunnable(Author authorBrunnable) {
        this.authorBrunnable = authorBrunnable;
    }

    @Override
    public void run() {


        Similarity(authorBrunnable);
    }
}
