package oop.ex6.checkfile.variable.exceptions;

/**
 * this class represent variable that not exist Exception
 */
public class VarDoNotExcException extends VariableException {

    public VarDoNotExcException(String varName) {
        System.err.println("Variable " + varName + " was not assigned.");
    }
}
