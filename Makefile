all:
	find . -type f -name '*.class' -delete; javac bpl/Bpl.java;

check:
	find . -type f -name '*.class' -delete; javac bpl/Bpl.java; find . -type f -name '*.class' -delete;

clean:
	find . -type f -name '*.class' -delete;
