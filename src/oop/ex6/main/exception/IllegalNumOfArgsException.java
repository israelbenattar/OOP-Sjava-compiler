package oop.ex6.main.exception;

public class IllegalNumOfArgsException extends Exception {
    public IllegalNumOfArgsException() {
        System.err.println("teh program received an illegal number of arguments.");
    }
}
