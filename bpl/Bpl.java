package bpl;
import bpl.nodes.*;
import bpl.exceptions.*;
public class Bpl {
    public static void main(String[] args){
        String inputFileName;
        String outputFileName;
        Parser myParser;
        TypeChecker checker;
        Generator gen;
        DeclarationList root;

        if (args.length < 1) {System.out.println("Please pass a file name."); return;}
        inputFileName = args[0];
        outputFileName = inputFileName.split(".")[0] + ".s";
        try {
            myParser = new Parser(inputFileName);
        } catch (CompException e) {
            System.out.println(e);
            return;
        }
        try {
            root = myParser.parse();
        } catch (CompException e) {
            System.out.println(e);
            return;
        }
        try {
            checker = new TypeChecker(root);
            checker.link();
            checker.check();
        } catch (TypeException e) {
            System.out.println(e);
            return;
        }
        try {
            gen = new Generator(root);
            gen.generate(outputFileName);
        } catch (GenException e) {
            System.out.println(e);
            return;
        }
    }
}
