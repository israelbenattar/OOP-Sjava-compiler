package oop.ex6.checkfile.variable.exceptions;

/**
 * this class represent variable that not declared Exception
 */
public class VarNotDeclaredException extends VariableException {

    public VarNotDeclaredException(String var) {
        System.err.println("Error in line : the variable " + var + " was not declared.");
    }
}
