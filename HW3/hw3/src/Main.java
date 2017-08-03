import algorithms.Utils;
import algorithms.logistic_regression.LrMcap;
import algorithms.perceptrons.SimplePerceptrons;
import contracts.Model;
import contracts.TrainingAlgorithm;
import models.Document;
import models.Stopwords;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/**
 * hw3
 * Author:      Swapnil Bhoite
 * B-Number:    XXX
 */
public class Main {

    private static final String DIR_HAM = "/ham";
    private static final String DIR_SPAM = "/spam";

    public static void main(String args[]) {

        if (args.length < 3) {
            runFormatError();
        }

        String trainingDataSetDirPath = args[1];
        String testingDataSetDirPath = args[2];
        boolean skipStopwords = args[3].equalsIgnoreCase("yes");

        System.out.println("Reading training data set...");
        List<Document> trainingDataSet = new ArrayList<>();
        readDataSet(trainingDataSetDirPath, trainingDataSet, skipStopwords, Document.Class.SPAM);
        readDataSet(trainingDataSetDirPath, trainingDataSet, skipStopwords, Document.Class.HAM);

        System.out.println("Reading testing data set...");
        List<Document> testingDataSet = new ArrayList<>();
        readDataSet(testingDataSetDirPath, testingDataSet, skipStopwords, Document.Class.SPAM);
        readDataSet(testingDataSetDirPath, testingDataSet, skipStopwords, Document.Class.HAM);

        if (args[0].equalsIgnoreCase("lrmcap")) {
            if (args.length != 7) {
                runFormatError();
            }
            System.out.println("\nLogistic Regression...");
            double learningRate = Double.parseDouble(args[4]);
            double lambda = Double.parseDouble(args[5]);
            double convergenceThreshold = Double.parseDouble(args[6]);
            runLrMcap(trainingDataSet, testingDataSet, skipStopwords, learningRate, lambda, convergenceThreshold);
            return;
        }

        if (args[0].equalsIgnoreCase("perceptrons")) {
            if (args.length != 6) {
                runFormatError();
            }
            System.out.println("\nPerceptrons...");
            int iterations = Integer.parseInt(args[4]);
            double learningRate = Double.parseDouble(args[5]);
            runSimplePerceptron(trainingDataSet, testingDataSet, skipStopwords, iterations, learningRate);
            return;
        }

        if (args[0].equalsIgnoreCase("weka-convert-arff")) {
            if (args.length != 6) {
                runFormatError();
            }
            System.out.println("\nNeural Networks...");
            List<String> vocabulary = Utils.extractVocabulary(trainingDataSet);
            System.out.println("creating training.arff...");
            Utils.convertToArff(trainingDataSet, vocabulary, args[4]);
            System.out.println("creating testing.arff...");
            Utils.convertToArff(testingDataSet, vocabulary, args[5]);
            return;
        }

        System.out.println();
    }

    private static void runFormatError() {
        System.err.println("Missing operands!");
        System.err.println("Usage: java -cp program.jar Main <algorithm> " +
                "<training-set-dir> <test-set-dir> <skip-stopwords> " +
                "<iterations> <learning rate> <lambda> " +
                "<path for training.arff> <path for testing.arff>" +
                "\n algorithm:{lrmcap/perceptrons/weka-convert-arff}" +
                "\n skip-stopwords:{yes/no}" +
                "\n iterations:{integer for number of iterations in perceptrons training}" +
                "\n learning rate:{double for learning rate in lrmcap, perceptrons training}" +
                "\n lambda:{double for strength of the regularization term in lrmcap training}" +
                "\n convergenceThreshold:{double for convergence threshold in lrmcap training}" +
                "");
        System.exit(1);
    }

    private static void runLrMcap(List<Document> trainingDataSet,
                                  List<Document> testingDataSet,
                                  boolean skipStopwords,
                                  double learningRate,
                                  double lambda,
                                  double convergenceThreshold) {
        TrainingAlgorithm trainingAlgorithm = new LrMcap(learningRate, lambda, convergenceThreshold);
        System.out.println("Training classifier with" +
                " learning rate = " + learningRate
                + ", lambda = " + lambda
                + ", skipping stopwords = " + (skipStopwords ? "yes" : "no"));
        Model model = trainingAlgorithm.train(trainingDataSet);

        System.out.println("Testing classifier...");
        int correctPredictions = 0;
        int total = testingDataSet.size();
        for (Document document : testingDataSet) {
            int prediction = model.test(document);
            if (document.getDocumentClass() == prediction) {
                correctPredictions++;
            }
        }
        System.out.println("Accuracy: (" + correctPredictions + "/" + total + ") = "
                + ((double) correctPredictions / total * 100) + "%");
        System.out.println();
    }

    private static void runSimplePerceptron(List<Document> trainingDataSet,
                                            List<Document> testingDataSet,
                                            boolean skipStopwords,
                                            int iterations,
                                            double learningRate) {
        TrainingAlgorithm trainingAlgorithm = new SimplePerceptrons(iterations, learningRate);
        System.out.println("Training classifier with" +
                " iterations = " + iterations +
                ", learning rate = " + learningRate
                + ", skipping stopwords = " + (skipStopwords ? "yes" : "no"));
        Model model = trainingAlgorithm.train(trainingDataSet);

        System.out.println("Testing classifier...");
        int correctPredictions = 0;
        int total = testingDataSet.size();
        for (Document document : testingDataSet) {
            int prediction = model.test(document);
            if (document.getDocumentClass() == prediction) {
                correctPredictions++;
            }
        }
        System.out.println("Accuracy: (" + correctPredictions + "/" + total + ") = "
                + ((double) correctPredictions / total * 100) + "%");
        System.out.println();

    }

    private static void readDataSet(String dataSetDirPath,
                                    List<Document> dataSet,
                                    boolean skipStopwords,
                                    int documentClass) {
        File trainingDataSetSpamDir;
        if (documentClass == Document.Class.SPAM) {
            trainingDataSetSpamDir = new File(dataSetDirPath + DIR_SPAM);
        } else {
            trainingDataSetSpamDir = new File(dataSetDirPath + DIR_HAM);
        }
        if (trainingDataSetSpamDir.exists() && trainingDataSetSpamDir.isDirectory()) {
            File[] spamFiles = trainingDataSetSpamDir.listFiles();
            if (spamFiles != null) {
                for (File spamFile : spamFiles) {
                    try {
                        Scanner scanner = new Scanner(new BufferedInputStream(new FileInputStream(spamFile)));
                        StringBuilder stringBuilder = new StringBuilder();
                        while (scanner.hasNext()) {
                            stringBuilder.append(scanner.nextLine().toLowerCase());
                        }
                        scanner.close();
                        Document document = new Document();
                        document.setDocumentClass(documentClass);
                        String[] split = stringBuilder.toString().split("\\s");
                        List<String> terms = new ArrayList<>();
                        Collections.addAll(terms, split);
                        if (skipStopwords) {
                            terms.removeAll(Stopwords.wordList);
                        }
                        document.setTerms(terms);
                        dataSet.add(document);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
