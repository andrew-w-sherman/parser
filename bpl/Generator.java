package bpl;
import bpl.nodes.*;
import bpl.exceptions.*;

public class Generator {

    DeclarationList root;

    public Generator (String outFileName) throws GenException {
        try {
            // create the buffer
        } catch (IOException e) {
            throw new GenException("Couldn't create output file.");
        }
    }
}
