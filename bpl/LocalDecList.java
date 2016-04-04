package bpl;
import bpl.nodes.*;
public class LocalDecList {

    public LocalDecList next;
    public DeclarationNode dec;

    public LocalDecList (DeclarationNode dec, LocalDecList next) {
        this.dec = dec;
        this.next = next;
    }

}
