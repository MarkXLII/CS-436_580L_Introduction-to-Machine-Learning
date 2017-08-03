all: program.jar
	make run-lrmcap
	make run-perceptrons
	make run-weka-convert-arff

program.jar:
	ant -f hw3/build.xml
	cp hw3/out/artifacts/hw3_jar/hw3.jar program.jar

run-lrmcap: program.jar
	java -cp program.jar Main lrmcap hw3/input/train hw3/input/test no 0.5684614750463296 0.007903417685980574 0.0
	java -cp program.jar Main lrmcap hw3/input/train hw3/input/test no 0.4684614750463296 0.005903417685980574 0.0
	java -cp program.jar Main lrmcap hw3/input/train hw3/input/test no 0.3684614750463296 0.003903417685980574 0.0
	java -cp program.jar Main lrmcap hw3/input/train hw3/input/test no 0.2684614750463296 0.001903417685980574 0.0
	java -cp program.jar Main lrmcap hw3/input/train hw3/input/test no 0.1684614750463296 0.000903417685980574 0.0

	java -cp program.jar Main lrmcap hw3/input/train hw3/input/test yes 0.5684614750463296 0.007903417685980574 0.0
	java -cp program.jar Main lrmcap hw3/input/train hw3/input/test yes 0.4684614750463296 0.005903417685980574 0.0
	java -cp program.jar Main lrmcap hw3/input/train hw3/input/test yes 0.3684614750463296 0.003903417685980574 0.0
	java -cp program.jar Main lrmcap hw3/input/train hw3/input/test yes 0.2684614750463296 0.001903417685980574 0.0
	java -cp program.jar Main lrmcap hw3/input/train hw3/input/test yes 0.1684614750463296 0.00903417685980574 0.0

run-perceptrons: program.jar
	java -cp program.jar Main perceptrons hw3/input/train hw3/input/test no 100 0.5684614750463296
	java -cp program.jar Main perceptrons hw3/input/train hw3/input/test no 90 0.1684614750463296
	java -cp program.jar Main perceptrons hw3/input/train hw3/input/test no 80 0.5684614750463296
	java -cp program.jar Main perceptrons hw3/input/train hw3/input/test no 70 0.4684614750463296
	java -cp program.jar Main perceptrons hw3/input/train hw3/input/test no 60 0.0684614750463296
	java -cp program.jar Main perceptrons hw3/input/train hw3/input/test no 50 0.5684614750463296
	java -cp program.jar Main perceptrons hw3/input/train hw3/input/test no 40 0.4684614750463296
	java -cp program.jar Main perceptrons hw3/input/train hw3/input/test no 30 0.5684614750463296
	java -cp program.jar Main perceptrons hw3/input/train hw3/input/test no 20 0.3684614750463296
	java -cp program.jar Main perceptrons hw3/input/train hw3/input/test no 10 0.5684614750463296
	java -cp program.jar Main perceptrons hw3/input/train hw3/input/test no 200 0.9684614750463296
	java -cp program.jar Main perceptrons hw3/input/train hw3/input/test no 220 0.5684614750463296
	java -cp program.jar Main perceptrons hw3/input/train hw3/input/test no 250 0.5684614750463296
	java -cp program.jar Main perceptrons hw3/input/train hw3/input/test no 300 0.5684614750463296
	java -cp program.jar Main perceptrons hw3/input/train hw3/input/test no 400 0.9984614750463296
	java -cp program.jar Main perceptrons hw3/input/train hw3/input/test no 500 0.5684614750463296
	java -cp program.jar Main perceptrons hw3/input/train hw3/input/test no 600 0.0684614750463296
	java -cp program.jar Main perceptrons hw3/input/train hw3/input/test no 700 0.0684614750463296
	java -cp program.jar Main perceptrons hw3/input/train hw3/input/test no 900 0.9684614750463296
	java -cp program.jar Main perceptrons hw3/input/train hw3/input/test no 1000 0.5684614750463296

	java -cp program.jar Main perceptrons hw3/input/train hw3/input/test yes 100 0.5684614750463296
	java -cp program.jar Main perceptrons hw3/input/train hw3/input/test yes 90 0.1684614750463296
	java -cp program.jar Main perceptrons hw3/input/train hw3/input/test yes 80 0.5684614750463296
	java -cp program.jar Main perceptrons hw3/input/train hw3/input/test yes 70 0.4684614750463296
	java -cp program.jar Main perceptrons hw3/input/train hw3/input/test yes 60 0.0684614750463296
	java -cp program.jar Main perceptrons hw3/input/train hw3/input/test yes 50 0.5684614750463296
	java -cp program.jar Main perceptrons hw3/input/train hw3/input/test yes 40 0.4684614750463296
	java -cp program.jar Main perceptrons hw3/input/train hw3/input/test yes 30 0.5684614750463296
	java -cp program.jar Main perceptrons hw3/input/train hw3/input/test yes 20 0.3684614750463296
	java -cp program.jar Main perceptrons hw3/input/train hw3/input/test yes 10 0.5684614750463296
	java -cp program.jar Main perceptrons hw3/input/train hw3/input/test yes 200 0.9684614750463296
	java -cp program.jar Main perceptrons hw3/input/train hw3/input/test yes 220 0.5684614750463296
	java -cp program.jar Main perceptrons hw3/input/train hw3/input/test yes 250 0.5684614750463296
	java -cp program.jar Main perceptrons hw3/input/train hw3/input/test yes 300 0.5684614750463296
	java -cp program.jar Main perceptrons hw3/input/train hw3/input/test yes 400 0.9984614750463296
	java -cp program.jar Main perceptrons hw3/input/train hw3/input/test yes 500 0.5684614750463296
	java -cp program.jar Main perceptrons hw3/input/train hw3/input/test yes 600 0.0684614750463296
	java -cp program.jar Main perceptrons hw3/input/train hw3/input/test yes 700 0.0684614750463296
	java -cp program.jar Main perceptrons hw3/input/train hw3/input/test yes 900 0.9684614750463296
	java -cp program.jar Main perceptrons hw3/input/train hw3/input/test yes 1000 0.5684614750463296

run-weka-convert-arff: program.jar
	java -cp program.jar Main weka-convert-arff hw3/input/train hw3/input/test yes training_0.arff testing_0.arff
	java -cp program.jar Main weka-convert-arff hw3/input/train hw3/input/test no training_1.arff testing_1.arff

clean:
	rm -rf hw3/out/
	rm -f program.jar
	rm -f *.arff
    
