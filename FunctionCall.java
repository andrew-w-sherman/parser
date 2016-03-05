public class FunctionCall extends ExpressionNode {

    public Token name;
    public Arguments args;

    private FunctionCall(int line) {
        this.line = line;
        this.type = FUN_CALL;
    }

    public FunctionCall(int line, Token name, Arguments args) {
        this(line);
        this.name = name;
        this.args = args;
    }

    public void printRec(int depth) {
        if (name != null && args != null) {
            printDepth(depth);
            System.out.print("Call to "+name.value+"\n");
            args.printRec(depth + 1);
        }
        else System.out.println("Screwed up! (FunctionCall.java)");
        if(next != null) next.printRec(depth);
    }
}
