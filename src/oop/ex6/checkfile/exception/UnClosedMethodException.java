package oop.ex6.checkfile.exception;

/**
 * this class represent UnClosed Method Exception
 */
public class UnClosedMethodException extends CheckFileException {

    public UnClosedMethodException(int lineNum) {
        System.err.println("Error in line " + (lineNum + 1) + " : The method that start in this line is unclosed.");
    }
}
