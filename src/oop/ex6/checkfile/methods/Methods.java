package oop.ex6.checkfile.methods;

import oop.ex6.checkfile.MethodWrapper;
import oop.ex6.checkfile.methods.exceptions.MethodException;
import oop.ex6.checkfile.methods.exceptions.UnValidLineException;
import oop.ex6.checkfile.methods.ifandwhile.IfAndWhile;
import oop.ex6.checkfile.variable.VariableVerifier;
import oop.ex6.checkfile.variable.exceptions.VariableException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;


/**
 * this class represent the method object that implement the HashMapFacade
 */
public class Methods implements HashMapFacade {

    //the pattern for the closing block
    private final Pattern closingBlockPattern;

    //the pattern for the variable declaration
    private final Pattern variableDeclarationPattern;

    //the pattern for the variable assignment
    private final Pattern variableAssPattern;

    //the pattern for the empty comment line
    private final Pattern commentEmptyLinePattern;

    //the pattern for the empty line
    private final Pattern emptyLinePattern;

    //the pattern for the if and while
    private final Pattern ifAndWhilePattern;

    //the pattern for the method call
    private final Pattern methodCallPattern;

    //one
    private final int ONE = 1;

    //the global variable
    private HashMap<String, Object[]> globalVariable;

    //the methods names
    private ArrayList<MethodWrapper> methodsNames;

    //the local method variable
    private HashMap<String, Object[]> methodVariables;

    //the VariableVerifier
    private VariableVerifier variableVerifier;

    //the MethodCallVerify
    private MethodCallVerify methodCallVerify;

    //the IfAndWhile
    private IfAndWhile ifWhileBlock;


    //-----------------------------     constructor     -----------------------------//


    /**
     * this constructor get the globals variable the methods name and the variableVerifier
     *
     * @param globalVariable   the globals variable
     * @param methodsNames     methods name
     * @param variableVerifier variableVerifier
     */
    public Methods(HashMap<String, Object[]> globalVariable, ArrayList<MethodWrapper> methodsNames,
                   VariableVerifier variableVerifier) {

        this.globalVariable = globalVariable;

        this.methodsNames = methodsNames;

        this.variableVerifier = variableVerifier;

        this.methodCallVerify = new MethodCallVerify(methodsNames);

        //===================== compile all the patterns =================//

        final String variableAssStrPattern = "^\\s*(_\\w|[a-zA-z])\\w*\\s*=\\s*";
        this.variableAssPattern = Pattern.compile(variableAssStrPattern);

        final String ifAndWhileStrPattern = "^\\s*(if|while)\\s*\\(.*\\)\\s*\\{\\s*$";
        this.ifAndWhilePattern = Pattern.compile(ifAndWhileStrPattern);

        final String variableDeclarationStrPattern = "^\\s*(final +)?(char|int|String|boolean|double)";
        this.variableDeclarationPattern = Pattern.compile(variableDeclarationStrPattern);

        final String commentEmptyReturnLine = "^(//.*|\\s*|\\s*return\\s*;)\\s*$";
        this.commentEmptyLinePattern = Pattern.compile(commentEmptyReturnLine);

        final String emptyLine = "\\s*";
        this.emptyLinePattern = Pattern.compile(emptyLine);

        final String closingBlockStrPattern = "\\s*}\\s*";
        this.closingBlockPattern = Pattern.compile(closingBlockStrPattern);

        final String methodCallStrPattern = "^\\s*[a-zA-Z]\\w*\\s*\\( *((-?\\d(.\\d)*|\'.?\'|\".*\"|(_\\w|" +
                "[a-zA-Z]\\w*)\\w*)\\s*(,\\s*(-?\\d(.\\d)*|\'.?\'|\".*\"|(_\\w|\\w[^_])\\w*))*)*\\s*" +
                "\\)\\s*;$";
        this.methodCallPattern = Pattern.compile(methodCallStrPattern);
    }


    //-----------------------------     methods     -----------------------------//


    /**
     * this method get method block and run un all the line and make sure it valid
     * every line he make sure its valid and call to the relevant method
     *
     * @param firstLineIndex the first line index
     * @param method         the array list with all the line of the method
     * @param methodParam    linkedHashMap with all the parameters of the method
     * @throws VariableException if the variable is not valid
     * @throws MethodException   if the method is not valid
     */
    public void methodsVerifier(int firstLineIndex, ArrayList<String> method, LinkedHashMap<String, Object[]>
            methodParam) throws VariableException, MethodException {

        this.methodVariables = (HashMap<String, Object[]>) methodParam;
        this.ifWhileBlock = new
                IfAndWhile(firstLineIndex, method, variableVerifier, methodsNames,
                new VarHashMapWrapper(this));

        int methodSize = method.size();
        int inMethodLineIndex = ONE;

        while (inMethodLineIndex < methodSize) {
            // string of the line
            String lineStr = method.get(inMethodLineIndex);

            if (commentEmptyLinePattern.matcher(lineStr).matches() ||
                    emptyLinePattern.matcher(lineStr).matches()) {
                inMethodLineIndex++;

            } else if (variableAssPattern.matcher(lineStr).lookingAt()) {
                variableVerifier.variableValid(lineStr, new VarHashMapWrapper(this), methodVariables);
                inMethodLineIndex++;

            } else if (variableDeclarationPattern.matcher(lineStr).lookingAt()) {
                variableVerifier.variableValid(lineStr, new VarHashMapWrapper(this), methodVariables);
                inMethodLineIndex++;

            } else if (ifAndWhilePattern.matcher(lineStr).lookingAt()) {
                inMethodLineIndex = ifWhileBlock.ifWhileLoopVerifier(inMethodLineIndex);

            } else if (methodCallPattern.matcher(lineStr).lookingAt()) {
                methodCallVerify.validMethod((inMethodLineIndex + firstLineIndex),
                        lineStr, new VarHashMapWrapper(this));
                inMethodLineIndex++;

            } else if (closingBlockPattern.matcher(lineStr).matches()) {
                resetMethodVarLst();
                return;

            } else {
                throw new UnValidLineException(firstLineIndex + inMethodLineIndex);
            }
        }
    }


    /**
     * this method reset the variable list
     */
    private void resetMethodVarLst() {
        this.methodVariables = new HashMap<>();
    }


    //see in HashMapFacade
    @Override
    public boolean containsKey(String key) {
        if (methodVariables.containsKey(key)) {
            return true;
        }
        return globalVariable.containsKey(key);
    }


    //see in HashMapFacade
    @Override
    public Object[] get(String key) {
        Object[] value = methodVariables.get(key);
        if (value != null) {
            return value;
        }
        return globalVariable.get(key);
    }


    //see in HashMapFacade
    @Override
    public Object[] put(String key, Object[] value) {
        return methodVariables.put(key, value);
    }
}
