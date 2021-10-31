package oop.ex6.checkfile.variable.exceptions;

/**
 * this class represent final variable that assignment twice Exception
 */
public class FinalVarException extends VariableException {

    public FinalVarException(String varName) {
        System.err.println("The variable " + varName + " is final and therefore cannot be assigned again.");
    }
}
