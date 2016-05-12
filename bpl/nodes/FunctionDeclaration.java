package bpl.nodes;
import bpl.TypeChecker;
import bpl.Token;
import bpl.LocalDecList;
import bpl.exceptions.*;
import java.util.HashMap;
public class FunctionDeclaration extends DeclarationNode {

    public CompoundStatement cs;

    public int maxPos;

    public FunctionDeclaration(int line, TypeSpecifier ts,
            Token name, Parameters params, CompoundStatement cs)
            throws TypeException {
        this.line = line;
        this.kind = FUN_DEC;
        this.ts = ts;
        this.name = name;
        this.params = params;
        this.cs = cs;
        this.isFunDec = true;
    }

    public void markVariablesFunc() {
        int position = 0;
        int depth = 1;
        this.markVariables(position, depth);
    }

    public void markVariables(int position, int depth) {
        position = params.markVariables(position, depth, this);
        cs.markVariables(position, depth, this);
    }

    public void setMaxPos(int candidate) {
        if (candidate > maxPos) maxPos = candidate;
    }

    public void printRec(int depth) {
        printDepth(depth);
        System.out.print("Function declaration:\n");
        ts.printRec(depth+1);
        printDepth(depth+1); System.out.print(name.value+"\n");
        printDepth(depth);
        System.out.print("Parameter field:\n");
        params.printRec(depth+1);
        printDepth(depth);
        System.out.print("Function Body:\n");
        cs.printRec(depth+1);
    }
}
