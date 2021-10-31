package oop.ex6.checkfile.exception;

/**
 * this class represent invalid line Exception
 */
public class InvalidLineException extends CheckFileException {

    public InvalidLineException(int lineNum) {
        System.err.println("Error in line " + (lineNum + 1) + " : invalid sJava line.");
    }
}
