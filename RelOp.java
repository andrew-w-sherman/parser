public class RelOp extends ExpressionNode {

    public Token token;

    public RelOp(int line, Token token) {
        this.token = token;
        this.line = line;
        this.type = RELOP;
    }

    public void printRec(int depth) {
        printDepth(depth);
        System.out.println(token.value);
        if (next != null) next.printRec(depth);
    }
}
