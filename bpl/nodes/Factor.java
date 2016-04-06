package bpl.nodes;
import bpl.TypeChecker;
import bpl.Token;
import bpl.LocalDecList;
import bpl.exceptions.*;
import java.util.HashMap;
public class Factor extends ExpressionNode {

    public Expression ex;
    public Token readToken; // it's a special case! :O
    public FunctionCall fc;
    public Literal lit;
    public Variable var;

    private Factor(int line) {
        this.line = line;
        this.kind = COMP_EXP;
    }

    public Factor(int line, Expression ex) {
        this(line);
        this.ex = ex;
    }

    public Factor(int line, Token token) throws ParserException {
        this(line);
        if (token.kind != Token.T_READ)
            throw new ParserException("something awful in factor", line);
        this.readToken = token;
    }

    public Factor(int line, FunctionCall fc) {
        this(line);
        this.fc = fc;
    }

    public Factor(int line, Literal lit) {
        this(line);
        this.lit = lit;
    }

    public Factor(int line, Variable var) {
        this(line);
        this.var = var;
    }

    public void findReferences(HashMap<String, DeclarationNode> symbolTable,
            LocalDecList localDecs) throws TypeException {
        if (ex != null) ex.findReferences(symbolTable, localDecs);
        else if (fc != null) fc.findReferences(symbolTable, localDecs);
        else if (var != null) var.findReferences(symbolTable, localDecs);
    }

    public String checkType() throws TypeException {
        if (readToken != null) type = "int";
        else if (ex != null) type = ex.checkType();
        else if (fc != null) type = fc.checkType();
        else if (lit != null) type = lit.checkType();
        else if (var != null) type = var.checkType();
        TypeChecker.debug("Factor on " + line + " assigned " + type);
        return type;
    }

    public boolean isLNode() {
        if (var != null) return true;
        if (ex != null) return ex.isLNode();
        return false;
    }

    public void printRec(int depth) {
        printDepth(depth);
        System.out.print("Factor\n");
        if (ex != null) ex.printRec(depth+1);
        else if (readToken != null) {
            printDepth(depth+1);
            System.out.print("Read call\n");
        }
        else if (fc != null) fc.printRec(depth+1);
        else if (lit != null) lit.printRec(depth+1);
        else if (var != null) var.printRec(depth+1);
        else System.out.println("Shouldn't get here! (Factor.java)");
        if (next != null) next.printRec(depth);
    }
}
