package oop.ex6.checkfile.variable.exceptions;

import oop.ex6.checkfile.exception.CheckFileException;

/**
 * this class represent a missing return statement Exception
 */
public class MissingReturnStatementException extends CheckFileException {

    public MissingReturnStatementException(int line) {
        System.err.println("Error in line " + line + " : missing return statement.");
    }
}
