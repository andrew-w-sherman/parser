class ScannerException extends CompException {
    private String message;
    private int lineNum;

    public ScannerException(String message, int lineNum) {
        this.message = message;
        this.lineNum = lineNum;
    }

    public ScannerException(String message) {
        this.message = message;
        this.lineNum = -1;
    }

    public String toString() {
        if (this.lineNum < 0) return "Scanner exception: " + message;
        return "Scanner exception: " + message + " (Line " + lineNum + ")";
    }
}
