public class ReturnStatement extends StatementNode {

    Expression ex;

    public ReturnStatement(int line) {
        this.line = line;
        this.type = RETURN_STMT;
    }

    public ReturnStatement(int line, Expression ex) {
        this(line);
        this.ex = ex;
    }

    public void printRec(int depth) {
        printDepth(depth);
        if (ex != null) {
            System.out.print("Return:\n");
            ex.printRec(depth+1);
        }
        else
            System.out.print("Return void\n");
        if (next != null) next.printRec(depth);
    }
}
