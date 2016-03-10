package bpl.nodes;
import bpl.Token;
import bpl.exceptions.*;
public class Arguments extends ExpressionNode {

    public ArgumentList al;

    public Arguments(int line) {
        this.line = line;
        this.type = ARGS;
    }

    public Arguments(int line, ArgumentList al) {
        this(line);
        this.al = al;
    }

    public void printRec(int depth) {
        printDepth(depth);
        if (al != null) {
            System.out.print("Arguments\n");
            al.printRec(depth+1);
        }
        else
            System.out.print("No arguments\n");
        if (next != null) next.printRec(depth);
    }
}
