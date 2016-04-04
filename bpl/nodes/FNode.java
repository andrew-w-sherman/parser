package bpl.nodes;
import bpl.TypeChecker;
import bpl.Token;
import bpl.LocalDecList;
import bpl.exceptions.*;
import java.util.HashMap;
public class FNode extends ExpressionNode {

    public Token ptrOp;
    public Factor fact;
    boolean negation;
    FNode fn;

    private FNode(int line) {
        this.line = line;
        this.kind = F;
        this.negation = false;
    }

    public FNode(int line, Factor fact) {
        this(line);
        this.fact = fact;
    }

    public FNode(int line, Token ptrOp, Factor fact) {
        this(line);
        this.ptrOp = ptrOp;
        this.fact = fact;
    }

    public FNode(int line, FNode fn) {
        this(line);
        this.negation = true;
        this.fn = fn;
    }

    public void findReferences(HashMap<String, DeclarationNode> symbolTable,
            LocalDecList localDecs) throws TypeException {
        if (fact != null) fact.findReferences(symbolTable, localDecs);
        else if (fn != null) fn.findReferences(symbolTable, localDecs);
    }

    public String checkType() throws TypeException {
        if (fact != null) {
            String factType = fact.checkType();
            if (ptrOp == null) {
                type = factType;
            }
            else if (ptrOp.kind == Token.T_ASTR) {
                // dereference
                if (factType.equals("pointer to int"))
                    type = "int";
                else if (factType.equals("pointer to string"))
                    type = "string";
                else throw new TypeException(
                        "Can't dereference non-pointer.", line);
            }
            else if (ptrOp.kind == Token.T_AMPR) {
                // reference
                if (factType.equals("int")) type = "pointer to int";
                else if (factType.equals("string")) type = "pointer to string";
                else throw new TypeException(
                        "Can't reference given type.", line);
            }
        }
        else if (fn != null) {
            String fnType = fn.checkType();
            if (fnType.equals("int")) type = fnType;
            else
                throw new TypeException("Cannot negate non-int.", line);
        }
        TypeChecker.debug("FNode on " + line + " assigned " + type);
        return type;
    }

    public boolean isLNode() {
        if (!negation && ptrOp == null) return fact.isLNode();
        return false;
    }

    public void printRec(int depth) {
        printDepth(depth);
        if (negation) System.out.print("-");
        if (ptrOp != null) {
            System.out.print(ptrOp.value);
        }
        System.out.print("F\n");
        if (fact != null) fact.printRec(depth + 1);
        if (fn != null) fn.printRec(depth + 1);
        if (next != null) next.printRec(depth);
    }
}
