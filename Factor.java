public class Factor extends ExpressionNode {

    public Expression ex;
    public Token readToken; // it's a special case! :O
    public FunctionCall fc;
    public Literal lit;
    public Variable var;

    private Factor(int line) {
        this.line = line;
        this.type = COMP_EXP;
    }

    public Factor(int line, Expression ex) {
        this(line);
        this.ex = ex;
    }

    public Factor(int line, Token token) throws ParserException {
        this(line);
        if (token.kind != Token.T_READ)
            throw new ParserException("something awful in factor", line);
        this.readToken = token;
    }

    public Factor(int line, FunctionCall fc) {
        this(line);
        this.fc = fc;
    }

    public Factor(int line, Literal lit) {
        this(line);
        this.lit = lit;
    }

    public Factor(int line, Variable var, Expression ex)

    public Factor(int line, Variable var) {
        this(line);
        this.var = var;
    }

    // TODO this printrec is gonna be weird
    public void printRec(int depth) {
        printDepth(depth);
        System.out.print("Factor\n");
        if (ex != null) ex.printRec(depth+1);
        else if (readToken != null) {
            printDepth(depth+1);
            System.out.print("Read call\n");
        }
        else if (fc != null) fc.printRec(depth+1);
        else if (lit != null) lit.printRec(depth+1);
        else if (var != null) var.printRec(depth+1);
        else System.out.println("Shouldn't get here! (Factor.java)");
        if (next != null) next.printRec(depth);
    }
}
