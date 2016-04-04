package bpl.nodes;
import bpl.TypeChecker;
import bpl.Token;
import bpl.LocalDecList;
import bpl.exceptions.*;
import java.util.HashMap;
public abstract class TreeNode {

    public int kind;
    public int line;
    TreeNode next;

    public abstract void printRec(int depth);

    public static void printDepth(int depth) {
        for (int i = 0; i < depth; i++) {
            System.out.print("  ");
        }
    }


    public static final int PROGRAM = 1;
    public static final int DECLARATION_LIST = 2;
    public static final int DECLARATION = 3;
    public static final int VAR_DEC = 4;
    public static final int TYPE_SPECIFIER = 5;
    public static final int FUN_DEC = 6;
    public static final int PARAMS = 7;
    public static final int PARAM_LIST = 8;
    public static final int PARAM = 9;
    public static final int COMPOUND_STMT = 10;
    public static final int LOCAL_DECS = 11;
    public static final int STATEMENT_LIST = 12;
    public static final int STATEMENT = 13;
    public static final int EXPRESSION_STMT = 14;
    public static final int IF_STMT = 15;
    public static final int WHILE_STMT = 16;
    public static final int RETURN_STMT = 17;
    public static final int WRITE_STMT = 18;
    public static final int EXPRESSION = 19;
    public static final int VAR = 20;
    public static final int COMP_EXP = 21;
    public static final int RELOP = 22;
    public static final int E = 23;
    public static final int ADDOP = 24;
    public static final int T = 25;
    public static final int MULOP = 26;
    public static final int F = 27;
    public static final int FACTOR = 28;
    public static final int FUN_CALL = 29;
    public static final int ARGS = 30;
    public static final int ARG_LIST = 31;
}
