package oop.ex6.checkfile.exception;

/**
 * this class represent method that already exist Exception
 */
public class MethodAlreadyExistException extends CheckFileException {

    public MethodAlreadyExistException(String methodName, int lineNum) {
        System.err.println("error in line " + (lineNum + 1) + " : a method named " + methodName + " already exist.");
    }
}
