public class ExpressionStatement extends StatementNode {

    public Expression expression;

    public ExpressionStatement(int line, Expression expression) {
        this.expression = expression;
        this.type = EXPRESSION_STMT;
        this.line = line;
    }

    public void printRec(int depth) {
        printDepth(depth);
        System.out.print("Expression Statement");
        System.out.print("\n");
        expression.printRec(depth + 1);
        if (next != null) next.printRec(depth);
    }
}
