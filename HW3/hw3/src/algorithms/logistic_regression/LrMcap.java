package algorithms.logistic_regression;

import contracts.Model;
import contracts.TrainingAlgorithm;
import models.Document;

import java.util.*;

/**
 * hw3
 * Author:      Swapnil Bhoite
 * B-Number:    XXX
 */
public class LrMcap implements TrainingAlgorithm {

    private static final int DUMMY_THRESHOLD = 1;
    private static final Random RANDOM = new Random(System.currentTimeMillis());
    private double learningRate;
    private double lambda;
    private double convergenceThreshold;

    public LrMcap(double learningRate, double lambda, double convergenceThreshold) {
        this.learningRate = learningRate;
        this.lambda = lambda;
        this.convergenceThreshold = convergenceThreshold;
    }

    @Override
    public Model train(List<Document> trainingDataSet) {
        int m = trainingDataSet.size();
        List<String> vocabulary = extractVocabulary(trainingDataSet);
        int n = vocabulary.size();
        LrModel model = new LrModel(n + 1);
        model.setVocabulary(vocabulary);

        int data[][] = constructDataMatrix(trainingDataSet, vocabulary);
        double pr[] = new double[m];
        for (int i = 0; i < pr.length; i++) {
            pr[i] = RANDOM.nextDouble();
        }
        double w[] = new double[n + 1];
        for (int i = 0; i < w.length; i++) {
            w[i] = RANDOM.nextDouble();
        }

        double deltaUpdate = Double.MAX_VALUE;
        while (Math.abs(deltaUpdate) > convergenceThreshold) {
            deltaUpdate = 0;
            for (int i = 0; i < data.length; i++) {
                pr[i] = computePr(data[i], w);
            }
            double dw[] = new double[n + 1];
            for (int i = 0; i < w.length; i++) {
                for (int j = 0; j < data.length; j++) {
                    double delta = data[j][i] * (data[j][n + 1] - pr[j]);
                    dw[i] += delta;
                    deltaUpdate += delta;
                }
            }
            for (int i = 0; i < w.length; i++) {
                w[i] += learningRate * (dw[i] - (lambda * w[i]));
            }
        }

        model.setWeights(w);

        return model;
    }

    private List<String> extractVocabulary(List<Document> trainingDataSet) {
        Set<String> vocabulary = new HashSet<>();
        for (Document document : trainingDataSet) {
            vocabulary.addAll(document.getTerms());
        }
        return new ArrayList<>(vocabulary);
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

    private double computePr(int[] x, double[] w) {
        double pr = w[0];

        for (int i = 1; i < x.length - 1; i++) {
            pr += (w[i] * x[i]);
        }

        return (pr > 0) ? Document.Class.HAM : Document.Class.SPAM;
    }
}
