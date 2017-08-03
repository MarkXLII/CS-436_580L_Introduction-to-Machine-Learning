package algorithms.perceptrons;

import contracts.Model;
import models.Document;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * hw3
 * Author:      Swapnil Bhoite
 * B-Number:    XXX
 */
public class PerceptronModel implements Model {

    private double[] weights;
    private List<String> vocabulary;

    void setWeights(double[] weights) {
        this.weights = weights;
    }

    void setVocabulary(List<String> vocabulary) {
        this.vocabulary = new ArrayList<>(vocabulary);
    }

    @Override
    public int test(Document document) {
        int x[] = new int[vocabulary.size()];
        for (int i = 0; i < x.length; i++) {
            x[i] = Collections.frequency(document.getTerms(), vocabulary.get(i));
        }

        double o = weights[0];
        for (int i = 1; i < x.length; i++) {
            o += (x[i - 1] * weights[i]);
        }

        return (o > 0) ? Document.Class.HAM : Document.Class.SPAM;
    }

}
