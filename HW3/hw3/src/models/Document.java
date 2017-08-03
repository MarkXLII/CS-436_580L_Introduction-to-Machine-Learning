package models;

import java.util.ArrayList;
import java.util.List;

/**
 * hw3
 * Author:      Swapnil Bhoite
 * B-Number:    XXX
 */
public class Document {

    private List<String> terms;
    private int documentClass;

    public List<String> getTerms() {
        return terms;
    }

    public void setTerms(List<String> terms) {
        this.terms = new ArrayList<>(terms);
    }

    public int getDocumentClass() {
        return documentClass;
    }

    public void setDocumentClass(int documentClass) {
        this.documentClass = documentClass;
    }

    public interface Class {
        int SPAM = -1;
        int HAM = 1;
    }
}
