import contracts.MultinomialNaiveBayes;
import models.Classifier;
import models.Document;

import java.util.*;

/**
 * hw2-part2
 * Author:      Swapnil Bhoite
 * B-Number:    XXX
 */
public class MultinomialNaiveBayesImpl implements MultinomialNaiveBayes {

    private static final double LOG2 = Math.log(2);

    @Override
    public Classifier train(List<Document> trainingDataSet) {
        Classifier classifier = new Classifier();
        Set<Integer> classes = new HashSet<>();
        classes.add(Document.Class.SPAM);
        classes.add(Document.Class.HAM);
        classifier.setClasses(classes);
        Map<Integer, Double> prior = new HashMap<>();
        Map<Object, Double> conditionalProbability = new HashMap<>();

        /* V ← EXTRACTVOCABULARY(D) */
        classifier.setVocabulary(extractVocabulary(trainingDataSet));

        /* N ← COUNTDOCS(D) */
        int n = trainingDataSet.size();

        /* for each c ∈ C do */
        for (Integer documentClass : classifier.getClasses()) {
            /* Nc ← COUNTDOCSINCLASS(D, c)  */
            int nc = countDocsInClass(trainingDataSet, documentClass);

            /* prior[c] ← Nc/N */
            prior.put(documentClass, (double) nc / n);

            /* textc ← CONCATENATETEXTOFALLDOCSINCLASS(D, c) */
            List<String> text = concatTextOfDocsInClass(trainingDataSet, documentClass);

            /* for each t ∈ V do */
            Map<String, Integer> termCount = new HashMap<>();
            double denominator = 0; //to count ∑t′(Tct′+1)
            for (String term : classifier.getVocabulary()) {
                /* Tct ← COUNTTOKENSOFTERM(textc, term) */
                int count = countTokensOfTerm(text, term);
                termCount.put(term, count);
                denominator += (count + 1);
            }

            /* for each t ∈ V do */
            for (String term : classifier.getVocabulary()) {
                /* condprob[t][c] ← (Tct+1 /∑t′(Tct′+1)) */
                double numerator = termCount.get(term) + 1;
                TermClassPair termClassPair = new TermClassPair(term, documentClass);
                conditionalProbability.put(termClassPair, numerator / denominator);
            }
        }

        classifier.setPrior(prior);
        classifier.setConditionalProbability(conditionalProbability);

        return classifier;
    }

    @Override
    public int test(Classifier classifier, Document document) {
        /* W ← EXTRACTTOKENSFROMDOC(V, d) */
        List<String> w = new ArrayList<>();
        Set<String> vocabulary = classifier.getVocabulary();
        for (String term : document.getTerms()) {
            if (vocabulary.contains(term)) {
                w.add(term);
            }
        }

        /* for each c ∈ C do */
        Map<Integer, Double> score = new HashMap<>();
        for (Integer documentClass : classifier.getClasses()) {
            /* score[c] ← log prior[c] */
            score.put(documentClass, logToBase2(classifier.getPrior().get(documentClass)));

            /* for each t ∈ W do */
            for (String term : w) {
                /* score[c] += log condprob[t][c] */
                double previousScore = score.get(documentClass);
                TermClassPair termClassPair = new TermClassPair(term, documentClass);
                double result = logToBase2(classifier.getConditionalProbability().get(termClassPair));
                score.put(documentClass, previousScore + result);
            }
        }
        double max = Collections.max(score.values());
        for (Integer documentClass : score.keySet()) {
            if (score.get(documentClass) >= max) {
                return documentClass;
            }
        }
        return -1;
    }

    private Set<String> extractVocabulary(List<Document> trainingDataSet) {
        Set<String> vocabulary = new HashSet<>();
        for (Document document : trainingDataSet) {
            vocabulary.addAll(document.getTerms());
        }
        return vocabulary;
    }

    private int countDocsInClass(List<Document> trainingDataSet, int documentClass) {
        int count = 0;
        for (Document document : trainingDataSet) {
            if (document.getDocumentClass() == documentClass) {
                count++;
            }
        }
        return count;
    }

    private List<String> concatTextOfDocsInClass(List<Document> trainingDataSet, Integer documentClass) {
        List<String> text = new ArrayList<>();
        for (Document document : trainingDataSet) {
            if (document.getDocumentClass() == documentClass) {
                text.addAll(document.getTerms());
            }
        }
        return text;
    }

    private int countTokensOfTerm(List<String> textC, String term) {
        return Collections.frequency(textC, term);
    }

    private double logToBase2(double num) {
        if (num == 0) {
            return 0;
        }
        return Math.log(num) / LOG2;
    }

    class TermClassPair {
        String term;
        int documentClass;

        TermClassPair(String term, int documentClass) {
            this.term = term;
            this.documentClass = documentClass;
        }

        @SuppressWarnings("SimplifiableIfStatement")
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            TermClassPair that = (TermClassPair) o;

            if (documentClass != that.documentClass) return false;
            return term != null ? term.equals(that.term) : that.term == null;
        }

        @Override
        public int hashCode() {
            int result = term != null ? term.hashCode() : 0;
            result = 31 * result + documentClass;
            return result;
        }
    }
}
