package oop.ex6.checkfile.variable;

import oop.ex6.checkfile.variable.exceptions.*;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * this class represent variable object that inherit to all the type of variable
 */
public class Variable {

    //zero
    private final int ZERO = 0;

    //one
    private final int ONE = 1;

    //two
    private final int TWO = 2;

    //all the illegal name for variable
    private final String[] unValidNames = new String[]{"int", "String", "boolean", "double",
            "char", "void", "final", "if", "while", "true", "false", "return"};


    //-----------------------------     methods     -----------------------------//


    /**
     * this method check the validity of the variable and call to the relevant method
     *
     * @param dividedLine  thr line divided in arrayList
     * @param globalVarLst the global variable list
     * @param localVarLst  the local variable list
     * @param type         the type of the variable
     * @throws VariableException if the variable is not valid
     */
    public void checkValid(ArrayList<String> dividedLine, HashMap<String, Object[]> globalVarLst,
                           HashMap<String, Object[]> localVarLst, String type)
            throws VariableException {

        boolean isFinal = false;
        boolean assigningValue = false;
        for (int i = ZERO; i < dividedLine.size(); i++) {

            String part = dividedLine.get(i);
            String variableName = "((_\\w|[a-z])\\w*)";

            if (part.equals("final") && i == ZERO) {
                isFinal = true;

            } else if ((part.equals(type) && isFinal && i == ONE) ||
                    (part.equals(type) && !isFinal && i == ZERO)) {
                continue;

            } else if (part.matches(variableName)) {

                if (!legalVarName(part)) {
                    throw new IllegalVarNameException(part);
                }

                if (localVarLst.containsKey(part)) {
                    throw new ContainsKayException(part);
                }

                if (i + ONE < dividedLine.size() && dividedLine.get(i + ONE).matches("=")) {
                    if (typeMatching(dividedLine.get(i + TWO), globalVarLst, type)) {
                        throw new AssignedValueTypeException();

                    } else {
                        assigningValue = true;
                        i += TWO;
                    }
                }

                if (isFinal && !assigningValue) {
                    throw new FinalVarNotAssignedException(part);
                }

                Object[] varValues = new Object[]{isFinal, assigningValue, type};
                localVarLst.put(part, varValues);
                assigningValue = false;
            }
        }
    }


    /**
     * this method get name and check if its illegal name or not
     *
     * @param name the name that the method check
     * @return true if its legal, false otherwise
     */
    private boolean legalVarName(String name) {
        for (String illegalName : unValidNames) {
            if (name.equals(illegalName)) {
                return false;
            }
        }
        return true;
    }


    /**
     * this method is type matching he get type and value and say if the value is type of the type
     * that the method get
     *
     * @param varValue        the value of the variable
     * @param varLst          the local variable list
     * @param assignedVarType the type
     * @return true if the value is type of the type that the method get, false otherwise
     * @throws VariableException if the type matching is false
     */
    public boolean typeMatching(String varValue, HashMap<String, Object[]> varLst, String assignedVarType)
            throws VariableException {

        String[] possibleValueTypes = getPossibleValueType(varValue, varLst);

        for (String possibleType : possibleValueTypes) {
            if (possibleType.equals(assignedVarType)) {
                return false;
            }
        }
        return true;
    }


    /**
     * this method get variable value and the global variable list and return list with all the possible
     * types that the variable can be
     *
     * @param varValue     the variable value
     * @param globalVarLst the global variable list
     * @return list with all the possible types that the variable can be
     * @throws VariableException if the variable is not valid
     */
    private String[] getPossibleValueType(String varValue, HashMap<String, Object[]> globalVarLst)
            throws VariableException {

        if (varValue.matches("^-?\\d+$")) {
            return new String[]{"int", "double", "boolean"};

        } else if (varValue.matches("\".*\"")) {
            return new String[]{"String"};

        } else if (varValue.matches("^true|false$")) {
            return new String[]{"boolean"};

        } else if (varValue.matches("^-?\\d+.\\d+$")) {
            return new String[]{"boolean", "double"};

        } else if (varValue.matches("^\'.?\'")) {
            return new String[]{"char"};

        } else if (varValue.matches("^((_\\w|[a-z])\\w*)$")) {
            //if its a call for variable
            if (globalVarLst.containsKey(varValue)) {

                //if its exist
                if ((boolean) globalVarLst.get(varValue)[1]) {
                    return possibleType((String) globalVarLst.get(varValue)[2], globalVarLst);
                }
                throw new VarNotDeclaredException(varValue);

            } else {
                return new String[]{};
            }
        } else {
            return new String[]{};
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
    private String[] possibleType(String type, HashMap<String, Object[]> varLst) throws VariableException {
        switch (type) {
            case "String":
                return getPossibleValueType("\"a\"", varLst);
            case "char":
                return getPossibleValueType("\'a\'", varLst);
            case "boolean":
                return getPossibleValueType("true", varLst);
            case "double":
                return getPossibleValueType("1.0", varLst);
            default:
                return getPossibleValueType("1", varLst);
        }
    }
}
