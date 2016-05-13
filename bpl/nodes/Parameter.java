package bpl.nodes;
import bpl.TypeChecker;
import bpl.Token;
import bpl.LocalDecList;
import bpl.exceptions.*;
import java.util.HashMap;
public class Parameter extends DeclarationNode {

    public Parameter next;
    public String type;

    public Parameter(int line, TypeSpecifier ts, Token name) {
        this.line = line;
        this.name = name;
        this.ts = ts;
        this.kind = PARAM;
        this.isPtr = false;
        this.isArray = false;
    }

    public Parameter(int line, TypeSpecifier ts, Token ptr, Token name) {
        this(line, ts, name);
        this.isPtr = true;
    }

    public Parameter(int line, TypeSpecifier ts, Token name, boolean array) {
        this(line, ts, name);
        this.isArray = true;
    }

    public String getType() {
        if (ts.typeSpec.kind == Token.T_STRING)
            type = "string";
        else if (ts.typeSpec.kind == Token.T_INT)
            type = "int";
        else
            System.out.println("oh dang... (Variable.java)");
        if (isPtr)
            type = "pointer to " + type;
        else if (isArray)
            type = type + " array";
        return type;
    }

    public int markVariables(int position, int depth, FunctionDeclaration fd)
    {
        this.position = position;
        this.depth = depth;
        position++;
        if (next != null)
            position = next.markVariables(position, depth, fd);
        return position;
    }

    public void printRec(int depth) {
        printDepth(depth);
        if (isPtr) {
            System.out.print("Param: *"+name.value+"\n");
            ts.printRec(depth+1);
        }
        else if (isArray) {
            System.out.print("Param: "+name.value+"[]\n");
            ts.printRec(depth+1);
        }
        else {
            System.out.print("Param: "+name.value+"\n");
            ts.printRec(depth+1);
        }
        if (next != null) next.printRec(depth);
    }
}
