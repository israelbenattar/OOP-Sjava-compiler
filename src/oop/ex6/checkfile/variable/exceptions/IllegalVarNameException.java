package oop.ex6.checkfile.variable.exceptions;

/**
 * this class represent variable that Have not legal name Exception
 */
public class IllegalVarNameException extends VariableException {

    public IllegalVarNameException(String varName) {
        System.err.println("The name " + varName + " is an reserved keywords in s-Java.");
    }
}
