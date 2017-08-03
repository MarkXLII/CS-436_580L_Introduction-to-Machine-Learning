package algorithms.perceptrons;

import algorithms.Utils;
import contracts.Model;
import contracts.TrainingAlgorithm;
import models.Document;

import java.util.Collections;
import java.util.List;

/**
 * iterations
 * Author:      Swapnil Bhoite
 * B-Number:    XXX
 */
public class SimplePerceptrons implements TrainingAlgorithm {

    private static final int DUMMY_THRESHOLD = 1;
    private int iterations;
    private double learningRate;

    public SimplePerceptrons(int iterations, double learningRate) {
        this.iterations = iterations;
        this.learningRate = learningRate;
    }

    @Override
    public Model train(List<Document> trainingDataSet) {
        List<String> vocabulary = Utils.extractVocabulary(trainingDataSet);
        int n = vocabulary.size();
        PerceptronModel model = new PerceptronModel();
        model.setVocabulary(vocabulary);

        int data[][] = constructDataMatrix(trainingDataSet, vocabulary);
        double w[] = new double[n + 1];

        int classColumn = n + 1;
        for (int k = 0; k < iterations; k++) {
            for (int[] x : data) {
                int o = perceptronOutput(x, w);
                int t = x[classColumn];
                if (o != t) {
                    for (int j = 0; j < w.length; j++) {
                        w[j] += learningRate * ((t - o) * x[j]);
                    }
                }
            }
        }

        model.setWeights(w);

        return model;
    }

    private int[][] constructDataMatrix(List<Document> trainingDataSet, List<String> vocabulary) {
        int data[][] = new int[trainingDataSet.size()][vocabulary.size() + 2];
        for (int i = 0; i < trainingDataSet.size(); i++) {
            Document document = trainingDataSet.get(i);
            data[i][0] = DUMMY_THRESHOLD;
            for (int j = 1; j <= vocabulary.size(); j++) {
                data[i][j] = Collections.frequency(document.getTerms(), vocabulary.get(j - 1));
            }
            data[i][vocabulary.size() + 1] = document.getDocumentClass();
        }
        return data;
    }

    private int perceptronOutput(int x[], double w[]) {
        double o = 0;
        for (int i = 0; i < x.length - 1; i++) {
            o += (x[i] * w[i]);
        }
        return (o > 0) ? Document.Class.HAM : Document.Class.SPAM;
    }
}
