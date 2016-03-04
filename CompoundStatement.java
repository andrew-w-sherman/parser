public class CompoundStatement extends StatementNode {

    public StatementList sl;

    public CompoundStatement(int line, StatementList sl) {
        this.sl = sl;
        this.line = line;
        this.type = COMPOUND_STMT;
    }

    public void printRec(int depth) {
        printDepth(depth);
        System.out.print("Compound statement\n");
        sl.printRec(depth+1);
        if (next != null) next.printRec(depth);
    }
}
