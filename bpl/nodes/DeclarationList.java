package bpl.nodes;
import bpl.Token;
import bpl.exceptions.*;
public class DeclarationList extends DeclarationNode {

    Declaration head;

    public DeclarationList(int line, Declaration head) {
        this.line = line;
        this.head = head;
        this.type = DECLARATION_LIST;
    }

    public void printRec(int depth) {
        printDepth(depth);
        System.out.print("Declaration List\n");
        head.printRec(depth + 1);
        if (next != null) next.printRec(depth);
    }
}
