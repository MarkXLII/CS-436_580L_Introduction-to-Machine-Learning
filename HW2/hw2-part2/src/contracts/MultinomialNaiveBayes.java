package contracts;

import models.Classifier;
import models.Document;

import java.util.List;

/**
 * hw2-part2
 * Author:      Swapnil Bhoite
 * B-Number:    XXX
 */
public interface MultinomialNaiveBayes {

    Classifier train(List<Document> trainingDataSet);

    int test(Classifier classifier, Document document);
}
