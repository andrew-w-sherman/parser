package bpl.nodes;
import bpl.TypeChecker;
import bpl.Token;
import bpl.LocalDecList;
import bpl.exceptions.*;
import java.util.HashMap;
public class CompoundStatement extends StatementNode {

    public StatementList sl;
    public LocalDeclarations ld;

    public CompoundStatement(int line, LocalDeclarations ld,
            StatementList sl) {
        this.ld = ld;
        this.sl = sl;
        this.line = line;
        this.kind = COMPOUND_STMT;
    }

    public void findReferences(HashMap<String, DeclarationNode> symbolTable,
            LocalDecList localDecs) throws TypeException {
        if (ld != null) {
            for (VariableDeclaration dn = ld.head; dn != null; dn = dn.next) {
                localDecs = new LocalDecList(dn, localDecs);
            }
        }
        sl.findReferences(symbolTable, localDecs);
    }

    public void checkType(String rt) throws TypeException {
        if (sl != null) sl.checkType(rt);
    }

    public void printRec(int depth) {
        printDepth(depth);
        System.out.print("Compound statement:\n");
        if (ld != null) {
            printDepth(depth);
            System.out.print("Local decs:\n");
            ld.printRec(depth+1);
        }
        if (sl != null) {
            printDepth(depth);
            System.out.print("Body:\n");
            sl.printRec(depth+1);
        }
        if (next != null) next.printRec(depth);
    }
}
