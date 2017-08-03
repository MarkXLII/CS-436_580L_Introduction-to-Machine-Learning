package algorithms;

import models.Document;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ArffSaver;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * hw3
 * Author:      Swapnil Bhoite
 * B-Number:    XXX
 */
public class Utils {

    public static List<String> extractVocabulary(List<Document> dataSet) {
        Set<String> vocabulary = new HashSet<>();
        for (Document document : dataSet) {
            vocabulary.addAll(document.getTerms());
        }
        return new ArrayList<>(vocabulary);
    }

    public static Instances convertToArff(List<Document> dataSet, List<String> vocabulary, String fileName) {
        int dataSetSize = dataSet.size();
        /* Create features */
        ArrayList<Attribute> attributes = new ArrayList<>();
        for (int i = 0; i < vocabulary.size(); i++) {
            attributes.add(new Attribute("word_" + i));
        }
        Attribute classAttribute = new Attribute("Class");
        attributes.add(classAttribute);

        /* Add examples */
        System.out.println("Building instances...");
        Instances trainingDataSet = new Instances(fileName, attributes, 0);
        for (int k = 0; k < dataSetSize; k++) {
            Document document = dataSet.get(k);
            Instance example = new DenseInstance(attributes.size());
            for (int i = 0; i < vocabulary.size(); i++) {
                String word = vocabulary.get(i);
                example.setValue(i, Collections.frequency(document.getTerms(), word));
            }
            example.setValue(classAttribute, document.getDocumentClass());
            trainingDataSet.add(example);
            int progress = (int) ((k * 100.0) / dataSetSize);
            System.out.printf("\rPercent completed: %3d%%", progress);
        }
        trainingDataSet.setClass(classAttribute);
        System.out.println();

        System.out.println("Writing to file ...");
        try {
            ArffSaver saver = new ArffSaver();
            saver.setInstances(trainingDataSet);
            saver.setFile(new File(fileName));
            saver.writeBatch();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return trainingDataSet;
    }
}
