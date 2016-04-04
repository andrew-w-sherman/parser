package bpl.nodes;
import bpl.TypeChecker;
import bpl.Token;
import bpl.LocalDecList;
import bpl.exceptions.*;
import java.util.HashMap;
public class TNode extends ExpressionNode {

    public TNode t;
    public MulOp mul;
    public FNode f;

    public TNode(int line) {
        this.line = line;
        this.kind = T;
    }

    public TNode(int line, FNode f) {
        this(line);
        this.f = f;
    }

    public TNode(int line, TNode t, MulOp mul, FNode f) {
        this(line);
        this.t = t;
        this.mul = mul;
        this.f = f;
    }

    public void findReferences(HashMap<String, DeclarationNode> symbolTable,
            LocalDecList localDecs) throws TypeException {
        if (t != null && mul != null) {
            t.findReferences(symbolTable, localDecs);
        }
        f.findReferences(symbolTable, localDecs);
    }

    public String checkType() throws TypeException {
        String fType = f.checkType();
        if (t != null && mul != null) {
            // numeric op
            String tType = t.checkType();
            if (tType.equals("int") && fType.equals("int"))
                type = "int";
            else
                throw new TypeException(
                        "Arithmetic operation needs type int.", line);
        }
        else type = fType;
        TypeChecker.debug("TNode on " + line + " assigned " + type);
        return type;
    }

    public boolean isLNode() {
        if (mul == null && f.isLNode()) return true;
        return false;
    }

    public void printRec(int depth) {
        printDepth(depth);
        System.out.print("T\n");
        if (t != null && mul != null) {
            t.printRec(depth+1);
            mul.printRec(depth+1);
        }
        f.printRec(depth+1);
        if (next != null) next.printRec(depth);
    }
}
