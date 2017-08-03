/**
 * hw1
 * Author:      Swapnil Bhoite
 * B-Number:    XXX
 */
public interface DecisionTree {

    void train(DataSet trainingDataSet);

    void validate(DataSet validationDataSet);

    void test(DataSet testingDataSet);

    void printTree();
}
