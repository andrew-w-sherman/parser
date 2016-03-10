package bpl.nodes;
import bpl.Token;
import bpl.exceptions.*;
public class Literal extends ExpressionNode {

    public Integer numVal;
    public String strVal;

    private Literal(int line) {
        this.type = VAR;
        this.line = line;
    }

    public Literal(int line, Token tok) {
        this(line);
        if (tok.kind == Token.T_STRLIT) {
            strVal = tok.value;
        }
        else if (tok.kind == Token.T_NUM) {
            numVal = Integer.valueOf(tok.value);
        }
        else System.out.print("This really shouldn't happen. (Literal.java)");
    }

    public void printRec(int depth) {
        printDepth(depth);
        if (strVal != null)
            System.out.print("\"" + strVal + "\"");
        else if (numVal != null)
            System.out.print("" + numVal);
        else System.out.println("Noooooooope :| (Literal.java)");
        System.out.print("\n");
        if (next != null) next.printRec(depth);
    }
}
