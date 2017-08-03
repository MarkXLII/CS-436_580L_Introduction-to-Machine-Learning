package models;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * hw2-part2
 * Author:      Swapnil Bhoite
 * B-Number:    XXX
 */
public class Classifier {

    private Set<Integer> classes;
    private Set<String> vocabulary;
    private Map<Integer, Double> prior;
    private Map<Object, Double> conditionalProbability;

    public Set<Integer> getClasses() {
        return classes;
    }

    public void setClasses(Set<Integer> classes) {
        this.classes = new HashSet<>(classes);
    }

    public Set<String> getVocabulary() {
        return vocabulary;
    }

    public void setVocabulary(Set<String> vocabulary) {
        this.vocabulary = new HashSet<>(vocabulary);
    }

    public Map<Integer, Double> getPrior() {
        return prior;
    }

    public void setPrior(Map<Integer, Double> prior) {
        this.prior = new HashMap<>(prior);
    }

    public Map<Object, Double> getConditionalProbability() {
        return conditionalProbability;
    }

    public void setConditionalProbability(Map<Object, Double> conditionalProbability) {
        this.conditionalProbability = new HashMap<>(conditionalProbability);
    }
}
