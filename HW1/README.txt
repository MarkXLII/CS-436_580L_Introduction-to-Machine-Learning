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
	- "src" directory contains all the source files and required library to read csv files
	- "input" directory contains two example data sets provided with homework
	- Makefile contails all the commands for compiling the project and run it on the example data sets
	- "Results.txt" file containing the results on the test data sets 1 and 2
	- Steps:
		1. make
			This will compile the project and create "program.jar" executable in current directory
		2. make run1
			This will compile the project if not already and run it against data set 1
		3. make run2
			This will compile the project if not already and run it against data set 2
		4. make clean
			Remove all compiled files including executable "program.jar"
	- To run the project against other data set, use the following command:
		java -jar program.jar <training-set> <validation-set> <test-set> <to-print>
		to-print:{yes/no}

- Accuracy on data set 1:
	- Using Information Gain Heuristic
		Accuracy: 574/600 = (95.66666666666667%)
	- Using Variance Impurity Heuristic
		Accuracy: 572/600 = (95.33333333333333%)

- Accuracy on data set 2:
	- Using Information Gain Heuristic
		Accuracy: 434/600 = (72.33333333333333%)
	- Using Variance Impurity Heuristic
		Accuracy: 435/600 = (72.5%)
