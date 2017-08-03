package contracts;

import models.Document;

import java.util.List;

/**
 * hw3
 * Author:      Swapnil Bhoite
 * B-Number:    XXX
 */
public interface TrainingAlgorithm {

    Model train(List<Document> trainingDataSet);
}
