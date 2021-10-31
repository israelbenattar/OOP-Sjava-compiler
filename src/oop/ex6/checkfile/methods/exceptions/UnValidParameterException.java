package oop.ex6.checkfile.methods.exceptions;

/**
 * this class represent invalid parameters Exception
 */
public class UnValidParameterException extends MethodException {

    public UnValidParameterException(int lineNum) {
        System.err.println("Error in line " + (lineNum + 1) + " : inValid condition.");
    }
}
