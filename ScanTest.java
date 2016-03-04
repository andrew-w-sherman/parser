public class ScanTest {
    public static void main(String[] args){
        String inputFileName;
        Scanner myScanner;

        if (args.length < 1) {System.out.println("Please pass a file name."); return;}
        inputFileName = args[0];
        try {
            myScanner = new Scanner(inputFileName);
        } catch (ScannerException e) {
            System.out.println(e);
            return;
        }
        System.out.println(myScanner.nextToken);
        while (myScanner.nextToken.kind != Token.T_EOF) {
            try {
                myScanner.getNextToken();
                System.out.println(myScanner.nextToken);
            } catch (ScannerException e) {
                System.out.println(e);
                return;
            }
        }
    }
}
