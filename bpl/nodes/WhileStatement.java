package bpl.nodes;
import bpl.TypeChecker;
import bpl.Token;
import bpl.LocalDecList;
import bpl.exceptions.*;
import java.util.HashMap;
public class WhileStatement extends StatementNode {

    public Expression ex;
    public Statement st;

    public WhileStatement(int line, Expression ex, Statement st) {
        this.ex = ex;
        this.st = st;
        this.line = line;
        this.kind = WHILE_STMT;
    }

    public void findReferences(HashMap<String, DeclarationNode> symbolTable,
            LocalDecList localDecs) throws TypeException {
        ex.findReferences(symbolTable, localDecs);
        st.findReferences(symbolTable, localDecs);
    }

    public void checkType(String rt) throws TypeException {
        if (!ex.checkType().equals("int"))
            throw new TypeException(
                    "While predicate must be type int.", line);
        st.checkType(rt);
    }

    public int markVariables(int position, int depth,
            FunctionDeclaration fd) {
        return st.markVariables(position, depth, fd);
    }

    public void printRec(int depth) {
        printDepth(depth);
        System.out.print("While:\n");
        ex.printRec(depth+1);
        printDepth(depth);
        System.out.print("do:\n");
        st.printRec(depth+1);
        printDepth(depth);
        System.out.print("Endwhile\n");
        if (next != null) next.printRec(depth);
    }
}
