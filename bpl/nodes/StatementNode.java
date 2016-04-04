package bpl.nodes;
import bpl.TypeChecker;
import bpl.Token;
import bpl.LocalDecList;
import bpl.exceptions.*;
import java.util.HashMap;
// a statement does something, such as write(x)
public abstract class StatementNode extends TreeNode {

    public void findReferences(HashMap<String, DeclarationNode> symbolTable,
            LocalDecList localDecs) throws TypeException {}

    public void checkType(String rt) throws TypeException {}
}
