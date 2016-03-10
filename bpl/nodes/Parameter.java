package bpl.nodes;
import bpl.Token;
import bpl.exceptions.*;
public class Parameter extends DeclarationNode {

    public TypeSpecifier ts;
    public boolean isPtr;
    public boolean isArray;
    public Token name;
    public Parameter next;

    public Parameter(int line, TypeSpecifier ts, Token name) {
        this.line = line;
        this.name = name;
        this.ts = ts;
        this.type = PARAM;
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
