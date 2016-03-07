public class ParameterList extends DeclarationNode {

    Parameter head;

    public ParameterList(int line, Parameter head) {
        this.line = line;
        this.head = head;
        this.type = ARG_LIST;
    }

    public void printRec(int depth) {
        printDepth(depth);
        System.out.print("Parameter List\n");
        head.printRec(depth + 1);
        if (next != null) next.printRec(depth);
    }
}
