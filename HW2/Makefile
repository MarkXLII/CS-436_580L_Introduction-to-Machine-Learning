all: program1.jar program2.jar

program1.jar:
	ant -f hw2-part1/build.xml
	cp hw2-part1/out/artifacts/hw2_part1_jar/hw2-part1.jar program1.jar

program2.jar:
	ant -f hw2-part2/build.xml
	cp hw2-part2/out/artifacts/hw2_part2_jar/hw2-part2.jar program2.jar

run1: program1.jar
	java -cp program1.jar Main 10 5 hw2-part1/input/dataset_1/training_set.csv hw2-part1/input/dataset_1/validation_set.csv hw2-part1/input/dataset_1/test_set.csv no

run2: program1.jar
	java -cp program1.jar Main 10 5 hw2-part1/input/dataset_2/training_set.csv hw2-part1/input/dataset_2/validation_set.csv hw2-part1/input/dataset_2/test_set.csv no

run3: program1.jar
	java -cp program1.jar Main 10 5 hw2-part1/input/dataset_1/training_set.csv hw2-part1/input/dataset_1/validation_set.csv hw2-part1/input/dataset_1/test_set.csv yes

run4: program1.jar
	java -cp program1.jar Main 10 5 hw2-part1/input/dataset_2/training_set.csv hw2-part1/input/dataset_2/validation_set.csv hw2-part1/input/dataset_2/test_set.csv yes

run5: program2.jar
	java -cp program2.jar Main hw2-part2/input/train hw2-part2/input/test no

run6: program2.jar
	java -cp program2.jar Main hw2-part2/input/train hw2-part2/input/test yes

clean:
	rm -rf hw2-part1/out/
	rm -rf hw2-part2/out/
	rm -f program1.jar
	rm -f program2.jar
