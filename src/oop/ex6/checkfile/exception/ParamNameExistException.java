package oop.ex6.checkfile.exception;

/**
 * this class represent parameters that already exist Exception
 */
public class ParamNameExistException extends CheckFileException {

    public ParamNameExistException(String paramName, int lineNum) {
        System.err.println("error in line " + (lineNum + 1) + " : the param name " + paramName + " already exist.");
    }
}
