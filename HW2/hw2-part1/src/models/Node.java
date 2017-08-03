package models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * hw2
 * Author:      Swapnil Bhoite
 * B-Number:    XXX
 */
public class Node {

    private static List<String> attributes;
    private boolean isLeaf;
    private int decisionAttribute;
    private String attributeLabel;
    private Map<String, Node> children;
    private Node parent;
    private String parentLink;

    public Node() {
        isLeaf = true;
        decisionAttribute = -1;
        attributeLabel = "";
        children = new HashMap<>();
        parent = null;
        parentLink = "";
    }

    public static void setAttributes(List<String> attributes) {
        Node.attributes = new ArrayList<>(attributes);
    }

    public boolean isLeaf() {
        return isLeaf;
    }

    public void setLeaf(boolean leaf) {
        isLeaf = leaf;
    }

    public int getDecisionAttribute() {
        return decisionAttribute;
    }

    public void setDecisionAttribute(int decisionAttribute) {
        this.decisionAttribute = decisionAttribute;
    }

    public String getAttributeLabel() {
        return attributeLabel;
    }

    public void setAttributeLabel(String attributeLabel) {
        this.attributeLabel = attributeLabel;
    }

    public Map<String, Node> getChildren() {
        return children;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public String getParentLink() {
        return parentLink;
    }

    public void setParentLink(String parentLink) {
        this.parentLink = parentLink;
    }

    @SuppressWarnings("MethodDoesntCallSuperMethod")
    @Override
    public Node clone() {
        Node node = new Node();
        node.setLeaf(this.isLeaf);
        node.setDecisionAttribute(this.decisionAttribute);
        node.setAttributeLabel(this.attributeLabel);
        node.children = new HashMap<>();
        for (String value : this.children.keySet()) {
            Node child = children.get(value).clone();
            child.setParent(node);
            child.setParentLink(value);
            node.children.put(value, child);
        }
        return node;
    }

    public void print() {
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

    @Override
    public String toString() {
        return "Node{" +
                "parentLink='" + parentLink + '\'' +
                ", parent=" + (parent != null ? parent.decisionAttribute : "NULL") +
                ", isLeaf=" + isLeaf +
                ", decisionAttribute=" + decisionAttribute +
                ", attributeLabel='" + attributeLabel + '\'' +
                ", children=" + children +
                '}';
    }
}
