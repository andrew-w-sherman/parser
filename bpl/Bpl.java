package bpl;
import bpl.nodes.TreeNode;
import bpl.exceptions.*;
public class Bpl {
    public static void main(String[] args){
        String inputFileName;
        Parser myParser;
        TreeNode root;

        if (args.length < 1) {System.out.println("Please pass a file name."); return;}
        inputFileName = args[0];
        try {
            myParser = new Parser(inputFileName);
        } catch (CompException e) {
            System.out.println(e);
            return;
        }
        try {
            root = myParser.parse();
            root.printRec(0);
        } catch (CompException e) {
            System.out.println(e);
            return;
        }

    }
}
