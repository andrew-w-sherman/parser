package bpl.nodes;
import bpl.Token;
import bpl.exceptions.*;
public class WhileStatement extends StatementNode {

    Expression ex;
    Statement st;

    public WhileStatement(int line, Expression ex, Statement st) {
        this.ex = ex;
        this.st = st;
        this.line = line;
        this.type = WHILE_STMT;
    }

    public void printRec(int depth) {
        printDepth(depth);
        System.out.print("While:\n");
        ex.printRec(depth+1);
        printDepth(depth);
        System.out.print("do:\n");
        st.printRec(depth+1);
        printDepth(depth);
        System.out.print("Endwhile\n");
        if (next != null) next.printRec(depth);
    }
}
