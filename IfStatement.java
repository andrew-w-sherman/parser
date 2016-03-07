public class IfStatement extends StatementNode {

    Expression ex;
    Statement st1;
    Statement st2;

    public IfStatement(int line, Expression ex, Statement st) {
        this.ex = ex;
        this.st1 = st;
        this.line = line;
        this.type = IF_STMT;
    }

    public IfStatement(int line, Expression ex, Statement st1, Statement st2) {
        this(line, ex, st1);
        this.st2 = st2;
    }

    public void printRec(int depth) {
        printDepth(depth);
        System.out.print("If:\n");
        ex.printRec(depth+1);
        printDepth(depth);
        System.out.print("do:\n");
        st1.printRec(depth+1);
        if (st2 != null) {
            printDepth(depth);
            System.out.print("else:\n");
            st2.printRec(depth+1);
        }
        printDepth(depth);
        System.out.print("EndIf\n");
        if (next != null) next.printRec(depth);
    }
}
