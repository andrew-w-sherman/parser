public class VariableDeclaration extends DeclarationNode {

    public TypeSpecifier ts;
    public boolean isPtr;
    public Token name;
    public Integer size = null;

    public VariableDeclaration(int line, TypeSpecifier ts, Token name) {
        this.line = line;
        this.name = name;
        this.ts = ts;
        this.type = VAR_DEC;
        this.isPtr = false;
    }

    public VariableDeclaration(int line, TypeSpecifier ts, Token ptr, Token name) {
        this(line, ts, name);
        this.isPtr = true;
    }

    public VariableDeclaration(int line, TypeSpecifier ts, Token name, int size) {
        this(line, ts, name);
        this.size = size;
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
