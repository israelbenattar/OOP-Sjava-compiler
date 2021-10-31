package oop.ex6.checkfile.variable.exceptions;

/**
 * this class represent final variable that have no assignment Exception
 */
public class FinalVarNotAssignedException extends VariableException {

    public FinalVarNotAssignedException(String var) {
        System.err.println("The final variable " + var + " was not assigned at declaration.");
    }
}
