/* Scanner class */
/* objective-bob */
/* bob# */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

class Scanner {

    // store the latest token
    public Token nextToken;

    // lines are 1-indexed
    private int currentLine;
    private String line = "";
    private int cursor = 0;
    private String buffer = "";
    private boolean fileDone = false;

    FileReader fr;
    BufferedReader br;

    public Scanner(String inputFileName) throws ScannerException{
        // instantiate
        try{
            fr = new FileReader(inputFileName);
            br = new BufferedReader(fr);
        } catch(IOException ex) {throw new ScannerException("Can't open file.");}
        currentLine = 1;
        try{
            line = br.readLine();
        } catch (IOException e) {throw new ScannerException("Line read failed.");}
    }

    public void getNextToken() throws ScannerException {
        if (fileDone) return;
        while (cursor >= line.length() || Character.isWhitespace(line.charAt(cursor))) {
            if (cursor >= line.length()) {
                currentLine++;
                cursor = 0;
                try{
                    line = br.readLine();
                } catch (IOException e) {throw new ScannerException("Line read failed.");}
                if (line == null) {
                    endOfFile();
                    return;
                }
            }
            else {cursor++;}
        }
        if (Character.isAlphabetic(line.charAt(cursor))) identifier();
        else if (Character.isDigit(line.charAt(cursor))) number();
        else { special(); }
    }

    private void endOfFile() throws ScannerException {
        try{
            br.close();
        } catch (IOException e) {throw new ScannerException("File close failed.");}
        fileDone = true;
        nextToken = new Token(Token.T_EOF, "EOF", currentLine);
    }

    private void identifier() {
        do {
            buffer += line.charAt(cursor);
            cursor++;
        } while (cursor < line.length() && (Character.isAlphabetic(line.charAt(cursor)) ||
                Character.isDigit(line.charAt(cursor)) || line.charAt(cursor) == '_'));
        nextToken = new Token(Token.identKind(buffer), buffer, currentLine);
        buffer = "";
    }

    private void number() {
        do {
            buffer += line.charAt(cursor);
            cursor++;
        } while (cursor < line.length() && Character.isDigit(line.charAt(cursor)));
        nextToken = new Token(Token.T_NUM, buffer, currentLine);
        buffer = "";
    }

    private void special() throws ScannerException{
        int tok;
        int startLine = currentLine;
        switch(line.charAt(cursor)) {
            case ';':
                buffer += line.charAt(cursor); tok = Token.T_SEMICOL;
                break;
            case ',':
                buffer += line.charAt(cursor); tok = Token.T_COMMA;
                break;
            case '[':
                buffer += line.charAt(cursor); tok = Token.T_LBRACK;
                break;
            case ']':
                buffer += line.charAt(cursor); tok = Token.T_RBRACK;
                break;
            case '{':
                buffer += line.charAt(cursor); tok = Token.T_LBRACE;
                break;
            case '}':
                buffer += line.charAt(cursor); tok = Token.T_RBRACE;
                break;
            case '(':
                buffer += line.charAt(cursor); tok = Token.T_LPAREN;
                break;
            case ')':
                buffer += line.charAt(cursor); tok = Token.T_RPAREN;
                break;
            case '<':
                buffer += line.charAt(cursor);
                if ( cursor + 1 < line.length() && line.charAt(cursor + 1) == '=') {
                    cursor++; buffer += line.charAt(cursor); tok = Token.T_LESSEQ; }
                else { tok = Token.T_LESS; }
                break;
            case '=':
                buffer += line.charAt(cursor);
                if ( cursor + 1 < line.length() && line.charAt(cursor + 1) == '=') {
                    cursor++; buffer += line.charAt(cursor); tok = Token.T_EQ; }
                else { tok = Token.T_ASSN; }
                break;
            case '!':
                if ( cursor + 1 < line.length() && line.charAt(cursor + 1) == '=') {
                    buffer += line.charAt(cursor);
                    cursor++; buffer += line.charAt(cursor); tok = Token.T_NEQ; }
                else {
                    throw new ScannerException("Symbol ! not recognized or not valid here.", startLine);
                }
                break;
            case '>':
                buffer += line.charAt(cursor);
                if ( cursor + 1 < line.length() && line.charAt(cursor + 1) == '=') {
                    cursor++; buffer += line.charAt(cursor); tok = Token.T_GRTREQ; }
                else { tok = Token.T_GRTR; }
                break;
            case '+':
                buffer += "+"; tok = Token.T_PLUS;
                break;
            case '-':
                buffer += "-"; tok = Token.T_MINUS;
                break;
            case '*':
                buffer += "*"; tok = Token.T_ASTR;
                break;
            case '/':
                if ( cursor + 1 < line.length() && line.charAt(cursor + 1) == '*') {
                    cursor += 2;
                    while (cursor + 1 >= line.length() ||
                            line.charAt(cursor) != '*' || line.charAt(cursor+1) != '/') {
                        if (cursor + 1 >= line.length()) {
                            currentLine++;
                            try{
                                line = br.readLine();
                            } catch (IOException e) {throw new ScannerException("Line read failed.");}
                            if (line == null) {
                                // I guess this could also be considered a scanner error?
                                endOfFile(); return;
                            }
                            cursor = 0;
                        } else {cursor++;}
                    }
                    cursor += 2;
                    getNextToken();
                    return;
                }
                else {
                    buffer += line.charAt(cursor);
                    tok = Token.T_DIV;
                }
                break;
            case '%':
                buffer += "%"; tok = Token.T_MOD;
                break;
            case '&':
                buffer += "&"; tok = Token.T_AMPR;
                break;
            case '"':
                cursor++;
                while (cursor < line.length() && line.charAt(cursor) != '"') {
                    buffer += line.charAt(cursor);
                    cursor++;
                }
                if (cursor >= line.length()) {
                    throw new ScannerException("Unterminated string literal.", startLine);
                } else {
                    tok = Token.T_STRLIT;
                }
                break;
            default:
                throw new ScannerException("Symbol " + line.charAt(cursor) + " not recognized or not " +
                    "valid here.", currentLine);
        }
        nextToken = new Token(tok, buffer, startLine);
        buffer = "";
        cursor++;
    }

}
