all:
	find . -type f -name '*.class' -delete; javac bpl/Bpl.java;

clean:
	find . -type f -name '*.class' -delete;
