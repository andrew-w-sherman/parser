package bpl.nodes;
import bpl.Token;
import bpl.exceptions.*;
public class WriteStatement extends StatementNode {

    Expression ex;
    Statement st;

    public WriteStatement(int line) {
        this.line = line;
        this.type = WRITE_STMT;
    }

    public WriteStatement(int line, Expression ex) {
        this(line);
        this.ex = ex;
    }

    public void printRec(int depth) {
        printDepth(depth);
        if (ex != null) {
            System.out.print("Write:\n");
            ex.printRec(depth+1);
        }
        else
            System.out.print("WriteLn\n");
        if (next != null) next.printRec(depth);
    }
}
