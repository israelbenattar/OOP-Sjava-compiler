package oop.ex6.main.exception;

public class NotSJavaFileException extends Exception{
    public NotSJavaFileException() {
        System.err.println("Warning: Running on a non s-java file.");
    }
}
