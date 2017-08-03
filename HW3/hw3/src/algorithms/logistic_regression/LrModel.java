package algorithms.logistic_regression;

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
public class LrModel implements Model {

    private double[] weights;
    private List<String> vocabulary;


    LrModel(int weightsLength) {
        this.weights = new double[weightsLength];
    }

    void setVocabulary(List<String> vocabulary) {
        this.vocabulary = new ArrayList<>(vocabulary);
    }

    void setWeights(double[] weights) {
        this.weights = weights;
    }

    @Override
    public int test(Document document) {
        int x[] = new int[vocabulary.size()];
        for (int i = 0; i < x.length; i++) {
            x[i] = Collections.frequency(document.getTerms(), vocabulary.get(i));
        }

        double pr = weights[0];

        for (int i = 1; i <= x.length; i++) {
            pr += (weights[i] * x[i - 1]);
        }

        return (pr > 0) ? Document.Class.HAM : Document.Class.SPAM;
    }
}
