package oop.ex6.checkfile.methods;

import oop.ex6.checkfile.MethodWrapper;
import oop.ex6.checkfile.methods.exceptions.MethodException;
import oop.ex6.checkfile.methods.exceptions.UnExistMethodException;
import oop.ex6.checkfile.methods.exceptions.UnValidParameterException;

import java.util.*;


/**
 * this class represent the MethodCallVerify this class have method that get the method line call
 * and return true if the method call is valid and false otherwise
 */
public class MethodCallVerify {

    //the type TypeParamMatch that get type and param and match between them
    private TypeParamMatch typeMatcher;

    //the parameters in linkedHashMap
    private LinkedHashMap<String, Object[]> parameters;

    //array list with all the methods
    private ArrayList<MethodWrapper> methodWrapper;

    //the global variables
    private HashMap<String, Object[]> globalVariables;

    //arrayList with all the types that the method should get by order
    private ArrayList<String> methodTypes;

    //zero the index of first value in array
    final private int FIRST = 0;

    //zero
    private final int ZERO = 0;

    //one
    private final int ONE = 1;

    //two
    private final int TWO = 2;


    //-----------------------------     constructor     -----------------------------//


    /**
     * this constructor get arrayList of wrapper methods, that give us access to all the methods
     *
     * @param methods the arrayList of methods
     */
    public MethodCallVerify(ArrayList<MethodWrapper> methods) {

        this.methodWrapper = methods;

        this.typeMatcher = new TypeParamMatch();
    }


    //-----------------------------     methods     -----------------------------//


    /**
     * @param lineIndex            the line number of the call to the method
     * @param methodLine      the all line of the call
     * @param globalVariables the global variables
     * @throws UnExistMethodException exception that the call us not valid
     */
    public void validMethod(int lineIndex, String methodLine, HashMap<String, Object[]> globalVariables)
            throws MethodException {
        //first we split the line

        TreeMap<String, ArrayList<String>> methodNameParam = extractNameAndParam(methodLine);

        String methodName = methodNameParam.firstKey();
        ArrayList<String> param = methodNameParam.get(methodName);

        //making sure that the method is exist
        if (existMethod(methodName)) {
            this.methodTypes = getParamFromWrapper(methodName, globalVariables);

            //making sure that the param are valid
            if (!validParamMethod(methodTypes, param)){
                throw new UnValidParameterException(lineIndex);
            }
            return;

        }
        //if one of the condition is not true throw exception
        throw new UnExistMethodException(methodName, lineIndex);
    }


    /**
     * this method get name of method and return the all types of the method should get in
     * arrayList by order
     *
     * @param methodName       the name of the method
     * @param hashMapVariables the global variables
     * @return array list with all the types of the method should get in arrayList by order
     */
    private ArrayList<String> getParamFromWrapper(String methodName, HashMap<String, Object[]>
            hashMapVariables) {

        this.globalVariables = hashMapVariables;
        ArrayList<String> param = new ArrayList<>();

        //extract the types from the wrapper
        for (MethodWrapper methodWrap : methodWrapper) {
            if (methodWrap.getName().equals(methodName)) {
                this.parameters = methodWrap.getParam();
            }
        }
        if (this.parameters != null)
            //add the types to array list
            parameters.forEach((name, array) -> {
                param.add((String) array[TWO]);
            });
        return param;
    }


    /**
     * this method get method name and return true if the method exist and false otherwise
     *
     * @param methodName the name of the method
     * @return true if the method exist and false otherwise
     */
    private boolean existMethod(String methodName) {
        for (MethodWrapper methodWrap : methodWrapper) {
            if (methodWrap.getName().equals(methodName)) {
                return true;
            }
        }
        return false;
    }


    /**
     * this method extract the name and the parameters from line that call method
     *
     * @param methodLine the method line
     * @return TreeMap<String, ArrayList < String>> that the string is the name and the arrayList
     * is the parameters by orders
     */
    private TreeMap<String, ArrayList<String>> extractNameAndParam(String methodLine) {
        String[] words = methodLine.split("\\s*,\\s*|\\s*\\(\\s*|\\s*\\)\\s*|\\s*;\\s*");
        String methodName = words[FIRST].stripLeading();
        ArrayList<String> param = new ArrayList<>();
        TreeMap<String, ArrayList<String>> method = new TreeMap<>();
        int length = words.length;
        int index = ONE;

        while (index < length) {
            param.add(words[index]);
            index++;
        }

        method.put(methodName, param);

        return method;
    }


    /**
     * this method get list of the type and list of the parameters and return true if the
     * parameters are valid and false otherwise
     *
     * @param typeList  the list of the types
     * @param paramList the list of the parameters
     * @return true if the parameters are valid and false otherwise
     */
    private Boolean validParamMethod(ArrayList<String> typeList, ArrayList<String> paramList) {
        if (typeList.size() != paramList.size()) {
            return false;
        }
        int size = paramList.size();
        int index = ZERO;
        while (index < size) {
            if (!typeMatcher.paramIsType(typeList.get(index), paramList.get(index), globalVariables)){
                return false;
            }
            index++;
        }
        return true;
    }
}

