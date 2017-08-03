I have done this assignment completely on my own. I have not copied it, nor
have I given my solution to anyone else. I understand that if I am involved in
plagiarism or cheating I will have to sign an official form that I have
cheated and that this form will be stored in my official university record. I
also understand that I will receive a grade of 0 for the involved assignment
for my first offense and that I will receive a grade of "F" for the course for
any additional offense.

Name: 		Swapnil Bhoite
B-Number:	XXX
Email:		XXX

- Instructions on how to compile and run the code:
	- Language used: JAVA
	- "hw3" directory contains all the source files
	- "input" directory in the above directory contains example data sets provided with the homework
	- Makefile contails all the commands for compiling the project and run it on the example data sets
	- "results.txt" contains overall summury of the the all outputs
	- "output.txt" file contains actual output log after running the program
	- "training_0.arff" is WEKA format ARFF file of test data set
	- "testing_0.arff" is WEKA format ARFF file of test data set
	- Steps:
		1. make
			This will compile the all source code files and create "program.jar" executable in current directory

		2. make run1
			Will run part1 with dataset1, L = 10, K = 5, to-print = no

		3. make run2
			Will run part1 with dataset2, L = 10, K = 5, to-print = no

		4. make run3
			Will run part1 with dataset1, L = 10, K = 5, to-print = yes

		5. make run4
			Will run part1 with dataset2, L = 10, K = 5, to-print = yes

		6. make run4
			Will run part1 with given dataset, skip-stopwords = no

		7. make run4
			Will run part1 with given dataset, skip-stopwords = yes

		8. make clean
			Remove all compiled files including executable "program1.jar" and "program2.jar"

	- To run the against other data set and values, use the following command:
		java -jar program.jar <algorithm> <training-set-dir> <test-set-dir> <skip-stopwords> <iterations> <learning rate> <lambda> <path for training.arff> <path for testing.arff>
			algorithm:{lrmcap/perceptrons/weka-convert-arff}
			skip-stopwords:{yes/no}
			iterations:{integer for number of iterations in perceptrons training}
			learning rate:{double for learning rate in lrmcap, perceptrons training}
			lambda:{double for strength of the regularization term in lrmcap training}
