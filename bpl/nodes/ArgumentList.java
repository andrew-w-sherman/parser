package bpl.nodes;
import bpl.Token;
import bpl.exceptions.*;
public class ArgumentList extends ExpressionNode {

    Expression head;

    public ArgumentList(int line, Expression head) {
        this.line = line;
        this.head = head;
        this.type = ARG_LIST;
    }

    public void printRec(int depth) {
        printDepth(depth);
        System.out.print("Argument List\n");
        head.printRec(depth + 1);
        if (next != null) next.printRec(depth);
    }
}
