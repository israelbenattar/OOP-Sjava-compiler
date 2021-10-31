package oop.ex6.checkfile.methods.exceptions;

/**
 * this class represent wrong condition type Exception
 */
public class WrongConditionTypeException extends MethodException {

    public WrongConditionTypeException(int lineNum, String param) {
        System.err.println("Error in line " + (lineNum + 1) + " : the param " + param + " is not boolean.");
    }
}
