package bpl.nodes;
import bpl.Token;
import bpl.exceptions.*;
public class FunctionDeclaration extends DeclarationNode {

    public TypeSpecifier ts;
    public Token name;
    public Parameters params;
    public CompoundStatement cs;

    public FunctionDeclaration(int line, TypeSpecifier ts,
            Token name, Parameters params, CompoundStatement cs) {
        this.line = line;
        this.type = FUN_DEC;
        this.ts = ts;
        this.name = name;
        this.params = params;
        this.cs = cs;
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
