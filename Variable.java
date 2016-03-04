public class Variable extends ExpressionNode {

    public String value;

    public Variable(int line, String value) {
        this.value = value;
        this.type = VAR;
        this.line = line;
    }

    public void printRec(int depth) {
        for (int i = 0; i < depth; i++){
            System.out.print("  ");
        }
        System.out.print("Variable: " + value);
        System.out.print("\n");
        if (next != null) next.printRec(depth);
    }
}
