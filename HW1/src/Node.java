import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * hw1
 * Author:      Swapnil Bhoite
 * B-Number:    XXX
 */
class Node {

    private static List<String> attributes;
    private boolean isLeaf;
    private int decisionAttribute;
    private String attributeLabel;
    private Map<String, Node> children;

    Node() {
        isLeaf = true;
        decisionAttribute = -1;
        attributeLabel = "";
        children = new HashMap<>();
    }

    static void setAttributes(List<String> attributes) {
        Node.attributes = new ArrayList<>(attributes);
    }

    boolean isLeaf() {
        return isLeaf;
    }

    void setLeaf(boolean leaf) {
        isLeaf = leaf;
    }

    int getDecisionAttribute() {
        return decisionAttribute;
    }

    void setDecisionAttribute(int decisionAttribute) {
        this.decisionAttribute = decisionAttribute;
    }

    String getAttributeLabel() {
        return attributeLabel;
    }

    void setAttributeLabel(String attributeLabel) {
        this.attributeLabel = attributeLabel;
    }

    Map<String, Node> getChildren() {
        return children;
    }

    @Override
    public String toString() {
        return "Node{" +
                "isLeaf=" + isLeaf +
                ", decisionAttribute=" + decisionAttribute +
                ", attributeLabel=" + attributeLabel +
                ", children=" + children +
                '}';
    }

    void print() {
        print("");
    }

    private void print(String prefix) {
        if (!isLeaf()) {
            for (String value : children.keySet()) {
                Node node = children.get(value);
                System.out.print("\n" + prefix + attributes.get(getDecisionAttribute()) + " = " + value + " : ");
                if (node.isLeaf()) {
                    node.print("");
                } else {
                    node.print(prefix + "| ");
                }
            }
        } else {
            System.out.print(prefix + attributeLabel);
        }
    }
}
