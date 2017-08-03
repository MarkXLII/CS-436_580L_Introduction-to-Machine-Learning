all: jar

jar: program.jar

compile:
	mkdir -p out
	javac -cp src/opencsv-3.9.jar src/*.java -d out

program.jar: src/*.java src/*.jar
	make clean
	make compile
	cd out && echo "Main-Class: Main\nClass-Path: src/opencsv-3.9.jar" > manifest.txt && jar cvfm ../program.jar manifest.txt *.class
	rm -rf out

clean:
	rm -rf out
	rm -f program.jar

run1: program.jar
	java -jar program.jar input/dataset_1/training_set.csv input/dataset_1/validation_set.csv input/dataset_1/test_set.csv yes

run2: program.jar
	java -jar program.jar input/dataset_2/training_set.csv input/dataset_2/validation_set.csv input/dataset_2/test_set.csv yes
