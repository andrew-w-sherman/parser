public class Statement extends StatementNode {

    public ExpressionStatement es;
    public CompoundStatement cs;
    public WhileStatement ws;

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

    public Statement(int line, WhileStatement child) {
        this.line = line;
        this.type = STATEMENT;
        ws = child;
    }

    public void printRec(int depth) {
        printDepth(depth);
        System.out.print("Statement\n");
        if(es != null) es.printRec(depth+1);
        else if(cs != null) cs.printRec(depth + 1);
        else if(ws != null) ws.printRec(depth + 1);
        else System.out.println("This shouldn't happen!!!! (Statement.java)");
        if(next != null) next.printRec(depth);
    }
}
