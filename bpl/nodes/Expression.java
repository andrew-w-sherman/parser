package bpl.nodes;
import bpl.TypeChecker;
import bpl.Token;
import bpl.LocalDecList;
import bpl.exceptions.*;
import java.util.HashMap;
public class Expression extends ExpressionNode {

    public Variable var;
    public Expression ex;
    public CompoundExpression ce;

    public Expression next;

    public Expression(int line) {
        this.line = line;
        this.kind = EXPRESSION;
    }

    public Expression(int line, Variable var, Expression ex) {
        this(line);
        this.var = var;
        this.ex = ex;
    }

    public Expression(int line, CompoundExpression ce) {
        this(line);
        this.ce = ce;
    }

    public void findReferences(HashMap<String, DeclarationNode> symbolTable,
            LocalDecList localDecs) throws TypeException {
        if (var != null && ex != null) {
            // assignment statement
            var.findReferences(symbolTable, localDecs);
            ex.findReferences(symbolTable, localDecs);
        }
        else if (ce != null) {
            // compound expression
            ce.findReferences(symbolTable, localDecs);
        }
        if (next != null) {
            next.findReferences(symbolTable, localDecs);
        }
    }

    public String checkType() throws TypeException {
        if (var != null && ex != null) {
            // assignment!!!
            String varType = var.checkType();
            String exType = ex.checkType();
            if (varType.equals(exType))
                type = exType;
            else throw new TypeException("Mismatched assignment.", line);
            TypeChecker.debug("Assignment Exp on " + line + " assigned "
                    + type);
        }
        else if (ce != null) {
            type = ce.checkType();
            TypeChecker.debug("Exp on " + line + " assigned " + type);
        }
        return type;
    }

    public void printRec(int depth) {
        printDepth(depth);
        System.out.print("Expression\n");
        if (var != null && ex != null) {
            var.printRec(depth + 1);
            printDepth(depth + 1); System.out.print("=\n");
            ex.printRec(depth + 1);
        }
        else if (ce != null) {
            ce.printRec(depth + 1);
        }
        else System.out.println("Nope. (Expression.java)");
        if (next != null) next.printRec(depth);
    }
}
