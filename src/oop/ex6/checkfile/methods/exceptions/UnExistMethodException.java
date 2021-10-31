package oop.ex6.checkfile.methods.exceptions;

/**
 * this class represent un exist method Exception
 */
public class UnExistMethodException extends MethodException {

    public UnExistMethodException(String methodName, int lineNum) {
        System.err.println("error in line " + (lineNum + 1) + " : The method " + methodName + " don't exist.");
    }
}
