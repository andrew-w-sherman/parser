package bpl.nodes;
import bpl.TypeChecker;
import bpl.Token;
import bpl.LocalDecList;
import bpl.exceptions.*;
import java.util.HashMap;
public class ENode extends ExpressionNode {

    public ENode e;
    public AddOp add;
    public TNode t;

    public ENode(int line) {
        this.line = line;
        this.kind = E;
    }

    public ENode(int line, TNode t) {
        this(line);
        this.t = t;
    }

    public ENode(int line, ENode e, AddOp add, TNode t) {
        this(line);
        this.e = e;
        this.add = add;
        this.t = t;
    }

    public void findReferences(HashMap<String, DeclarationNode> symbolTable,
            LocalDecList localDecs) throws TypeException {
        if (e != null && add != null)
            e.findReferences(symbolTable, localDecs);
        t.findReferences(symbolTable, localDecs);
    }

    public String checkType() throws TypeException {
        if (e != null && add != null) {
            if (e.checkType().equals("int") && t.checkType().equals("int"))
                type = "int";
            else
                throw new TypeException(
                        "Arithmetic operation needs type int.", line);
        }
        else type = t.checkType();
        TypeChecker.debug("ENode on " + line + " assigned " + type);
        return type;
    }

    public boolean isLNode() {
        if (this.add == null && t.isLNode()) return true;
        return false;
    }

    public void printRec(int depth) {
        printDepth(depth);
        System.out.print("E\n");
        if (e != null && add != null) {
            e.printRec(depth+1);
            add.printRec(depth+1);
        }
        t.printRec(depth+1);
        if (next != null) next.printRec(depth);
    }
}
