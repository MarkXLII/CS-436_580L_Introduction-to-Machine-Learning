/**
 * hw1
 * Author:      Swapnil Bhoite
 * B-Number:    XXX
 */
public class Main {

    //DATA SETS
    private static final String DATA_SET_1_TRAINING = "input/dataset_1/training_set.csv";
    private static final String DATA_SET_1_VALIDATION = "input/dataset_1/validation_set.csv";
    private static final String DATA_SET_1_TEST = "input/dataset_1/test_set.csv";
    private static final String DATA_SET_2_TRAINING = "input/dataset_2/training_set.csv";
    private static final String DATA_SET_2_VALIDATION = "input/dataset_2/validation_set.csv";
    private static final String DATA_SET_2_TEST = "input/dataset_2/test_set.csv";

    public static void main(String args[]) {

        if (args.length != 4) {
            System.err.println("Missing operands!");
            System.err.println("Usage: java -jar program.jar <training-set> <validation-set> <test-set> <to-print>\n"
                    + "to-print:{yes/no}");
            System.exit(1);
        }

        System.out.println("Using Information Gain Heuristic");
        DecisionTree decisionTreeIgh = new InformationGainHeuristic();
        decisionTreeIgh.train(new DataSet(args[0]));
        decisionTreeIgh.validate(new DataSet(args[1]));
        decisionTreeIgh.test(new DataSet(args[2]));
        if (args[3].equalsIgnoreCase("yes")) {
            decisionTreeIgh.printTree();
        }

        System.out.println();

        System.out.println("Using Variance Impurity Heuristic");
        DecisionTree decisionTreeVih = new VarianceImpurityHeuristic();
        decisionTreeVih.train(new DataSet(args[0]));
        decisionTreeVih.validate(new DataSet(args[1]));
        decisionTreeVih.test(new DataSet(args[2]));
        if (args[3].equalsIgnoreCase("yes")) {
            decisionTreeVih.printTree();
        }
    }
}
