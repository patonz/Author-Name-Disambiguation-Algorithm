package configuration;

/**
 * Created by Leonardo on 05/02/2015.
 */
public class Option {
    public boolean type_error = false; // consider typing errors: jack almost similar to jacks ALPHA
    public double cor = 2.0; // the Cost Of Replacement for each char
    public boolean cfs = false; // ..Cointains First Spaces   Yibin and Bin  vs Bin & Binli
    public boolean mwa = false; // Multiple Words Analyzer ALPHA
    public boolean spaces = false; //Remove the spaces between words
    public boolean dots = false; // remove all dots
    public boolean arw = false; // Auto Recalibrating Weight, (Must be unique in the config. file)


    public String toString(){


        return "options:[type_error: "+type_error+", cor: "+cor+", cfs: "+cfs+", mwa: "+mwa+", spaces: "+spaces+", dots: "+dots+", arw: "+arw+"]";
    }
}
