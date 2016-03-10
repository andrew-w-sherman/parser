package bpl.nodes;
import bpl.Token;
import bpl.exceptions.*;
public class Statement extends StatementNode {

    public ExpressionStatement es;
    public CompoundStatement cs;
    public IfStatement is;
    public WhileStatement ws;
    public ReturnStatement rs;
    public WriteStatement wrs;

    public Statement next;

    public Statement(int line, ExpressionStatement child) {
        this.line = line;
        this.type = STATEMENT;
        es = child;
    }

    public Statement(int line, CompoundStatement child) {
        this.line = line;
        this.type = STATEMENT;
        cs = child;
    }

    public Statement(int line, IfStatement child) {
        this.line = line;
        this.type = STATEMENT;
        is = child;
    }

    public Statement(int line, WhileStatement child) {
        this.line = line;
        this.type = STATEMENT;
        ws = child;
    }

    public Statement(int line, ReturnStatement child) {
        this.line = line;
        this.type = STATEMENT;
        rs = child;
    }

    public Statement(int line, WriteStatement child) {
        this.line = line;
        this.type = STATEMENT;
        wrs = child;
    }

    public void printRec(int depth) {
        printDepth(depth);
        System.out.print("Statement\n");
        if(es != null) es.printRec(depth+1);
        else if(cs != null) cs.printRec(depth + 1);
        else if(is != null) is.printRec(depth + 1);
        else if(ws != null) ws.printRec(depth + 1);
        else if(rs != null) rs.printRec(depth + 1);
        else if(wrs != null) wrs.printRec(depth + 1);
        else System.out.println("This shouldn't happen!!!! (Statement.java)");
        if(next != null) next.printRec(depth);
    }
}
