package oop.ex6.checkfile.variable.exceptions;

/**
 * this class represent variable that have no assignment Exception
 */
public class InValidVarAssignException extends VariableException {

    public InValidVarAssignException() {
        System.err.println("Invalid variable Assignment line.");
    }
}
