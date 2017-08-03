package tree_generators;

import contracts.DecisionTree;
import models.DataSet;
import models.Node;

import java.util.*;

/**
 * hw2
 * Author:      Swapnil Bhoite
 * B-Number:    XXX
 */
public class VarianceImpurityHeuristic implements DecisionTree {

    private Node root;
    private List<String> attributes;

    @Override
    public void train(DataSet trainingDataSet) {
        attributes = new ArrayList<>();
        Collections.addAll(attributes, trainingDataSet.getAttributes());
        root = id3(trainingDataSet);
    }

    @Override
    public double validate(DataSet validationDataSet) {
        int targetAttribute = validationDataSet.getTargetAttribute();
        int total = validationDataSet.getData().size();
        int correctPredictions = 0;
        for (String[] row : validationDataSet.getData()) {
            String actualValue = row[targetAttribute];
            String predictedValue = predict(row, root);
            if (actualValue.equals(predictedValue)) {
                correctPredictions++;
            }
        }
        return ((double) correctPredictions * 100 / total);
    }

    @Override
    public double test(DataSet testingDataSet) {
        int targetAttribute = testingDataSet.getTargetAttribute();
        int total = testingDataSet.getData().size();
        int correctPredictions = 0;
        for (String[] row : testingDataSet.getData()) {
            String actualValue = row[targetAttribute];
            String predictedValue = predict(row, root);
            if (actualValue.equals(predictedValue)) {
                correctPredictions++;
            }
        }
        double percent = (double) correctPredictions * 100 / total;
        System.out.println("Accuracy: " + correctPredictions + "/" + total + " = (" + percent + "%)");
        return percent;
    }

    @SuppressWarnings("MethodDoesntCallSuperMethod")
    @Override
    public DecisionTree clone() {
        VarianceImpurityHeuristic newTree = new VarianceImpurityHeuristic();
        newTree.root = null;
        if (this.root != null) {
            newTree.root = this.root.clone();
        }
        newTree.attributes = new ArrayList<>(this.attributes);
        return newTree;
    }

    @Override
    public void printTree() {
        Node.setAttributes(attributes);
        root.print();
        System.out.println();
    }

    @Override
    public Node getRoot() {
        return root;
    }

    @Override
    public void setRoot(Node root) {
        this.root = root;
    }

    private String predict(String[] row, Node current) {
        if (current.isLeaf()) {
            return current.getAttributeLabel();
        } else {
            return predict(row, current.getChildren().get(row[current.getDecisionAttribute()]));
        }
    }

    private Node id3(DataSet trainingDataSet) {
        /* Create a Root node for the tree */
        Node node = new Node();

        /* If all Examples are positive, Return the single-node tree Root, with label = + */
        /* If all Examples are negative, Return the single-node tree Root, with label = - */
        int examplesCount = trainingDataSet.getData().size();
        Map<String, Integer> count = new HashMap<>();
        int targetAttribute = trainingDataSet.getTargetAttribute();
        for (String value : trainingDataSet.getAttributeDistinctValues().get(targetAttribute)) {
            count.put(value, 0);
        }
        for (String[] row : trainingDataSet.getData()) {
            count.put(row[targetAttribute], count.get(row[targetAttribute]) + 1);
        }
        for (String value : count.keySet()) {
            if (count.get(value) == examplesCount) {
                node.setLeaf(true);
                node.setAttributeLabel(value);
                return node;
            }
        }

        /* If Attributes is empty, Return the single-node tree Root,
        with label = most common value of Target attribute in Examples */
        if (trainingDataSet.getAttributesCount() == 1) {
            String attributeLabel = getMostCommonValue(trainingDataSet);
            node.setLeaf(true);
            node.setAttributeLabel(attributeLabel);
            return node;
        }

        /* decisionAttribute =  the attribute from Attributes that best classifies Examples */
        int decisionAttribute = getBestClassifier(trainingDataSet);
        node.setLeaf(false);

        /* The decision attribute for Root = decisionAttribute */
        node.setDecisionAttribute(attributes.indexOf(trainingDataSet.getAttributes()[decisionAttribute]));

        /* For each possible value, vi, of decisionAttribute,
        Add a new tree branch below Root, corresponding to the test decisionAttribute = vi
        Let Examples-vi, be the subset of Examples that have value vi for A */
        for (String vi : trainingDataSet.getAttributeDistinctValues().get(decisionAttribute)) {
            DataSet dataSetFiltered = trainingDataSet.filter(decisionAttribute, vi);
            if (dataSetFiltered.getData().isEmpty()) {
            /* If Examples-vi is empty, then below this new branch
            add a leaf node with label = most common value of Target attribute in Examples  */
                Node leaf = new Node();
                leaf.setLeaf(true);
                leaf.setAttributeLabel(getMostCommonValue(trainingDataSet));
                leaf.setParent(node);
                leaf.setParentLink(vi);
                node.getChildren().put(vi, leaf);
            } else {
            /*  Get Attributes - decisionAttribute */
                dataSetFiltered.filter(decisionAttribute);
                Node childNode = id3(dataSetFiltered);
                childNode.setParent(node);
                childNode.setParentLink(vi);
                node.getChildren().put(vi, childNode);
            }
        }

        return node;
    }

    private String getMostCommonValue(DataSet trainingDataSet) {
        Map<String, Integer> count = new HashMap<>();
        int targetAttribute = trainingDataSet.getTargetAttribute();
        for (String value : trainingDataSet.getAttributeDistinctValues().get(targetAttribute)) {
            count.put(value, 0);
        }
        for (String[] row : trainingDataSet.getData()) {
            count.put(row[targetAttribute], count.get(row[targetAttribute]) + 1);
        }
        Integer max = Collections.max(count.values());
        for (String value : count.keySet()) {
            if (count.get(value).equals(max)) {
                return value;
            }
        }
        return null;
    }

    private int getBestClassifier(DataSet trainingDataSet) {
        List<Double> gainList = new ArrayList<>();
        double overallVarianceImpurity = getVarianceImpurity(trainingDataSet);
        for (int i = 0; i < trainingDataSet.getAttributesCount() - 1; i++) {
            double gain = getGain(trainingDataSet, overallVarianceImpurity, i);
            gainList.add(gain);
        }
        return gainList.indexOf(Collections.max(gainList));
    }

    private double getVarianceImpurity(DataSet trainingDataSet) {
        Map<String, Integer> count = new HashMap<>();
        int targetAttribute = trainingDataSet.getTargetAttribute();
        for (String value : trainingDataSet.getAttributeDistinctValues().get(targetAttribute)) {
            count.put(value, 0);
        }
        int total = 0;
        for (String[] row : trainingDataSet.getData()) {
            count.put(row[targetAttribute], count.get(row[targetAttribute]) + 1);
            total++;
        }
        if (total == 0) {
            return 0.0;
        }

        double result = 1;
        for (String value : count.keySet()) {
            result *= (double) count.get(value) / total;
        }
        return result;
    }

    private double getVarianceImpurity(DataSet trainingDataSet, int attribute, String attributeValue) {
        Map<String, Integer> count = new HashMap<>();
        int targetAttribute = trainingDataSet.getTargetAttribute();
        for (String value : trainingDataSet.getAttributeDistinctValues().get(targetAttribute)) {
            count.put(value, 0);
        }
        int total = 0;
        for (String[] row : trainingDataSet.getData()) {
            if (row[attribute].equals(attributeValue)) {
                count.put(row[targetAttribute], count.get(row[targetAttribute]) + 1);
                total++;
            }
        }
        if (total == 0) {
            return 0.0;
        }

        double result = 1;
        for (String value : count.keySet()) {
            result *= (double) count.get(value) / total;
        }
        return result;
    }

    private double getGain(DataSet trainingDataSet, double overallVarianceImpurity, int attribute) {
        Map<String, Integer> count = new HashMap<>();
        for (String value : trainingDataSet.getAttributeDistinctValues().get(attribute)) {
            count.put(value, 0);
        }
        int total = 0;
        for (String[] row : trainingDataSet.getData()) {
            count.put(row[attribute], count.get(row[attribute]) + 1);
            total++;
        }
        if (total == 0) {
            return 0.0;
        }
        double result = overallVarianceImpurity;
        for (String value : count.keySet()) {
            result -= ((double) count.get(value) / total) * getVarianceImpurity(trainingDataSet, attribute, value);
        }
        return result;
    }
}
