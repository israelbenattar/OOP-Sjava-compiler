package oop.ex6.checkfile.variable.exceptions;

/**
 * this class represent variable that already assigned Exception
 */
public class ContainsKayException extends VariableException {

    public ContainsKayException(String var) {
        System.err.println("The variable " + var + " was already assigned.");
    }
}
