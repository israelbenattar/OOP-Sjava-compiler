package oop.ex6.checkfile.methods.exceptions;

/**
 * this class represent un closed loop Exception
 */
public class UnClosedLoopException extends MethodException {

    public UnClosedLoopException(int lineNum) {
        System.err.println("Error in line " + (lineNum + 1) + " : the loop is unclosed.");
    }
}
