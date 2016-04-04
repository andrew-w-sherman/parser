package bpl.nodes;
import bpl.TypeChecker;
import bpl.Token;
import bpl.LocalDecList;
import bpl.exceptions.*;
import java.util.HashMap;
public class AddOp extends ExpressionNode {

    public Token token;

    public AddOp(int line, Token token) {
        this.token = token;
        this.line = line;
        this.kind = ADDOP;
    }

    public void printRec(int depth) {
        printDepth(depth);
        System.out.println(token.value);
        if (next != null) next.printRec(depth);
    }
}
