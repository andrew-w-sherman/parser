public class CompoundStatement extends StatementNode {

    public StatementList sl;
    public LocalDeclarations ld;

    public CompoundStatement(int line, LocalDeclarations ld,
            StatementList sl) {
        this.ld = ld;
        this.sl = sl;
        this.line = line;
        this.type = COMPOUND_STMT;
    }

    public void printRec(int depth) {
        printDepth(depth);
        System.out.print("Compound statement:\n");
        printDepth(depth);
        System.out.print("Local decs:\n");
        ld.printRec(depth+1);
        printDepth(depth);
        System.out.print("Body:\n");
        sl.printRec(depth+1);
        if (next != null) next.printRec(depth);
    }
}
