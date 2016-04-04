package bpl.nodes;
import bpl.TypeChecker;
import bpl.Token;
import bpl.LocalDecList;
import bpl.exceptions.*;
import java.util.HashMap;
public class DeclarationList extends DeclarationNode {

    public Declaration head;

    public DeclarationList(int line, Declaration head) {
        this.line = line;
        this.head = head;
        this.kind = DECLARATION_LIST;
    }

    public void printRec(int depth) {
        printDepth(depth);
        System.out.print("Declaration List\n");
        head.printRec(depth + 1);
        if (next != null) next.printRec(depth);
    }
}
