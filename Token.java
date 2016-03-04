/* Token class */

class Token {

    public int kind;
    public String value;
    public int line;
    
    public Token(int kind, String value, int line) {
        // instantiation
        this.kind = kind;
        this.value = value;
        this.line = line;
    }

    @Override public String toString() {
        // find a way to print token name, not just number
        return ("Token " + kind + "\tValue: \"" + value + "\"\t    Line: " + line);
    }

    public boolean isRelOp() {
        if (kind >= 121 && kind <= 126) return true;
        return false;
    }

    public static int identKind(String id) {
        if(id.equals("int")) return T_INT;
        if(id.equals("void")) return T_VOID;
        if(id.equals("string")) return T_STRING;
        if(id.equals("if")) return T_IF;
        if(id.equals("else")) return T_ELSE;
        if(id.equals("while")) return T_WHILE;
        if(id.equals("return")) return T_RETURN;
        if(id.equals("write")) return T_WRITE;
        if(id.equals("writeln")) return T_WRITELN;
        if(id.equals("read")) return T_READ;
        return T_IDEN;
    }

    public static int T_IDEN    = 100;
    public static int T_NUM     = 101;
    public static int T_INT     = 102;
    public static int T_VOID    = 103;
    public static int T_STRING  = 104;
    public static int T_IF      = 105;
    public static int T_ELSE    = 106;
    public static int T_WHILE   = 107;
    public static int T_RETURN  = 108;
    public static int T_WRITE   = 109;
    public static int T_WRITELN = 110;
    public static int T_READ    = 111;
    public static int T_SEMICOL = 112;
    public static int T_COMMA   = 113;
    public static int T_LBRACK  = 114;
    public static int T_RBRACK  = 115;
    public static int T_LBRACE  = 116;
    public static int T_RBRACE  = 117;
    public static int T_LPAREN  = 118;
    public static int T_RPAREN  = 119;
    public static int T_ASSN    = 120;
    public static int T_LESS    = 121;
    public static int T_LESSEQ  = 122;
    public static int T_EQ      = 123;
    public static int T_NEQ     = 124;
    public static int T_GRTREQ  = 125;
    public static int T_GRTR    = 126;
    public static int T_PLUS    = 127;
    public static int T_MINUS   = 128;
    public static int T_ASTR    = 129;
    public static int T_DIV     = 130;
    public static int T_MOD     = 131;
    public static int T_AMPR    = 132;
    public static int T_STRLIT  = 133;
    public static int T_EOF     = 999;
    
}

