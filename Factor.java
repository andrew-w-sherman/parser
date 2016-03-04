public class Factor extends ExpressionNode {

    public Expression ex;
    public Variable var;

    private Factor(int line) {
        this.line = line;
        this.type = COMP_EXP;
    }

    public Factor(int line, Expression ex) {
        this(line);
        this.ex = ex;
    }

    public Factor(int line, Variable var) {
        this(line);
        this.var = var;
    }

    public void printRec(int depth) {
        printDepth(depth);
        System.out.print("Factor\n");
        if (ex != null) ex.printRec(depth+1);
        else if (var != null) var.printRec(depth+1);
        else System.out.println("Shouldn't get here! (Factor.java)");
        if (next != null) next.printRec(depth);
    }
}
