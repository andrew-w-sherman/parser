package bpl.nodes;
import bpl.Token;
import bpl.exceptions.*;
public class MulOp extends ExpressionNode {

    public Token token;

    public MulOp(int line, Token token) {
        this.token = token;
        this.line = line;
        this.type = MULOP;
    }

    public void printRec(int depth) {
        printDepth(depth);
        System.out.println(token.value);
        if (next != null) next.printRec(depth);
    }
}
