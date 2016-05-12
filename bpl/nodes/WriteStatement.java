package bpl.nodes;
import bpl.TypeChecker;
import bpl.Token;
import bpl.LocalDecList;
import bpl.exceptions.*;
import java.util.HashMap;
public class WriteStatement extends StatementNode {

    public Expression ex;

    public WriteStatement(int line) {
        this.line = line;
        this.kind = WRITE_STMT;
    }

    public WriteStatement(int line, Expression ex) {
        this(line);
        this.ex = ex;
    }

    public void findReferences(HashMap<String, DeclarationNode> symbolTable,
            LocalDecList localDecs) throws TypeException {
        if (ex != null) ex.findReferences(symbolTable, localDecs);
    }

    public void checkType(String rt) throws TypeException {
        if (ex == null) return;
        String exType = ex.checkType();
        if (!(exType.equals("int") || exType.equals("string"))) {
            throw new TypeException("Write statement argument must be" +
                    " printable.", line);
        }
    }

    public void printRec(int depth) {
        printDepth(depth);
        if (ex != null) {
            System.out.print("Write:\n");
            ex.printRec(depth+1);
        }
        else
            System.out.print("WriteLn\n");
        if (next != null) next.printRec(depth);
    }
}
