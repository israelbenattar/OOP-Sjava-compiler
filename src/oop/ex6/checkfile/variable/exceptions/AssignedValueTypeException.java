package oop.ex6.checkfile.variable.exceptions;

/**
 * this class represent variable that Have illegal type Exception
 */
public class AssignedValueTypeException extends VariableException {

    public AssignedValueTypeException() {
        System.err.println("The type of the assigned value is incorrect.");
    }
}
