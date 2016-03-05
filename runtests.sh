#!/bin/sh

for n in 1 2
do
    java Test testfiles/t$n.bpl > testfiles/t$n.temp
    diff testfiles/t$n.out testfiles/t$n.temp
    rm testfiles/t$n.temp
done

# we could also run unit tests if we had them
#javac ParserTests.java
#java ParserTests.java
