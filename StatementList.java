public class StatementList extends StatementNode {

    Statement head;

    public StatementList(int line, Statement head) {
        this.line = line;
        this.head = head;
        this.type = STATEMENT_LIST;
    }

    public void printRec(int depth) {
        printDepth(depth);
        System.out.print("Statement List\n");
        head.printRec(depth + 1);
        if (next != null) next.printRec(depth);
    }
}
