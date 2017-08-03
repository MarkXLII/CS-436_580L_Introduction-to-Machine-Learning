import contracts.DecisionTree;
import contracts.PostPruning;
import models.DataSet;
import pruners.PostPruningImpl;

/**
 * hw2
 * Author:      Swapnil Bhoite
 * B-Number:    XXX
 */
@SuppressWarnings("unused")
public class Main {

    //DATA SETS
    private static final String DATA_SET_1_TRAINING = "input/dataset_1/training_set.csv";
    private static final String DATA_SET_1_VALIDATION = "input/dataset_1/validation_set.csv";
    private static final String DATA_SET_1_TEST = "input/dataset_1/test_set.csv";
    private static final String DATA_SET_2_TRAINING = "input/dataset_2/training_set.csv";
    private static final String DATA_SET_2_VALIDATION = "input/dataset_2/validation_set.csv";
    private static final String DATA_SET_2_TEST = "input/dataset_2/test_set.csv";
    private static final String DATA_SET_3_TRAINING = "input/dataset_3/training.csv";
    private static final String DATA_SET_3_VALIDATION = "input/dataset_3/training.csv";
    private static final String DATA_SET_3_TEST = "input/dataset_3/training.csv";

    public static void main(String args[]) {

        if (args.length != 6) {
            System.err.println("Missing operands!");
            System.err.println("Usage: java -cp program1.jar Main <l> <k> <training-set> <validation-set> <test-set> <to-print>\n"
                    + "l: integer (used in the post-pruning algorithm)\n"
                    + "k: integer (used in the post-pruning algorithm)"
                    + "to-print:{yes/no}");
            System.exit(1);
        }

        int l = Integer.parseInt(args[0]);
        int k = Integer.parseInt(args[1]);
        DataSet trainingDataSet = new DataSet(args[2]);
        DataSet validationDataSet = new DataSet(args[3]);
        DataSet testingDataSet = new DataSet(args[4]);

        PostPruning postPruning = new PostPruningImpl(trainingDataSet, validationDataSet);

        System.out.println("Using Information Gain Heuristic");
        contracts.DecisionTree decisionTreeIgh = new tree_generators.InformationGainHeuristic();
        decisionTreeIgh.train(trainingDataSet);
        decisionTreeIgh.test(testingDataSet);
        if (args[5].equalsIgnoreCase("yes")) {
            decisionTreeIgh.printTree();
        }
        System.out.println("Post Pruned version, L = " + l + " K = " + k);
        DecisionTree prunedIghTree = postPruning.prune(decisionTreeIgh, l, k);
        prunedIghTree.test(testingDataSet);
        if (args[5].equalsIgnoreCase("yes")) {
            prunedIghTree.printTree();
        }

        System.out.println();

        System.out.println("Using Variance Impurity Heuristic");
        contracts.DecisionTree decisionTreeVih = new tree_generators.VarianceImpurityHeuristic();
        decisionTreeVih.train(trainingDataSet);
        decisionTreeVih.test(testingDataSet);
        if (args[5].equalsIgnoreCase("yes")) {
            decisionTreeVih.printTree();
        }
        System.out.println("Post Pruned version, L = " + l + " K = " + k);
        DecisionTree prunedVih = postPruning.prune(decisionTreeVih, l, k);
        prunedVih.test(testingDataSet);
        if (args[5].equalsIgnoreCase("yes")) {
            prunedVih.printTree();
        }

    }
}
