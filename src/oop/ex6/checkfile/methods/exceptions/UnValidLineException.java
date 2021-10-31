package oop.ex6.checkfile.methods.exceptions;

/**
 * this class represent unValid line Exception
 */
public class UnValidLineException extends MethodException {

    public UnValidLineException(int lineNum) {
        System.err.println("Error in line " + (lineNum + 1) + " : the line is invalid S-java line.");
    }
}
