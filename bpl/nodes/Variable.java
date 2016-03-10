package bpl.nodes;
import bpl.Token;
import bpl.exceptions.*;
public class Variable extends ExpressionNode {

    public Token name;
    public Token astr;
    public Expression ex;

    private Variable(int line) {
        this.type = VAR;
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
