package bpl.nodes;
import bpl.TypeChecker;
import bpl.Token;
import bpl.LocalDecList;
import bpl.exceptions.*;
import java.util.HashMap;
public class ExpressionStatement extends StatementNode {

    public Expression expression;

    public ExpressionStatement(int line, Expression expression) {
        this.expression = expression;
        this.kind = EXPRESSION_STMT;
        this.line = line;
    }

    public void findReferences(HashMap<String, DeclarationNode> symbolTable,
            LocalDecList localDecs) throws TypeException {
        expression.findReferences(symbolTable, localDecs);
    }

    public void checkType(String rt) throws TypeException {
        expression.checkType();
    }

    public void printRec(int depth) {
        printDepth(depth);
        System.out.print("Expression Statement");
        System.out.print("\n");
        expression.printRec(depth + 1);
        if (next != null) next.printRec(depth);
    }

}
