public class TypeSpecifier extends DeclarationNode {

    public Token typeSpec;

    public TypeSpecifier(int line, Token typeSpec) {
        this.typeSpec = typeSpec;
        this.type = TYPE_SPECIFIER;
        this.line = line;
    }

    public void printRec(int depth) {
        printDepth(depth);
        System.out.print(typeSpec.value+"\n");
        if (next != null) next.printRec(depth);
    }
}
