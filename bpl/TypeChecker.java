package bpl;
import bpl.nodes.*;
import bpl.exceptions.*;
import java.util.LinkedList;
import java.util.HashMap;
public class TypeChecker {

    DeclarationList root;
    public static final boolean DEBUG = true;
    LinkedList<FunctionDeclaration> funDecs;

    public TypeChecker (DeclarationList root) {
        this.root = root;
    }

    public void link() throws TypeException {
        findReferences();
    }

    private void findReferences() throws TypeException {
        LocalDecList localDecs;
        HashMap<String, DeclarationNode> symbolTable = new HashMap<>();
        funDecs = new LinkedList<>();
        for (Declaration dec = root.head; dec != null; dec = dec.next) {
            // put the declaration in the table
            // then if it's a function declaration, put in in the
            // function declaration table for later eval
            // this is so we can define functions in any order,
            // which isn't required but I wanted it anyhow
            if (dec.fd != null) {
                funDecs.add(dec.fd);
                symbolTable.put(dec.fd.name.value, dec.fd);
            }
            if (dec.vd != null) {
                symbolTable.put(dec.vd.name.value, dec.vd);
            }
        }
        for ( int i = 0; i < funDecs.size(); i++ ) {
            // recurse into the declaration
            FunctionDeclaration fd = funDecs.get(i);
            localDecs = null;
            if (fd.params.pl != null) {
                Parameter paramHead = fd.params.pl.head;
                for (Parameter param = paramHead; param != null;
                        param = param.next) {
                    DeclarationNode dec = param;
                    localDecs = new LocalDecList(dec, localDecs);
                }
            }
            fd.cs.findReferences(symbolTable, localDecs);
        }
    }

    public void check() throws TypeException {
        String rt = ""; //return type
        for (int i = 0; i < funDecs.size(); i++) {
            FunctionDeclaration fd = funDecs.get(i);
            if (fd.ts.typeSpec.kind == Token.T_INT) rt = "int";
            if (fd.ts.typeSpec.kind == Token.T_STRING) rt = "string";
            fd.cs.checkType(rt);
        }
    }

    public static DeclarationNode lookup(
            HashMap<String, DeclarationNode> symbolTable,
            LocalDecList localDecs, Token reference) throws TypeException {
        String ref = reference.value;
        DeclarationNode localDec;
        // look through local decs
        for (LocalDecList dec = localDecs; dec != null; dec = dec.next) {
            localDec = dec.dec;
            if (localDec.name.value.equals(ref)) {
                return localDec;
            }
        }
        // check global decs
        if (symbolTable.get(ref) != null)
            return symbolTable.get(ref);
        else
            throw new TypeException("Could not find declaration for symbol "
                    + ref, reference.line);
    }

    public static void debug(String msg) {
        if (DEBUG) System.out.println(msg);
    }
}
