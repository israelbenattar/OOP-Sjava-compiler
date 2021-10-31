package oop.ex6.checkfile.variable;

import oop.ex6.checkfile.variable.exceptions.AssignedValueTypeException;
import oop.ex6.checkfile.variable.exceptions.VariableException;
import oop.ex6.checkfile.variable.exceptions.FinalVarException;
import oop.ex6.checkfile.variable.exceptions.VarDoNotExcException;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * this class represent VariableAssignment that extends the variable
 */
public class VariableAssignment extends Variable {

    //zero
    private final int ZERO = 0;

    //one
    private final int ONE = 1;

    //two
    private final int TWO = 2;


    /**
     * this method check the validity of assignment
     *
     * @param dividedLine the divided line in arrayList
     * @param varLst      the variable list
     * @throws VariableException if the variable is not valid
     */
    void checkValid(ArrayList<String> dividedLine, HashMap<String, Object[]> varLst)
            throws VariableException {
        String varName = dividedLine.get(ZERO);
        String varValue = dividedLine.get(TWO);

        if (!varLst.containsKey(varName)) {
            throw new VarDoNotExcException(dividedLine.get(ZERO));

        } else if ((boolean) varLst.get(varName)[ZERO]) {
            throw new FinalVarException(varName);

        } else if (super.typeMatching(varValue, varLst, (String) varLst.get(varName)[TWO])) {
            throw new AssignedValueTypeException();

        } else {
            Object[] varValues = varLst.get(varName);
            varLst.put(varName, new Object[]{varValues[0], true, varValues[2]});
        }
    }
}
