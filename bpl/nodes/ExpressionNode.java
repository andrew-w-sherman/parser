package bpl.nodes;
import bpl.TypeChecker;
import bpl.Token;
import bpl.LocalDecList;
import bpl.exceptions.*;
import java.util.HashMap;
// an expression computes a value, such as x + 1
public abstract class ExpressionNode extends TreeNode {

    public boolean isLNode() { return false; }

    public String type;

    public void findReferences(HashMap<String, DeclarationNode> symbolTable,
            LocalDecList localDecs) throws TypeException {}

    public String checkType() throws TypeException {
        System.out.println("Something is really wrong.");
        return "uhhhhhhhhhh";
    }

}
