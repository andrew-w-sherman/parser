package bpl.exceptions;
public class GenException extends CompException {

    private String message;
    private int lineNum;

    public GenException(String message, int lineNum) {
        this.message = message;
        this.lineNum = lineNum;
    }

    public GenException(String message) {
        this.message = message;
        this.lineNum = -1;
    }

    public String toString() {
        if (lineNum < 0) return "Generator exception: " + message;
        return "Type exception: " + message + " (Line " + lineNum + ")";
    }
}
