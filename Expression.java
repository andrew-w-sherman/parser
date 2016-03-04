public class Expression extends ExpressionNode {

    public Variable var;
    public Expression ex;
    public CompoundExpression ce;

    public Expression(int line) {
        this.line = line;
        this.type = EXPRESSION;
    }

    public Expression(int line, Variable var, Expression ex) {
        this(line);
        this.var = var;
        this.ex = ex;
    }

    public Expression(int line, CompoundExpression ce) {
        this(line);
        this.ce = ce;
    }

    public void printRec(int depth) {
        printDepth(depth);
        System.out.print("Expression\n");
        if (var != null && ex != null) {
            var.printRec(depth + 1);
            printDepth(depth + 1); System.out.print("=\n");
            ex.printRec(depth + 1);
        }
        else if (ce != null) {
            ce.printRec(depth + 1);
        }
        else System.out.println("Nope. (Expression.java)");
        if (next != null) next.printRec(depth);
    }
}
