package bpl.nodes;
import bpl.Token;
import bpl.exceptions.*;
// an expression computes a value, such as x + 1
public abstract class ExpressionNode extends TreeNode {
    public boolean isLNode() { return false; }
}
