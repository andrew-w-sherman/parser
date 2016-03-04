public class ParserException extends CompException {

    private String message;
    private int lineNum;

    public ParserException(String message, int lineNum) {
        this.message = message;
        this.lineNum = lineNum;
    }

    public ParserException(String message) {
        this.message = message;
        this.lineNum = -1;
    }

    public String toString() {
        if (lineNum < 0) return "Parser exception: " + message;
        return "Parser exception: " + message + " (Line " + lineNum + ")";
    }
}
