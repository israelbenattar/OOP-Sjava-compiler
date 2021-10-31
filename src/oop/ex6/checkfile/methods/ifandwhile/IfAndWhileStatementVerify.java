package oop.ex6.checkfile.methods.ifandwhile;

import oop.ex6.checkfile.methods.TypeParamMatch;
import oop.ex6.checkfile.methods.exceptions.*;

import java.util.HashMap;
import java.util.regex.Pattern;


/**
 * this class represent IfAndWhileStatementVerify object that check IF statement is if or WHILE
 */
public class IfAndWhileStatementVerify {

    // pattern for the IF or WHILE statement
    private final Pattern ifAndWhileFullPattern;

    //the matcher type
    private TypeParamMatch typeMatcher;


    //-----------------------------     constructor     -----------------------------//


    /**
     * the constructor compile the patterns
     */
    public IfAndWhileStatementVerify() {

        String ifAndWhileFullStr = "^\\s*(if|while)\\s*\\(\\s*((true|false|-?\\d+(\\.\\d*)?|(_\\w|[a-zA-Z]" +
                ")\\w*)\\s*((\\|\\||&&)\\s*(true|false|-?\\d+(\\.\\d*)?|(_\\w|[a-zA-Z])\\w*)\\s*)*)\\" +
                "s*\\)\\s*\\{\\s*$";
        this.ifAndWhileFullPattern = Pattern.compile(ifAndWhileFullStr);

        this.typeMatcher = new TypeParamMatch();
    }


    //-----------------------------     method     -----------------------------//


    /**
     * this method make the actual verify and throws exception if its not valid statement
     *
     * @param lineIndex        the index of the line
     * @param ifWhileStatement string of the line
     * @param variables        hashMap of all the global variable
     * @throws MethodException if the statement is not valid
     */
    public void ifAndWhileVerify(int lineIndex, String ifWhileStatement, HashMap<String, Object[]> variables)
            throws MethodException {
        if (!ifAndWhileFullPattern.matcher(ifWhileStatement).matches()) {
            throw new UnValidParameterException(lineIndex);
        } else {
            String[] statementArr = ifWhileStatement.split("\\s*(\\(|\\)|(\\|\\|)|(&&)|\\{)\\s*");
            for (int paramIndex = 1; paramIndex < statementArr.length; paramIndex++) {
                String param = statementArr[paramIndex];
                if (!typeMatcher.paramIsType("boolean", param, variables)) {
                    throw new WrongConditionTypeException(lineIndex, param);
                }
            }
        }
    }
}
