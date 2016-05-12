package bpl.nodes;
import bpl.TypeChecker;
import bpl.Token;
import bpl.LocalDecList;
import bpl.exceptions.*;
import java.util.HashMap;
public class VariableDeclaration extends DeclarationNode {

    public Integer size = null;
    public VariableDeclaration next;

    public int position;
    public int depth;

    public VariableDeclaration(int line, TypeSpecifier ts, Token name) {
        this.line = line;
        this.name = name;
        this.ts = ts;
        this.kind = VAR_DEC;
        this.isPtr = false;
        this.isArray = false;
    }

    public VariableDeclaration(int line, TypeSpecifier ts, Token ptr, Token name) {
        this(line, ts, name);
        this.isPtr = true;
        this.isArray = false;
    }

    public VariableDeclaration(int line, TypeSpecifier ts, Token name, int size) {
        this(line, ts, name);
        this.size = size;
        this.isPtr = false;
        this.isArray = true;
    }

    public int markVariables(int position, int depth,
            FunctionDeclaration fd) {
        position++;
        this.position = position;
        this.depth = depth;
        if (next != null) {
            position = next.markVariables(position, depth, fd);
        }
        fd.setMaxPos(position);
        return position;
    }

    public void printRec(int depth) {
        printDepth(depth);
        if (isPtr) {
            System.out.print("Vardec: *"+name.value+"\n");
            ts.printRec(depth+1);
        }
        else if (size != null) {
            System.out.print("Vardec: "+name.value+"["+size+"]\n");
            ts.printRec(depth+1);
        }
        else {
            System.out.print("Vardec: "+name.value+"\n");
            ts.printRec(depth+1);
        }
        if (next != null) next.printRec(depth);
    }
}
