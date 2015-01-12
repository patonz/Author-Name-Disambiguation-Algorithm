package core;

import exception.ResultSearchNullException;
import semantic.Author;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Leonardo on 12/01/2015.
 */
public class Search {

    private ArrayList<Author> result = new ArrayList<Author>();
    private String input;
    private String timestamp;


    public String getTimestamp() {
        return timestamp;
    }

    public void searchByString(String input) {

        this.input = input;

        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy h:mm:ss a");
        this.timestamp = sdf.format(date);

        for (Author author : DataManager.getInstance().dataAuthor.bindings) {
            if (author.label.value.toLowerCase().contains(this.input.toLowerCase()) || author.familyName.value.toLowerCase().contains(this.input.toLowerCase()) || author.givenName.value.toLowerCase().contains(this.input.toLowerCase())) {
                this.result.add(author);
            }

        }
    }


    public void printResult() {


        System.out.println("Parameter: " + this.input);
        if (result != null) {
            if (result.isEmpty()) {
                System.out.println("Author not Found\n");
                return;
            }
            System.out.println("Authors found: "+result.size());
            for (int i = 0; i< result.size(); i++) {
                System.out.println(i+")"+result.get(i).label.value);
            }
            System.out.println();
        } else {
            throw new ResultSearchNullException();
        }
    }


}
