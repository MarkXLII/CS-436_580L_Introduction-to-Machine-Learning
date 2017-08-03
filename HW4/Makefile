all: part1

part1: svm-predict svm-train
	@echo "********** kernel type: linear: u'*v **********"
	./svm-train -t 0 promoters_data/training.new promoters_t0.model
	@echo "\n********** kernel type: polynomial: (gamma*u'*v + coef0)^degree **********"
	./svm-train -t 1 promoters_data/training.new promoters_t1.model
	@echo "\n********** kernel type: radial basis function: exp(-gamma*|u-v|^2) **********"
	./svm-train -t 2 promoters_data/training.new promoters_t2.model
	@echo "\n********** kernel type: sigmoid: tanh(gamma*u'*v + coef0) **********"
	./svm-train -t 3 promoters_data/training.new promoters_t3.model

	@echo "\n\n********** testing **********"
	rm -f ../part1_output.txt
	@echo "********** kernel type: linear: u'*v **********" >> ../part1_output.txt
	./svm-predict promoters_data/validation.new promoters_t0.model promoters_t0.output >> ../part1_output.txt
	@echo "\n********** kernel type: polynomial: (gamma*u'*v + coef0)^degree **********" >> ../part1_output.txt
	./svm-predict promoters_data/validation.new promoters_t1.model promoters_t1.output >> ../part1_output.txt
	@echo "\n********** kernel type: radial basis function: exp(-gamma*|u-v|^2) **********" >> ../part1_output.txt
	./svm-predict promoters_data/validation.new promoters_t2.model promoters_t2.output >> ../part1_output.txt
	@echo "\n********** kernel type: sigmoid: tanh(gamma*u'*v + coef0) **********" >> ../part1_output.txt
	./svm-predict promoters_data/validation.new promoters_t3.model promoters_t3.output >> ../part1_output.txt
	cat ../part1_output.txt

clean:
	rm -f *.model
	rm -f ../part1_output.txt
