package contracts;

import models.DataSet;
import models.Node;

/**
 * hw2
 * Author:      Swapnil Bhoite
 * B-Number:    XXX
 */
public interface DecisionTree {

    void train(DataSet trainingDataSet);

    double validate(DataSet validationDataSet);

    double test(DataSet testingDataSet);

    DecisionTree clone();

    void printTree();

    Node getRoot();

    void setRoot(Node root);
}
