package oop.ex6.checkfile.exception;

/**
 * this class represent saved name Exception
 */
public class SavedNameException extends CheckFileException {

    public SavedNameException(String methodName, int lineNum) {
        System.err.println("error in line " + (lineNum + 1) + " : the name " + methodName + " is a saved name.");
    }
}
