package bpl.nodes;
import bpl.TypeChecker;
import bpl.Token;
import bpl.LocalDecList;
import bpl.exceptions.*;
import java.util.HashMap;
public class TypeSpecifier extends DeclarationNode {

    public Token typeSpec;

    public TypeSpecifier(int line, Token typeSpec) {
        this.typeSpec = typeSpec;
        this.kind = TYPE_SPECIFIER;
        this.line = line;
    }

    public void printRec(int depth) {
        printDepth(depth);
        System.out.print(typeSpec.value+"\n");
        if (next != null) next.printRec(depth);
    }
}
