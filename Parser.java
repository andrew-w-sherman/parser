public class Parser{

    Scanner sc;
    Token token;
    Token peekToken;

    public Parser(String inFile) throws ScannerException {
        try {
            sc = new Scanner(inFile);
        } catch (ScannerException e) {throw e;}
    }

    public Statement parse() throws CompException{
        getToken();
        Statement node = statement();
        getToken();
        expect(Token.T_EOF, "end of file");
        return node;
    }

    public Statement statement() throws CompException{
        Statement st;
        if (token.kind == Token.T_LBRACE) {
            return new Statement(token.line, compoundStatement());
        }
        if (token.kind == Token.T_WHILE) {
            return new Statement(token.line, whileStatement());
        }
        return new Statement(token.line, expressionStatement());
    }

    public CompoundStatement compoundStatement() throws CompException {
        expect(Token.T_LBRACE, "left brace");
        getToken();
        CompoundStatement cs = new CompoundStatement(token.line, statementList());
        expect(Token.T_RBRACE, "right brace");
        getToken();
        return cs;
    }

    public StatementList statementList() throws CompException {
        if (token.kind == Token.T_RBRACE) {
            return null;
        }
        int line = token.line;
        Statement head;
        TreeNode sn;
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

    public Expression expression() throws CompException{
        return new Expression(token.line, compoundExpression());
    }

    public CompoundExpression compoundExpression() throws CompException {
        int line = token.line;
        CompoundExpression ce;
        if (peekToken().isRelOp()) {
            ce = new CompoundExpression(line, eNode(), relOp(), eNode());
        }
        else {
            ce = new CompoundExpression(line, eNode());
        }
        return ce;
    }

    public RelOp relOp() throws CompException {
        if (!token.isRelOp()) throw new ParserException("Expected relop, found "+token.value+".", token.line);
        RelOp ro = new RelOp(token.line, token);
        getToken();
        return ro;
    }

    public ENode eNode() throws CompException {
        return new ENode(token.line, factor());
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
        else {
            return new Factor(line, variable());
        }
    }

    public Variable variable() throws CompException {
        expect(Token.T_IDEN, "identifier");
        Variable v = new Variable(token.line, token.value);
        getToken();
        return v;
    }

    void getToken() throws ScannerException {
        if (peekToken != null) {
            token = peekToken;
            peekToken = null;
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
        try {
            sc.getNextToken();
            peekToken = sc.nextToken;
        } catch (ScannerException e) {throw e;}
        return peekToken;
    }

    void expect( int tokenKind, String thingExpected ) throws ParserException {
        if (tokenKind != token.kind) {
            throw new ParserException("Found " + token.value + " expected "
                    + thingExpected + ".", token.line);
        }
    }
}
