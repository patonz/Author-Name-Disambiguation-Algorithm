package semantic;

import core.ElaborateManager;
import core.Levenshtein;

/**
 * Created by Leonardo on 08/01/2015.
 */
public class FamilyName extends semantic.Key {
    @Override
    public double Similarity(Key obj) {


        int distance = Levenshtein.distance(this.value, obj.value);
        // % della distanza sul massimo dei caratteri = quanto sono DIVERSI
        // il risultato che cerco è l'esatto inverso = quanto sono uguali
       // System.out.println("A = " + this.value+" B = "+obj.value+" distance "+distance);
       // System.out.println("A = "+this.value.length()+" B = "+obj.value.length());

        int miss = Math.max(this.value.length(),obj.value.length())- Math.min(this.value.length(), obj.value.length());
        int change = distance - miss;
        double grade =  100.0 - (100.0 / (ElaborateManager.maxChar(this.value, obj.value) )* distance + change);
        return grade >= 0 ? grade : 0.0;
    }
}
