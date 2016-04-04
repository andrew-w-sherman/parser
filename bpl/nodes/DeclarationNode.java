package bpl.nodes;
import bpl.TypeChecker;
import bpl.Token;
import bpl.LocalDecList;
import bpl.exceptions.*;
import java.util.HashMap;
// declaration node declares a variable along with its type
// i.e. int n
public abstract class DeclarationNode extends TreeNode {

    public TypeSpecifier ts = null;
    public boolean isPtr = false;
    public boolean isArray = false;
    public Token name = null;

    public boolean isFunDec = false;
    public Parameters params = null;
}
