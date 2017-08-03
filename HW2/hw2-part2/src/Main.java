import contracts.MultinomialNaiveBayes;
import models.Classifier;
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
 * hw2-part2
 * Author:      Swapnil Bhoite
 * B-Number:    XXX
 */
public class Main {

    private static final String DIR_HAM = "/ham";
    private static final String DIR_SPAM = "/spam";

    @SuppressWarnings("ConstantConditions")
    public static void main(String args[]) {
        if (args.length != 3) {
            System.err.println("Missing operands!");
            System.err.println("Usage: java -cp program2.jar Main <training-set-dir> <test-set-dir> <skip-stopwords>\n"
                    + "skip-stopwords:{yes/no}");
            System.exit(1);
        }

        String trainingDataSetDirPath = args[0];
        String testingDataSetDirPath = args[1];
        boolean skipStopwords = args[2].equalsIgnoreCase("yes");

        System.out.println("Reading training data set...");
        List<Document> trainingDataSet = new ArrayList<>();
        readSpams(trainingDataSetDirPath, trainingDataSet, skipStopwords);
        readHams(trainingDataSetDirPath, trainingDataSet, skipStopwords);

        System.out.println("Reading testing data set...");
        List<Document> testingDataSet = new ArrayList<>();
        readSpams(testingDataSetDirPath, testingDataSet, skipStopwords);
        readHams(testingDataSetDirPath, testingDataSet, skipStopwords);

        MultinomialNaiveBayes multinomialNaiveBayes = new MultinomialNaiveBayesImpl();
        System.out.println("Training classifier...");
        Classifier classifier = multinomialNaiveBayes.train(trainingDataSet);

        System.out.println("Testing classifier...");
        int total = testingDataSet.size();
        int correctPredictions = 0;
        for (Document document : testingDataSet) {
            if (multinomialNaiveBayes.test(classifier, document) == document.getDocumentClass()) {
                correctPredictions++;
            }
        }
        System.out.println("Accuracy: (" + correctPredictions + "/" + total + ") = "
                + ((double) correctPredictions / total * 100) + "%");

    }

    private static void readSpams(String dataSetDirPath, List<Document> dataSet, boolean skipStopwords) {
        File trainingDataSetSpamDir = new File(dataSetDirPath + DIR_SPAM);
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
                        document.setDocumentClass(Document.Class.SPAM);
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

    private static void readHams(String dataSetDirPath, List<Document> dataSet, boolean skipStopwords) {
        File trainingDataSetHamDir = new File(dataSetDirPath + DIR_HAM);
        if (trainingDataSetHamDir.exists() && trainingDataSetHamDir.isDirectory()) {
            File[] hamFiles = trainingDataSetHamDir.listFiles();
            if (hamFiles != null) {
                for (File hamFile : hamFiles) {
                    try {
                        Scanner scanner = new Scanner(new BufferedInputStream(new FileInputStream(hamFile)));
                        StringBuilder stringBuilder = new StringBuilder();
                        while (scanner.hasNext()) {
                            stringBuilder.append(scanner.nextLine().toLowerCase());
                        }
                        scanner.close();
                        Document document = new Document();
                        document.setDocumentClass(Document.Class.HAM);
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
