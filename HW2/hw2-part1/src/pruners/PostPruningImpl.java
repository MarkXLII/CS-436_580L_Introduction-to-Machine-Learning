package pruners;

import contracts.DecisionTree;
import contracts.PostPruning;
import models.DataSet;
import models.Node;

import java.util.*;

/**
 * hw2
 * Author:      Swapnil Bhoite
 * B-Number:    XXX
 */
public class PostPruningImpl implements PostPruning {

    private final DataSet trainingDataSet;
    private final DataSet validationDataSet;
    private final Random random;

    public PostPruningImpl(DataSet trainingDataSet, DataSet validationDataSet) {
        this.trainingDataSet = trainingDataSet;
        this.validationDataSet = validationDataSet;
        this.random = new Random(System.currentTimeMillis());
    }

    @Override
    public DecisionTree prune(DecisionTree decisionTree, int l, int k) {
        DecisionTree decisionTreeBest = decisionTree;
        double accuracyBest = decisionTreeBest.validate(validationDataSet);
        for (int i = 0; i < l; i++) {
            /* Copy the tree D into a new tree DDash */
            DecisionTree decisionTreeDDash = decisionTree.clone();

            /* M = a random number between 1 and K */
            int m = random.nextInt(k);
            for (int j = 0; j <= m; j++) {
                /* Let N denote the number of non-leaf nodes in the decision
                tree DDash . Order the nodes in DDash from 1 to N */
                List<Node> nonLeafNodes = getNonLeafNodes(decisionTreeDDash);
                int n = nonLeafNodes.size();
                if (nonLeafNodes.isEmpty()) {
                    continue;
                }

                /* P = a random number between 1 and N */
                int p = random.nextInt(n);

                /* Replace the subtree rooted at P in DDash by a leaf node.
                Assign the majority class of the subset of the data
                at P to the leaf node */
                Node leaf = new Node();
                Node nodeAtP = nonLeafNodes.get(p);
                leaf.setAttributeLabel(getMostCommonValue(nodeAtP));
                leaf.setLeaf(true);
                leaf.setParent(nodeAtP.getParent());
                leaf.setParentLink(nodeAtP.getParentLink());
                if (nodeAtP.getParent() == null) {
                    decisionTreeDDash.setRoot(leaf);
                } else {
                    nodeAtP.getParent().getChildren().put(nodeAtP.getParentLink(), leaf);
                }
            }

            /* Evaluate the accuracy of DDash on the validation set */
            double accuracy = decisionTreeDDash.validate(validationDataSet);

            /* if DDash is more accurate than DBest then DBest = DDash */
            //System.out.println("Prev: " + accuracyBest + "\tNew: " + accuracy);
            if (accuracy > accuracyBest) {
                decisionTreeBest = decisionTreeDDash;
                accuracyBest = accuracy;
            }
        }
        return decisionTreeBest;
    }

    private List<Node> getNonLeafNodes(DecisionTree decisionTreeDDash) {
        List<Node> result = new ArrayList<>();
        Node root = decisionTreeDDash.getRoot();
        Queue<Node> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            Node node = queue.poll();
            if (!node.isLeaf()) {
                result.add(node);
            }
            for (Node child : node.getChildren().values()) {
                queue.add(child);
            }
        }
        return result;
    }

    private String getMostCommonValue(Node root) {
        Map<String, Integer> count = new HashMap<>();
        int targetAttribute = trainingDataSet.getTargetAttribute();
        for (String value : trainingDataSet.getAttributeDistinctValues().get(targetAttribute)) {
            count.put(value, 0);
        }

        Queue<Node> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            Node node = queue.poll();
            if (node.isLeaf()) {
                count.put(node.getAttributeLabel(), count.get(node.getAttributeLabel()) + 1);
            }
            for (Node child : node.getChildren().values()) {
                queue.add(child);
            }
        }

        Integer max = Collections.max(count.values());
        for (String value : count.keySet()) {
            if (count.get(value).equals(max)) {
                return value;
            }
        }
        return null;
    }
}
