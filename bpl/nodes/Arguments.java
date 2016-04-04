package bpl.nodes;
import bpl.TypeChecker;
import bpl.Token;
import bpl.LocalDecList;
import bpl.exceptions.*;
import java.util.HashMap;
public class Arguments extends ExpressionNode {

    public ArgumentList al;

    public Arguments(int line) {
        this.line = line;
        this.kind = ARGS;
    }

    public Arguments(int line, ArgumentList al) {
        this(line);
        this.al = al;
    }

    public void findReferences(HashMap<String, DeclarationNode> symbolTable,
            LocalDecList localDecs) throws TypeException {
        if (al != null) {
            al.findReferences(symbolTable, localDecs);
        }
    }

    public String checkType(Parameters params) throws TypeException {
        type = "void";
        if (params.pl == null && al == null) return type;
        else if (params.pl == null || al == null)
            throw new TypeException("Argument mismatch.", line);
        Expression ex = al.head;
        Parameter pr = params.pl.head;
        while (ex != null && pr != null) {
            String extype = ex.checkType();
            if (!extype.equals(pr.getType()))
                throw new TypeException("Argument mismatch.", line);
            TypeChecker.debug("Matched param type at " + line +
                    " with type " + extype);
            ex = ex.next;
            pr = pr.next;
        }
        if (ex != null || pr != null)
            throw new TypeException("Argument mismatch.", line);
        return type;
    }

    public void printRec(int depth) {
        printDepth(depth);
        if (al != null) {
            System.out.print("Arguments\n");
            al.printRec(depth+1);
        }
        else
            System.out.print("No arguments\n");
        if (next != null) next.printRec(depth);
    }
}
