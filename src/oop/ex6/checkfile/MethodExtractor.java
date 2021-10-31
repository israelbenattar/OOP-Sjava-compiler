package oop.ex6.checkfile;

import oop.ex6.checkfile.exception.*;
import oop.ex6.checkfile.variable.exceptions.MissingReturnStatementException;

import java.util.*;


/**
 * this class is represent the MethodExtractor
 */
public class MethodExtractor {

    //zero
    private final int ZERO = 0;

    //one
    private final int ONE = 1;

    //two
    private final int TWO = 2;

    //tree
    private final int TREE = 3;

    //all the illegal name for variable
    private static final String[] savedNames = new String[]{"int", "String", "boolean", "double",
            "char", "void", "final", "if", "while", "true", "false", "return"};


    //-----------------------------     methods     -----------------------------//


    /**
     * this method extract the method from the sJavaFile
     *
     * @param startingLineIndex the starting line index
     * @param sJavaFile         the sJavaFile
     * @param methodsNames      arrayList with all names if exist method
     * @param methods           arrayList of all the methods
     * @return the closing line index
     * @throws CheckFileException if there is illegal line
     */
    public int extractMethod(int startingLineIndex, List<String> sJavaFile, ArrayList<String> methodsNames,
                             ArrayList<MethodWrapper> methods) throws CheckFileException {

        ArrayList<String> methodLines = new ArrayList<>();
        int closingLine = methodLineCounting(startingLineIndex, sJavaFile, methodLines);

        LinkedHashMap<String,  Object[]> methodParam = new LinkedHashMap<>();
        String methodName = extractMethodName(sJavaFile.get(startingLineIndex), startingLineIndex,
                methodsNames, methodParam);

        if (closingLine == ZERO) {
            throw new UnClosedMethodException(startingLineIndex);

        } else if (!((String) methodLines.get(methodLines.size() - TWO)).matches
                ("^\\s*return\\s*;\\s*$")) {

            throw new MissingReturnStatementException(closingLine - ONE);

        } else {
            MethodWrapper method = new MethodWrapper(startingLineIndex, methodName, methodParam,
                    methodLines);

            methods.add(method);
            return closingLine;
        }
    }


    /**
     * this method counting the line numbers of lines and return Object[] of the lines of the methods
     *
     * @param startingLineIndex the starting line index
     * @param sJavaFile         list with the sJavaFile lines
     * @return Object[] of the lines of the methods
     */
    private int methodLineCounting(int startingLineIndex, List<String> sJavaFile,
                                   ArrayList<String> methodArr) {
        int closingLine = startingLineIndex;
        int openParenthesis = ONE;

        for (int i = startingLineIndex; i < sJavaFile.size(); i++) {
            String line = sJavaFile.get(i);

            if (line.matches("^\\s*(if|while)\\s*\\(.*\\)\\s*\\{\\s*$")) {
                openParenthesis++;

            } else if (line.matches("\\s*}\\s*")) {
                openParenthesis--;
            }
            closingLine++;
            methodArr.add(line);

            if (openParenthesis == ZERO) {
                return closingLine ;
            }
        }
        return ZERO;
    }


    /**
     * this method get method call and extract the method name
     *
     * @param methodCall      tha line of the method call
     * @param methodLineIndex the method line index
     * @param methodsNames    array list with all the methods names
     * @return Object[] with the name and parameters
     * @throws CheckFileException if the call is illegal
     */
    private String extractMethodName(String methodCall, int methodLineIndex, ArrayList<String>
            methodsNames, LinkedHashMap<String, Object[]> methodParam) throws CheckFileException {

        String[] methodCallArr = methodCall.split("\\s*(void |\\(|\\)|,|\\{)\\s*");
        String methodName = methodCallArr[ONE];
        LinkedList<String> paramsNames = new LinkedList<>();

        if (methodsNames.contains(methodName)) {
            throw new MethodAlreadyExistException(methodName, methodLineIndex);

        } else if (checkForSavedName(methodName)) {
            throw new SavedNameException(methodName, methodLineIndex);
        }
        for (int i = TWO; i < methodCallArr.length; i++) {
            methodParamValidate(methodLineIndex, methodCallArr[i], methodParam, paramsNames);
        }
        methodsNames.add(methodName);
        return methodName;
    }


    /**
     * this method checked if the parameters in method are legal
     *
     * @param lineIndex   the index of the line
     * @param param       the string of the parameter
     * @param parameters  linkedList of the parameters
     * @param paramsNames linkedList of the parameters names
     * @throws CheckFileException if the file is illegal
     */
    private void methodParamValidate(int lineIndex, String param, LinkedHashMap<String, Object[]> parameters,
                                     LinkedList<String> paramsNames) throws CheckFileException {

        String[] paramArr = param.split(" +");
        String paramName = paramArr[paramArr.length - ONE];
        boolean isFinal = (paramArr.length == TREE);

        if (checkForSavedName(paramName)) {
            throw new SavedNameException(paramName, lineIndex);

        } else if (paramsNames.contains(paramName)) {
            throw new ParamNameExistException(paramName, lineIndex);
        }

        paramsNames.add(paramName);
        parameters.put(paramName, new Object[]{isFinal, true, paramArr[paramArr.length - TWO]});
    }


    /**
     * this method checked if the name is already exist
     *
     * @param string the name that the method checked
     * @return true if is exist already, false otherwise
     */
    private boolean checkForSavedName(String string) {

        for (String name : savedNames) {
            if (name.equals(string)) {
                return true;
            }
        }
        return false;
    }
}
