package bpl;
import bpl.nodes.*;
import bpl.exceptions.*;
public class Parser{

    Scanner sc;
    Token token;
    Token peekToken;
    Token doublePeek;     // love it

    public Parser(String inFile) throws ScannerException {
        try {
            sc = new Scanner(inFile);
        } catch (ScannerException e) {throw e;}
    }

    public DeclarationList parse() throws CompException{
        getToken();
        DeclarationList node = declarationList();
        expect(Token.T_EOF, "end of file");
        return node;
    }

    // DECLARATIONS!!!
    
    public DeclarationList declarationList() throws CompException {
        // special case for error clarity
        expect((token.kind != Token.T_EOF), "a program");
        // normal stuff
        int line = token.line;
        Declaration head;
        Declaration dn;
        head = declaration();
        dn = head;
        while (token.kind != Token.T_EOF) {
            dn.next = declaration();
            dn = dn.next;
        }
        return new DeclarationList(line, head);
    }

    public Declaration declaration() throws CompException {
        int line = token.line;
        if (doublePeek().kind == Token.T_LPAREN)
            return new Declaration(line, functionDeclaration());
        else
            return new Declaration(line, variableDeclaration());
    }
    
    public VariableDeclaration variableDeclaration() throws CompException {
        int line = token.line;
        expect(token.isParamType(), "non-void variable type");
        TypeSpecifier ts = typeSpecifier();
        if (token.kind == Token.T_ASTR) {
            Token ptr = token; getToken();
            expect(Token.T_IDEN, "identifier for pointer param");
            Token name = token; getToken();
            expect(Token.T_SEMICOL, "semicolon after declaration");
            getToken();
            return new VariableDeclaration(line, ts, ptr, name);
        }
        expect(Token.T_IDEN, "identifier for parameter");
        Token name = token; getToken();
        if (token.kind == Token.T_LBRACK) {
            expect(Token.T_LBRACK, "["); getToken();
            expect(Token.T_NUM, "literal integer array size");
            int size = Integer.parseInt(token.value); getToken();
            expect(Token.T_RBRACK, "]"); getToken();
            expect(Token.T_SEMICOL, "semicolon after declaration");
            getToken();
            return new VariableDeclaration(line, ts, name, size);
        }
        else {
            expect(Token.T_SEMICOL, "semicolon after declaration");
            getToken();
            return new VariableDeclaration(line, ts, name);
        }
    }
    
    public TypeSpecifier typeSpecifier() throws CompException {
        expect(token.isTypeSpec(), "type specifier");
        Token type = token;
        getToken();
        return new TypeSpecifier(type.line, type);
    }

    public FunctionDeclaration functionDeclaration() throws CompException {
        int line = token.line;
        TypeSpecifier ts = typeSpecifier();
        Token name = token; getToken();
        expect(Token.T_LPAREN, "( to open function params"); getToken();
        Parameters params = parameters();
        expect(Token.T_RPAREN, ") to open function params"); getToken();
        CompoundStatement cs = compoundStatement();
        return new FunctionDeclaration(line, ts, name, params, cs);
    }

    public Parameters parameters() throws CompException {
        int line = token.line;
        if (token.kind == Token.T_VOID) {
            getToken();
            return new Parameters(line);
        }
        else
            return new Parameters(line, parameterList());
    }

    public ParameterList parameterList() throws CompException {
        int line = token.line;
        Parameter head;
        Parameter param;
        head = parameter();
        param = head;
        while (token.kind != Token.T_RPAREN) {
            expect(Token.T_COMMA, ","); getToken();
            param.next = parameter();
            param = param.next;
        }
        return new ParameterList(line, head);
    }

    public Parameter parameter() throws CompException {
        int line = token.line;
        expect(token.isParamType(), "non-void param type");
        TypeSpecifier ts = typeSpecifier();
        if (token.kind == Token.T_ASTR) {
            Token ptr = token; getToken();
            expect(Token.T_IDEN, "identifier for pointer param");
            Token name = token; getToken();
            return new Parameter(line, ts, ptr, name);
        }
        expect(Token.T_IDEN, "identifier for parameter");
        Token name = token; getToken();
        if (token.kind == Token.T_LBRACK) {
            expect(Token.T_LBRACK, "["); getToken();
            expect(Token.T_RBRACK, "]"); getToken();
            boolean array = true;
            return new Parameter(line, ts, name, array);
        }
        else return new Parameter(line, ts, name);
    }

    // STATEMENTS!!!

    public Statement statement() throws CompException{
        Statement st;
        if (token.kind == Token.T_LBRACE) {
            return new Statement(token.line, compoundStatement());
        }
        if (token.kind == Token.T_IF) {
            return new Statement(token.line, ifStatement());
        }
        if (token.kind == Token.T_WHILE) {
            return new Statement(token.line, whileStatement());
        }
        if (token.kind == Token.T_RETURN) {
            return new Statement(token.line, returnStatement());
        }
        if (token.kind == Token.T_WRITE ||
                token.kind == Token.T_WRITELN) {
            return new Statement(token.line, writeStatement());
        }
        return new Statement(token.line, expressionStatement());
    }

    public CompoundStatement compoundStatement() throws CompException {
        expect(Token.T_LBRACE, "left brace"); getToken();
        LocalDeclarations ld = localDeclarations();
        CompoundStatement cs = new CompoundStatement(token.line, ld, statementList());
        expect(Token.T_RBRACE, "right brace"); getToken();
        return cs;
    }

    public LocalDeclarations localDeclarations() throws CompException {
        if (!token.isParamType()) {
            return null;
        }
        int line = token.line;
        VariableDeclaration head;
        VariableDeclaration vd;
        head = variableDeclaration();
        vd = head;
        while (token.isParamType()) {
            vd.next = variableDeclaration();
            vd = vd.next;
        }
        return new LocalDeclarations(line, head);
    }

    public StatementList statementList() throws CompException {
        if (token.kind == Token.T_RBRACE) {
            return null;
        }
        int line = token.line;
        Statement head;
        Statement sn;
        head = statement();
        sn = head;
        while (token.kind != Token.T_RBRACE) {
            sn.next = statement();
            sn = sn.next;
        }
        return new StatementList(line, head);
    }

    public ExpressionStatement expressionStatement() throws CompException{
        ExpressionStatement es = new ExpressionStatement(token.line, expression());
        expect(Token.T_SEMICOL, "semicolon");
        getToken();
        return es;
    }

    public IfStatement ifStatement() throws CompException {
        Token ifToken = token;
        expect(Token.T_IF, "if"); getToken();
        expect(Token.T_LPAREN, "( for if statement"); getToken();
        Expression ex = expression();
        expect(Token.T_RPAREN, ") for if statement"); getToken();
        Statement st = statement();
        if (token.kind == Token.T_ELSE) {
            getToken();
            return new IfStatement(ifToken.line, ex, st, statement());
        }
        else
            return new IfStatement(ifToken.line, ex, st);
    }

    public WhileStatement whileStatement() throws CompException {
        int line = token.line;
        expect(Token.T_WHILE, "\"while\"");
        getToken();
        expect(Token.T_LPAREN, "(");
        getToken();
        Expression ex = expression();
        expect(Token.T_RPAREN, ")");
        getToken();
        Statement st = statement();
        return new WhileStatement(line, ex, st);
    }

    public ReturnStatement returnStatement() throws CompException {
        Token ret = token;
        expect(Token.T_RETURN, "return token"); getToken();
        if (token.kind == Token.T_SEMICOL) {
            getToken();
            return new ReturnStatement(ret.line);
        }
        else {
            Expression ex = expression();
            expect(Token.T_SEMICOL, "semicolon to end return"); getToken();
            return new ReturnStatement(ret.line, ex);
        }
    }

    public WriteStatement writeStatement() throws CompException {
        Token wr = token;
        if(token.kind == Token.T_WRITELN) {
            getToken();
            expect(Token.T_LPAREN, "("); getToken();
            expect(Token.T_RPAREN, ")"); getToken();
            expect(Token.T_SEMICOL, ";"); getToken();
            return new WriteStatement(wr.line);
        }
        else {
            expect(Token.T_WRITE, "write token"); getToken();
            expect(Token.T_LPAREN, "("); getToken();
            Expression ex = expression();
            expect(Token.T_RPAREN, ")"); getToken();
            expect(Token.T_SEMICOL, ";"); getToken();
            return new WriteStatement(wr.line, ex);
        }
    }

    // EXPRESSIONS!!!

    public Expression expression() throws CompException{
        int line = token.line;
        CompoundExpression ce = compoundExpression();
        if(token.kind == Token.T_ASSN) {
            // awful ambiguity hack: fix???
            if (ce.e1.t.f.ptrOp != null &&
                    ce.e1.t.f.ptrOp.kind == Token.T_ASTR) {
                ce.e1.t.f.fact.var.astr = ce.e1.t.f.ptrOp;
                ce.e1.t.f.ptrOp = null;
            }
            expect(ce.isLNode(), "mutable reference");
            getToken();
            // this is weird, maybe I want to just save the ce
            return new Expression(line, ce.e1.t.f.fact.var, expression());
        }
        else return new Expression(line, ce);
    }

    public Variable variable() throws CompException {
        if (token.kind == Token.T_ASTR) {
            Token astr = token; getToken();
            expect(Token.T_IDEN, "id for pointer");
            return new Variable(astr.line, astr, token);
        }
        else if (peekToken().kind == Token.T_LBRACK) {
            expect(Token.T_IDEN, "id for array ref");
            Token name = token; getToken();
            expect(Token.T_LBRACK, "lbrack for array ref"); getToken();
            Expression ex = expression();
            expect(Token.T_RBRACK, "rbrack for array ref"); getToken();
            return new Variable(name.line, name, ex);
        }
        expect(Token.T_IDEN, "identifier for var ref");
        Variable v = new Variable(token.line, token);
        getToken();
        return v;
    }

    public CompoundExpression compoundExpression() throws CompException {
        int line = token.line;
        ENode e1 = eNode();
        if (token.isRelOp()) {
            return new CompoundExpression(line, e1, relOp(), eNode());
        }
        else {
            return new CompoundExpression(line, e1);
        }
    }

    public RelOp relOp() throws CompException {
        expect(token.isRelOp(), "rel op");
        RelOp ro = new RelOp(token.line, token);
        getToken();
        return ro;
    }

    public ENode eNode() throws CompException {
        ENode root = new ENode(token.line, tNode());
        while (token.isAddOp()) {
            ENode newroot = new ENode(token.line, root, addOp(), tNode());
            root = newroot;
        }
        return root;
    }

    public AddOp addOp() throws CompException {
        expect(token.isAddOp(), "add op");
        AddOp ao = new AddOp(token.line, token);
        getToken();
        return ao;
    }

    public TNode tNode() throws CompException {
        TNode root = new TNode(token.line, fNode());
        while (token.isMulOp()) {
            TNode newroot = new TNode(token.line, root, mulOp(), fNode());
            root = newroot;
        }
        return root;
    }

    public MulOp mulOp() throws CompException {
        expect(token.isMulOp(), "mulop");
        MulOp mo = new MulOp(token.line, token);
        getToken();
        return mo;
    }

    public FNode fNode() throws CompException {
        if (token.isPointerOp()) {
            Token ptr = token;
            getToken();
            return new FNode(ptr.line, ptr, factor());
        }
        if (token.kind == Token.T_MINUS) {
            int line = token.line;
            getToken();
            return new FNode(line, fNode());
        }
        return new FNode(token.line, factor());
    }

    public Factor factor() throws CompException {
        int line = token.line;
        if (token.kind == Token.T_LPAREN) {
            getToken();
            Factor fact = new Factor(line, expression());
            expect(Token.T_RPAREN, ")");
            getToken();
            return fact;
        }
        else if (token.kind == Token.T_READ) {
            Token read = token;
            getToken(); expect(Token.T_LPAREN, "(");
            getToken(); expect(Token.T_RPAREN, ")");
            getToken();
            return new Factor(read.line, read);
        }
        else if (peekToken().kind == Token.T_LPAREN) {
            return new Factor(token.line, functionCall());
        }
        else if (token.isLit()) {
            return new Factor(token.line, literal());
        }
        else {
            return new Factor(line, variable());
        }
    }

    public FunctionCall functionCall() throws CompException {
        expect(Token.T_IDEN, "identifier");
        Token name = token; getToken();
        expect(Token.T_LPAREN, "arg list open paren"); getToken();
        FunctionCall fc = new FunctionCall(name.line, name, arguments());
        expect(Token.T_RPAREN, "arg list close paren"); getToken();
        return fc;
    } 

    public Arguments arguments() throws CompException {
        if (token.kind == Token.T_RPAREN)
            return new Arguments(token.line);
        return new Arguments(token.line, argumentList());
    }

    public ArgumentList argumentList() throws CompException {
        int line = token.line;
        Expression head;
        Expression en;
        head = expression();
        en = head;
        while (token.kind != Token.T_RPAREN) {
            expect(Token.T_COMMA, ","); getToken();
            en.next = expression();
            en = en.next;
        }
        return new ArgumentList(line, head);
    }

    public Literal literal() throws CompException {
        expect(token.isLit(), "literal");
        Literal lit = new Literal(token.line, token);
        getToken();
        return lit;
    }

    // HELPER FUNCTIONS!!!

    void getToken() throws ScannerException {
        if (peekToken != null) {
            token = peekToken;
            peekToken = doublePeek;
            doublePeek = null;
            return;
        }
        try {
            sc.getNextToken();
            token = sc.nextToken;
        } catch (ScannerException e) {throw e;}
    }

    // returns the token after the current one without advancing
    Token peekToken() throws ScannerException {
        if (peekToken != null) return peekToken;
        sc.getNextToken();
        peekToken = sc.nextToken;
        return peekToken;
    }

    Token doublePeek() throws ScannerException {
        if (doublePeek != null) return doublePeek;
        else if (peekToken != null) {
            sc.getNextToken();
            doublePeek = sc.nextToken;
        }
        else {
            sc.getNextToken();
            peekToken = sc.nextToken;
            sc.getNextToken();
            doublePeek = sc.nextToken;
        }
        return doublePeek;
    }

    void expect( int tokenKind, String thingExpected ) throws ParserException {
        if (tokenKind != token.kind) {
            throw new ParserException("Found " + token.value + " expected "
                    + thingExpected + ".", token.line);
        }
    }

    void expect (boolean expectedTrue, String thingExpected) throws ParserException {
        if (!expectedTrue) {
            throw new ParserException("Found " + token.value + " expected " + thingExpected + ".", token.line);
        }
    }

}
