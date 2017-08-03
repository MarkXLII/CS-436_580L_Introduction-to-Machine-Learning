I have done this assignment completely on my own. I have not copied it, nor have I given my solution to anyone else. I understand that if I am involved in plagiarism or cheating I will have to sign an official form that I have cheated and that this form will be stored in my official university record. I also understand that I will receive a grade of 0 for the involved assignment for my first offense and that I will receive a grade of "F" for the course for any additional offense.

Name: 		Swapnil Bhoite
B-Number:	XXX
Email:		XXX

Instructions on how to compile and run the code:

- Language used: JAVA
- Post-pruning in Decision Trees:
  - "hw2-part1" directory contains all the source files and required library to read csv files
- Naive Bayes for Text Classification:
  - "hw2-part2" directory contains all the source files
- "input" directory in each of the above directories contain example data sets provided with the homework
- Makefile contails all the commands for compiling the project and run it on the example data sets
- "Results-Part-1-Data-Set-1.txt" file contains results on 10 values of L and K on against data set 1
- "Results-Part-1-Data-Set-2.txt" file contains results on 10 values of L and K on against data set 2
- "Results-Part-2.txt" file contains results of Naive Bayes algorithm on give data sets
- Steps:
  1. make
     This will compile both parts and create "program1.jar" and "program2.jar" executables in current directory
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
- To run the part1 against other data set, use the following command:
  java -cp program1.jar Main L K <training-set> <validation-set> <test-set> <to-print>
  to-print:{yes/no}
- To run the part2 against other data set, use the following command:
  java -cp program2.jar Main <training-set-dir> <test-set-dir> <skip-stopwords>
  skip-stopwords:{yes/no}