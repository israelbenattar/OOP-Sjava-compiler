package oop.ex6.checkfile.methods;

import java.util.HashMap;

/**
 * this class represent TypeParamMatch that have method that get type and param and check
 * if the type of param is equal to the type that he get
 */
public class TypeParamMatch {

    //boolean type
    private final String BOOLEAN = "boolean";

    //int type
    private final String INT = "int";

    //string type
    private final String STRING = "String";

    //char type
    private final String CHAR = "char";

    //double type
    private final String DOUBLE = "double";

    //one
    private final int ONE = 1;

    //two
    private final int TWO = 2;


    /**
     * this method get type and param and check if the type of param is equal to the type that he get
     *
     * @param type      the type
     * @param param     the parameter
     * @param variables the hashMap of variables
     * @return true if the type of param is equal to the type that he get, false otherwise
     */
    public boolean paramIsType(String type, String param, HashMap<String, Object[]> variables) {

        if (param.matches("^-?\\d+$")) {
            return (type.equals(INT) || type.equals(DOUBLE) || type.equals(BOOLEAN));

        } else if (param.matches("^\".*\"$")) {
            return (type.equals(STRING));

        } else if (param.matches("^true|false$")) {
            return (type.equals(BOOLEAN));

        } else if (param.matches("^-?\\d+.\\d+$")) {
            return (type.equals(BOOLEAN) || type.equals(DOUBLE));

        } else if (param.matches("^\'.?\'$")) {
            return (type.equals(CHAR));

        } else if (param.matches("^((_\\w|[a-z])\\w*)$")) {
            //call for variable

            if (variables.containsKey(param)) {
                //making sure that the variable is exist
                Object[] objects = variables.get(param);

                if (objects[ONE].equals(true)) {
                    //making sure that the variable is assign
                    return possibleType((String) objects[TWO], variables);
                }
            }
            return false;
        } else {
            return false;
        }
    }


    /**
     * this method get type and the variables and return boolean value. true if the type is
     * possible, false otherwise
     *
     * @param type   the type
     * @param varLst the variables hashMAP
     * @return true if the type is possible, false otherwise
     */
    private boolean possibleType(String type, HashMap<String, Object[]> varLst) {
        if (type.equals(STRING)) {
            return paramIsType("boolean", "\"a\"", varLst);
        } else if (type.equals(CHAR)) {
            return paramIsType("boolean", "\'a\'", varLst);
        } else {
            return paramIsType("boolean", "0", varLst);
        }
    }
}
