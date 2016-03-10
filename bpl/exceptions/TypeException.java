package bpl.exceptions;
public class TypeException extends CompException {

    private String message;
    private int lineNum;

    public TypeException(String message, int lineNum) {
        this.message = message;
        this.lineNum = lineNum;
    }

    public TypeException(String message) {
        this.message = message;
        this.lineNum = -1;
    }

    public String toString() {
        if (lineNum < 0) return "Type exception: " + message;
        return "Type exception: " + message + " (Line " + lineNum + ")";
    }
}
