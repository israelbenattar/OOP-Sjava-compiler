package oop.ex6.checkfile;

import java.util.ArrayList;
import java.util.LinkedHashMap;


/**
 * this class represent MethodWrapper that extends ArrayList<String>, he get all the detail of method anf
 * return tha all details in this wrapper
 */
public class MethodWrapper extends ArrayList<String> {

    //the starting line index
    private int startingLineIndex;

    //the method name
    private String methodName;

    //linkedList with all the parameters of the method
    private LinkedHashMap<String, Object[]> methodParam;

    //arrayList with all the methods lines
    private ArrayList<String> methodArray;


    //-----------------------------     constructor     -----------------------------//


    /**
     * the constructor of the MethodWrapper
     *
     * @param startingLineIndex the starting line index
     * @param methodName        the name of the method
     * @param methodParam       linkedList with all the parameters
     * @param methodArray       arrayList with all the method lines
     */
    public MethodWrapper(int startingLineIndex, String methodName, LinkedHashMap<String, Object[]>
            methodParam, ArrayList<String> methodArray) {

        this.startingLineIndex = startingLineIndex;

        this.methodName = methodName;

        this.methodParam = methodParam;

        this.methodArray = methodArray;
    }


    //-----------------------------     methods     -----------------------------//


    /**
     * this method return arrayList with all the method lines
     *
     * @return arrayList with all the method lines
     */
    public ArrayList<String> getArray() {
        return this.methodArray;
    }


    /**
     * this method return the starting line index
     *
     * @return the starting line index
     */
    public int getStartingLineIndex() {
        return this.startingLineIndex;
    }


    /**
     * this method return the linkedList with all the parameters
     *
     * @return linkedList with all the parameters
     */
    public LinkedHashMap<String, Object[]> getParam() {
        return this.methodParam;
    }


    /**
     * this method return the the name of the method
     *
     * @return the name of the method
     */
    public String getName() {
        return methodName;
    }
}
