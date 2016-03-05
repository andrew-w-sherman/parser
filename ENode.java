public class ENode extends ExpressionNode {

    public ENode e;
    public AddOp add;
    public TNode t;

    public ENode(int line) {
        this.line = line;
        this.type = E;
    }

    public ENode(int line, TNode t) {
        this(line);
        this.t = t;
    }

    public ENode(int line, ENode e, AddOp add, TNode t) {
        this(line);
        this.e = e;
        this.add = add;
        this.t = t;
    }

    public boolean isLNode() {
        if (this.add == null && t.isLNode()) return true;
        return false;
    }

    public void printRec(int depth) {
        printDepth(depth);
        System.out.print("E\n");
        if (e != null && add != null) {
            e.printRec(depth+1);
            add.printRec(depth+1);
        }
        t.printRec(depth+1);
        if (next != null) next.printRec(depth);
    }
}
