#!/bin/sh

for n in 1 2 3 4
do
    java Test testfiles/t$n.bpl > testfiles/t$n.temp
    diff testfiles/t$n.out testfiles/t$n.temp
    rm testfiles/t$n.temp
done
javac ParserTests.java
java ParserTests.java
