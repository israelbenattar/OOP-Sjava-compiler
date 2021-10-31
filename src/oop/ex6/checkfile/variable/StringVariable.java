package oop.ex6.checkfile.variable;

import oop.ex6.checkfile.variable.exceptions.VariableException;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * this class represent String variable that extends the variable
 */
public class StringVariable extends Variable {

    //the string type
    private final  String MY_TYPE = "String";


    /**
     * this method check the validity of String type
     *
     * @param dividedLine  the divided line in arrayList
     * @param globalVarLst the global variable list
     * @param localVarLst  the local variable list
     * @throws VariableException if the variable is not valid
     */
    public void checkValid(ArrayList<String> dividedLine, HashMap<String, Object[]> globalVarLst,
                           HashMap<String, Object[]> localVarLst) throws VariableException {
        super.checkValid(dividedLine, globalVarLst, localVarLst, MY_TYPE);
    }
}
