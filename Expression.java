public class Expression extends ExpressionNode {

    public Variable var;
    public CompoundExpression ce;

    public Expression(int line) {
        this.line = line;
        this.type = EXPRESSION;
    }

    public Expression(int line, Variable var) {
        this(line);
        this.var = var;
    }

    public Expression(int line, CompoundExpression ce) {
        this(line);
        this.ce = ce;
    }

    public void printRec(int depth) {
        printDepth(depth);
        System.out.print("Expression\n");
        if (var != null) {
            var.printRec(depth + 1);
        }
        else if (ce != null) {
            ce.printRec(depth + 1);
        }
        else System.out.println("Nope. (Expression.java)");
        if (next != null) next.printRec(depth);
    }
}
