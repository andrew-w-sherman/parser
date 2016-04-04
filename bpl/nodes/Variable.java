package bpl.nodes;
import bpl.TypeChecker;
import bpl.Token;
import bpl.LocalDecList;
import bpl.exceptions.*;
import java.util.HashMap;
public class Variable extends ExpressionNode {

    public Token name;
    public Token astr;
    public Expression ex;

    public DeclarationNode declaration;

    private Variable(int line) {
        this.kind = VAR;
        this.line = line;
    }

    public Variable(int line, Token astr, Token name) {
        this(line,name);
        if (astr.kind != Token.T_ASTR)
            System.out.println("Real bad stuff in Variable.java");
        this.astr = astr;
    }

    public Variable(int line, Token name, Expression ex) {
        this(line,name);
        this.ex = ex;
    }

    public Variable(int line, Token name) {
        this(line);
        this.name = name;
    }

    public void findReferences(HashMap<String, DeclarationNode> symbolTable,
            LocalDecList localDecs) throws TypeException {
        declaration = TypeChecker.lookup(symbolTable, localDecs, name);
        // print debug
        if (TypeChecker.DEBUG)
                System.out.println( "Linked " + name.value +
                " on line " + name.line + " to declaration on line " +
                declaration.name.line);
    }

    public String checkType() throws TypeException {
        if (declaration.isFunDec)
            throw new TypeException("Symbol " + name.value + 
                    " is a function being used as a variable.", name.line);
        if (declaration.ts.typeSpec.kind == Token.T_STRING)
            type = "string";
        else if (declaration.ts.typeSpec.kind == Token.T_INT)
            type = "int";
        else
            System.out.println("oh dang... (Variable.java)");
        if (declaration.isPtr && astr == null)
            type = "pointer to " + type;
        else if (declaration.isArray && ex == null)
            type = type + " array";
        //TypeChecker.debug("Variable at " + line + " assigned " + type);
        return type;
    }

    public void printRec(int depth) {
        for (int i = 0; i < depth; i++){
            System.out.print("  ");
        }
        if (astr != null)
            System.out.print("Variable: *"+name.value+"\n");
        else if (ex != null) {
            System.out.print("Array "+name.value+" index:\n");
            ex.printRec(depth+1);
        }
        else
            System.out.print("Variable: " + name.value+"\n");
        if (next != null) next.printRec(depth);
    }
}
