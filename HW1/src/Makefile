all: Main.class

Main.class: DataSet.java DecisionTree.java InformationGainHeuristic.java Main.java Node.java opencsv-3.9.jar VarianceImpurityHeuristic.java
	javac -cp .:opencsv-3.9.jar DataSet.java DecisionTree.java InformationGainHeuristic.java Main.java Node.java VarianceImpurityHeuristic.java

run: Main.class
	java -cp .:opencsv-3.9.jar Main ../hw1/input/dataset_1/training_set.csv ../hw1/input/dataset_1/validation_set.csv ../hw1/input/dataset_1/test_set.csv yes

jar:
	touch Manifest.txt
	echo ""
	jar cvfm program.jar *.java

clean:
	rm -f program.jar
	rm -f *.class
