package bpl;
import bpl.nodes.*;
import bpl.exceptions.*;

import java.io.PrintWriter;
import java.io.IOException;

public class Generator {

    DeclarationList root;
    PrintWriter out;

    private int labelNum = 0;

    public Generator (String outFileName)
        throws GenException {
        try {
            // create the buffer
            out = new PrintWriter(outFileName, "UTF-8");
        } catch (IOException e) {
            throw new GenException("Couldn't create output file.");
        }
    }

    public void generate(DeclarationList root) {
        this.root = root;
        makeHeaders();
        Declaration dec = root.head;
        while (dec != null) {
            if (dec.fd != null) {
                dec.fd.markVariablesFunc();
                genCodeFunctionDec(dec.fd);
            }
            // global vars are declared already
            dec = dec.next;
        }
        out.flush();
        out.close();
    }

    private void makeHeaders() {
        // for now, let's just have number writing stuff
        out.println(".section .rodata");
        out.println(".WriteIntString: .string \"%d\"");
        out.println(".WritelnString: .string \"\\n\"");
        out.println(".WriteStringString: .string \"%s\"");
        out.println(".ReadIntString: .string \"%d\"");
        // here we'll also need to take care of globals, str lits, etc.
        out.println("");
        out.println(".text");
        out.println(".globl main");
        out.println("");
    }

    private void genCodeFunctionDec(FunctionDeclaration f) {
        out.println(f.name.value + ":");
        genRegReg("movq", sp, fp, "setup fp");
        int numLoc = f.maxPos;
        // allocate locals
        genImmReg("subq", (numLoc * 8) + "", sp,
                "Put local vars in frame");
        // allocate temp vars
        genReg("push", tempq, "push the temp");
        genStatement(f.cs.sl.head);
        // deallocate temp vars
        genReg("pop", tempq, "pop the temp");
        // dealloc locals
        genImmReg("addq", (numLoc * 8) + "", sp,
                "Pull local vars off frame");
        genOp("ret", "return from the function");
    }

    private void genStatement(StatementNode sn) {
        if (sn.kind == TreeNode.WRITE_STMT) {
            WriteStatement ws = (WriteStatement) sn;
            if (ws.ex == null) {
                genImmReg("movq", ".WritelnString", arg1q, "put writeln");
            }
            else {
                genCodeExpression(ws.ex);
                // result is in eax/rax
                if (ws.ex.type == "int") {
                    genImmReg("movq", ".WriteIntString", arg1q, "put %d");
                    genRegReg("movl", accum, arg2, "move accum to arg2");
                }
                else if (ws.ex.type.equals("string")) {
                    genImmReg("movq", "WriteString", arg1q, "put %s");
                }
                else System.out.println("something is wrong in Generator");
            }
            genImmReg("movl", "0", accum, "clear accum");
            genCall("printf", "print the thing");
        }
        else if (sn.kind == TreeNode.STATEMENT) {
            Statement s = (Statement) sn;
            genStatement(s.sn);
            if (s.next != null) genStatement(s.next);
        }
        else if (sn.kind == TreeNode.EXPRESSION_STMT) {
            ExpressionStatement es = (ExpressionStatement) sn;
            genCodeExpression(es.expression);
        }
        else if (sn.kind == TreeNode.COMPOUND_STMT) {
            CompoundStatement cs = (CompoundStatement) sn;
            genStatement(cs.sl.head);
        }
        else if (sn.kind == TreeNode.IF_STMT) {
            IfStatement is = (IfStatement) sn;
            // result is in eax/rax
            String l1 = getLabel();
            genCodeExpression(is.ex);
            // cond jump past first statement
            genImmReg("cmpl", "0", accum, "compare to 0");
            genDir("je", l1, "cond jump to l2");
            genStatement(is.st1);
            if (is.st2 != null) {
                String l2 = getLabel();
                genDir("jmp", l2, "noncond to end of if");
                // label for first statement
                genLabelMark(l1);
                genStatement(is.st2);
                //label for second statement
                genLabelMark(l2);
            }
            else {
                //label for first statement
                genLabelMark(l1);
            }
            
        }
        else if (sn.kind == TreeNode.WHILE_STMT) {
            WhileStatement ws = (WhileStatement) sn;
            // result is in eax/rax
            // manipulate it for cond jumping
            String l1 = getLabel();
            genLabelMark(l1);
            String l2 = getLabel();
            genCodeExpression(ws.ex);
            genImmReg("cmpl", "0", accum, "compare to 0");
            genDir("je", l2, "cond jump to l2");
            genStatement(ws.st);
            // noncond jump to L1
            genDir("jmp", l1, "back to start of while");
            genLabelMark(l2);
        }
        else if (sn.kind == TreeNode.RETURN_STMT) {
            ReturnStatement rs = (ReturnStatement) sn;
            if (rs.ex != null)
                genCodeExpression(rs.ex);
            int numLoc = rs.fd.maxPos;
            // deallocate temp vars
            genReg("pop", tempq, "pop the temp");
            // dealloc locals
            genImmReg("addq", (numLoc * 8) + "", sp,
                    "Pull local vars off frame");
            genOp("ret", "return from the function");
        }
    }

    private void genCodeExpression(Expression ex) {
        if (ex.ex != null) {
            // this is where assignment happens
            genCodeExpression(ex.ex); // it's in the accumulator
            if (ex.var.declaration.depth == 0) System.out.println(
                    "no globals yet");
            else
                genRegOff("movl", accum, offsetOf(ex.var), fp, "get l var");
        } 
        else {
            // this is where normal happens
            genArithmetic(ex.ce);
        }

        // if (ex.next != null) genCodeExpression(ex.next);
    }

    private void genArithmetic(ExpressionNode en) {
        if (en.kind == TreeNode.COMP_EXP) {
            CompoundExpression ce = (CompoundExpression) en;
            if (ce.rel == null) {
                // this is where normal happens
                genArithmetic(ce.e1);
            }
            else {
                // comparisons!
                genArithmetic(ce.e1);
                genReg("push", accumq, "push L to stack");
                genArithmetic(ce.e2);
                genRegOff("cmpl", accum, 0, sp, "compare");
                String l1 = getLabel();
                genRelOpJump(ce.rel, l1);
                genImmReg("movl", "0", accum, "put 0");
                String l2 = getLabel();
                genDir("jmp", l2, "jump through");
                genLabelMark(l1);
                genImmReg("movl", "1", accum, "put 1");
                genLabelMark(l2);
                genImmReg("addq", "8", sp, "pop the L value");
            }
        }
        if (en.kind == TreeNode.E) {
            ENode e = (ENode) en;
            if (e.add == null) {
                genArithmetic(e.t);
            }
            else {
                genArithmetic(e.e);
                genReg("push", accumq, "push L to stack");
                genArithmetic(e.t);
                if (e.add.token.kind == Token.T_PLUS)
                    genOffReg("addl", 0, sp, accum, "add L and R");
                if (e.add.token.kind == Token.T_MINUS) {
                    genOffReg("movl", 0, sp, temp, "move L to temp");
                    genRegReg("subl", accum, temp, "sub R from temp");
                    genRegReg("movl", temp, accum, "move temp into acc");
                }
                genImmReg("addq", "8", sp, "pop the L value");
            }
        }
        if (en.kind == TreeNode.T) {
            TNode t = (TNode) en;
            if (t.mul == null) {
                genFNode(t.f);
            }
            else {
                genArithmetic(t.t);
                genReg("push", accumq, "push L to stack");
                genFNode(t.f);
                if (t.mul.token.kind == Token.T_ASTR)
                    genOffReg("imul", 0, sp, accum, "multiply L and R");
                else {
                    genRegReg("movl", accum, temp,
                            "R (the divisor) into ebp");
                    genOffReg("movl", 0, sp, accum,
                            "L (the dividend) into accumulator");
                    genOp("cltq", "convert long to quad");
                    genOp("cqto", "convert quad to oct");
                    genReg("idivl", temp, "do the division!");
                    if (t.mul.token.kind == Token.T_MOD)
                        genRegReg("movl", "%edx", accum, "move mod to accum");
                }
                genImmReg("addq", "8", sp, "pop the L value");
            }
        }
    }

    private void genFNode(FNode f) {
        if (f.ptrOp != null) {}
        else if (f.negation) {}
        else {
            genFactor(f.fact);
        }
    }

    private void genFactor(Factor fact) {
        if (fact.readToken != null) {}
        else if (fact.fc != null) genFunctionCall(fact.fc);
        else if (fact.ex != null) {}
        else if (fact.var != null) genVariableRef(fact.var);
        else if (fact.lit != null) genLiteral(fact.lit);
        else
            System.out.println("Something's wrong (Generator)");
    }

    private void genLiteral(Literal lit) {
        if (lit.numVal != null) {
            genImmReg("movl", ""+lit.numVal, accum, "put literal in accum");
        }
        else {
            // put the string in the place
        }
    }
    
    private void genVariableRef(Variable v) {
        if (v.type.equals("int") && v.declaration.depth == 0) {
            // globals here
        }
        else if (v.type.equals("int")) {
            genOffReg("movl", offsetOf(v), fp, accum, "get a param variable");
        }
    }

    private int offsetOf(Variable v) {
        int offset = 0; // garbage value
        if (v.declaration.depth == 1) {
            offset = (v.declaration.position + 2) * 8;
        }
        else if (v.declaration.depth > 1) {
            offset = (v.declaration.position + 1) * -8;
        }
        else System.out.println("Oh golly");
        return offset;
    }

    private void genFunctionCall(FunctionCall fc) {
        // evaluate the arguments & push
        int numArgs;
        if (fc.args.al == null) numArgs = 0;
        else numArgs = pushArgs(fc.args.al.head);
        genReg("push", fp, "push the frame pointer");
        genDir("call", fc.name.value, "call function");
        genReg("pop", fp, "restore the frame pointer");
        // pop the arguments
        genImmReg("addq", (numArgs * 8) + "", sp, "pop args");
    }

    private int pushArgs(Expression arg) {
        // we're pushing the last arg first so we use tail recursion
        int howMany;
        if (arg.next != null) howMany = pushArgs(arg.next);
        else howMany = 0;
        genCodeExpression(arg);
        genReg("push", accumq, "push the argument result");
        howMany++;
        return howMany;
    }

    private String getLabel() {
        String label = String.format(".L%d", labelNum);
        labelNum++;
        return label;
    }

    public void genRelOpJump(RelOp r, String label) {
        if (r.token.kind == Token.T_LESS)
            genDir("jl", label, "less than");
        if (r.token.kind == Token.T_LESSEQ)
            genDir("jle", label, "less than or equal");
        if (r.token.kind == Token.T_EQ)
            genDir("je", label, "equal");
        if (r.token.kind == Token.T_NEQ)
            genDir("jne", label, "not equal");
        if (r.token.kind == Token.T_GRTR)
            genDir("jg", label, "greater than");
        if (r.token.kind == Token.T_GRTREQ)
            genDir("jge", label, "greater than or equal");
    }

    // formatting functions
    public void genRegReg(String opcode, String r1, String r2, String comment){
        out.printf("\t %4s %4s, %4s %10s #%s\n", opcode, r1, r2, "", comment);
    }

    public void genOffReg(String opcode, int offset, String r1, String r2,
            String comment){
        out.printf("\t %4s %d (%4s), %4s %10s #%s\n", opcode, offset,
                r1, r2, "", comment);
    }

    public void genRegOff(String opcode, String r1, int offset, String r2,
            String comment){
        out.printf("\t %4s %4s, %d (%4s) %10s #%s\n", opcode, r1,
                offset, r2, "", comment);
    }

    public void genImmReg(String opcode, String imm, String r2,
            String comment) {
        out.printf("\t %4s $%3s, %4s %10s #%s\n",
                opcode, imm, r2, "", comment);
    }

    public void genDirReg(String opcode, String label, String r2,
            String comment) {
        out.printf("\t %4s %4s, %4s %10s #%s\n",
                opcode, label, r2, "", comment);
    }

    // single operand
    public void genReg(String opcode, String reg, String comment) {
        out.printf("\t %4s %4s %10s #%s\n", opcode, reg, "", comment);
    }

    public void genOff(String opcode, int off, String reg, String comment) {
        out.printf("\t %4s %d (%4s) %10s #%s\n",
                opcode, off, reg, "", comment);
    }

    public void genImm(String opcode, String imm, String comment) {
        out.printf("\t %4s $%3s %10s #%s\n", opcode, imm, "", comment);
    }

    public void genDir(String opcode, String label, String comment) {
        out.printf("\t %4s %4s %10s #%s\n", opcode, label, "", comment);
    }

    // no operands
    public void genOp(String opcode, String comment) {
        out.printf("\t %4s %10s #%s\n", opcode, "", comment);
    }

    public void genCall(String label, String comment) {
        out.printf("\t call %10s %10s #%s\n", label, "", comment);
    }

    public void genLabelMark(String label) {
        out.printf("%s:\n", label);
    }

    // register name constants
    public static final String fp = "%rbx";
    public static final String sp = "%rsp";
    public static final String accum = "%eax";
    public static final String accumq = "%rax";
    public static final String arg1 = "%edi";
    public static final String arg2 = "%esi";
    public static final String arg1q = "%rdi";
    public static final String arg2q = "%rsi";
    public static final String temp = "%ebp";
    public static final String tempq = "%rbp";
}
