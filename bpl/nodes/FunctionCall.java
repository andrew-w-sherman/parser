package bpl.nodes;
import bpl.TypeChecker;
import bpl.Token;
import bpl.LocalDecList;
import bpl.exceptions.*;
import java.util.HashMap;
import bpl.TypeChecker;
public class FunctionCall extends ExpressionNode {

    public Token name;
    public Arguments args;
    public DeclarationNode declaration;

    private FunctionCall(int line) {
        this.line = line;
        this.kind = FUN_CALL;
    }

    public FunctionCall(int line, Token name, Arguments args) {
        this(line);
        this.name = name;
        this.args = args;
    }
    
    public void findReferences(HashMap<String, DeclarationNode> symbolTable,
            LocalDecList localDecs) throws TypeException {
        // first link the function itself
        declaration = TypeChecker.lookup(symbolTable, localDecs, name);
        // then link its arguments
        args.findReferences(symbolTable, localDecs);
        // print debug
        if (TypeChecker.DEBUG) System.out.println("Linked " + name.value +
                " on line " + name.line + " to declaration on line " +
                declaration.name.line);
    }

    public String checkType() throws TypeException {
        // return type can't be array or pointer (can be void)
        if (declaration.ts.typeSpec.kind == Token.T_STRING)
            type = "string";
        else if (declaration.ts.typeSpec.kind == Token.T_INT)
            type = "int";
        else if (declaration.ts.typeSpec.kind == Token.T_VOID)
            type = "void";
        else
            System.out.println("aw darn... (FunctionCall.java)");
        // arguments!!!
        if (!declaration.isFunDec)
            throw new TypeException("Symbol " + name.value + " is a " +
                    "variable and takes no arguments.", name.line);
        args.checkType(declaration.params);
        TypeChecker.debug("FunCall on " + line + " assigned " + type);
        return type;
    }

    public void printRec(int depth) {
        if (name != null && args != null) {
            printDepth(depth);
            System.out.print("Call to "+name.value+"\n");
            args.printRec(depth + 1);
        }
        else System.out.println("Screwed up! (FunctionCall.java)");
        if(next != null) next.printRec(depth);
    }
}
