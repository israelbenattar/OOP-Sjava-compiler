package oop.ex6.checkfile.methods.ifandwhile;

import oop.ex6.checkfile.methods.HashMapFacade;
import oop.ex6.checkfile.MethodWrapper;
import oop.ex6.checkfile.methods.VarHashMapWrapper;
import oop.ex6.checkfile.methods.MethodCallVerify;
import oop.ex6.checkfile.methods.exceptions.MethodException;
import oop.ex6.checkfile.methods.exceptions.UnClosedLoopException;
import oop.ex6.checkfile.methods.exceptions.UnValidLineException;
import oop.ex6.checkfile.variable.VariableVerifier;
import oop.ex6.checkfile.variable.exceptions.VariableException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Pattern;

/**
 * this class represent the ifAndWhile object that implement the HashMapFacade
 */
public class IfAndWhile implements HashMapFacade {

    //the pattern for if or while
    private final Pattern ifAndWhileFullPattern;

    //the pattern for variable declaration
    private final Pattern variableDeclarationPattern;

    //the pattern for variable assignment
    private final Pattern variableAssPattern;

    //the pattern for empty return comment
    private final Pattern commentEmptyReturnLinePattern;

    //the pattern for methodLines call
    private final Pattern methodCallPattern;

    //the pattern for closing block
    private final Pattern closingBlockPattern;

    //one
    private final int ONE = 1;

    //the IfAndWhileStatementVerify
    private IfAndWhileStatementVerify ifAndWhileStatementVerify;

    //the list of all the methods
    private ArrayList<MethodWrapper> sJavaMethods;

    //the methodLines in array
    private ArrayList<String> methodLines;

    //all the global variables in hashMap
    private HashMap<String, Object[]> globalVariable;

    //all the loop variables in hashMap
    private HashMap<String, Object[]> loopVariables;

    //the index of the first line of the block
    private int methodFirstLineIndex;

    //the variableVerifier
    private VariableVerifier variableVerifier;

    //the MethodCallVerify
    private MethodCallVerify methodCallVerify;



    //------------------------------      contractor      ------------------------------//


    /**
     * \
     * contractor for the if and while object
     *
     * @param methodFirstLineIndex the index that the block start with
     * @param methodLines          array list with all the line in the methodLines
     * @param variableVerifier     the variableVerifier
     * @param methods              arrayList with all the names of the methods in the class
     * @param globalVariable       hashMap with all the global variable
     */
    public IfAndWhile(int methodFirstLineIndex, ArrayList<String> methodLines, VariableVerifier variableVerifier,
                      ArrayList<MethodWrapper> methods, HashMap<String, Object[]> globalVariable) {

        this.methodLines = methodLines;

        this.sJavaMethods = methods;

        this.loopVariables = new HashMap<>();

        this.variableVerifier = variableVerifier;

        this.globalVariable = globalVariable;

        this.methodFirstLineIndex = methodFirstLineIndex;

        this.ifAndWhileStatementVerify = new IfAndWhileStatementVerify();

        this.methodCallVerify = new MethodCallVerify(methods);

        //===================== compile all the patterns =================//

        final String methodCallStrPattern = "^\\s*[a-zA-Z]\\w*\\s*\\( *((-?\\d(.\\d)*|\'.?\'|\".*\"|(_\\w|" +
                "[a-zA-Z]\\w*)\\w*)\\s*(,\\s*(-?\\d(.\\d)*|\'.?\'|\".*\"|(_\\w|\\w[^_])\\w*))*)*\\s*" +
                "\\)\\s*;$";
        this.methodCallPattern = Pattern.compile(methodCallStrPattern);

        final String variableAssStrPattern = "^\\s(_\\w|\\w)\\w*\\s*=\\s*";
        this.variableAssPattern = Pattern.compile(variableAssStrPattern);

        final String variableDeclarationStrPattern = "^\\s*(final\\s+)?(char|int|String|boolean|double)";
        this.variableDeclarationPattern = Pattern.compile(variableDeclarationStrPattern);

        final String commentEmptyReturnLine = "(^//.*|\\s*(return\\s*;)?)$";
        this.commentEmptyReturnLinePattern = Pattern.compile(commentEmptyReturnLine);

        final String closingBlockStrPattern = "\\s*}\\s*";
        this.closingBlockPattern = Pattern.compile(closingBlockStrPattern);

        final String ifAndWhileFullStr = "^\\s*(if|while)\\s*\\(\\s*((true|false|-?\\d+(\\.\\d*)?|(_\\w|" +
                "[a-zA-Z])\\w*)\\s*((\\|\\||&&)\\s*(true|false|-?\\d+(\\.\\d*)?|(_\\w|[a-zA-Z])\\w*)" +
                "\\s*)*)\\s*\\)\\s*\\{\\s*$";
        this.ifAndWhileFullPattern = Pattern.compile(ifAndWhileFullStr);
    }


    //------------------------------      methods      ------------------------------//


    /**
     * this method get IF or WHILE block and run un all the line and make sure it valid
     * every line he make sure its valid and call to the relevant method
     *
     * @param firstLineIndex the index of the first line
     * @return int the index of the line
     * @throws VariableException if there is not valid variable
     * @throws MethodException if the method is not valid
     */
    public int ifWhileLoopVerifier(int firstLineIndex)
            throws VariableException, MethodException {


        ifAndWhileStatementVerify.ifAndWhileVerify(methodFirstLineIndex + firstLineIndex,
                methodLines.get(firstLineIndex), new VarHashMapWrapper(this));

        int currentMethodLine = firstLineIndex + ONE;

        while (currentMethodLine < methodLines.size()) {
            String line = methodLines.get(currentMethodLine);

            if (commentEmptyReturnLinePattern.matcher(line).lookingAt()) {
                currentMethodLine++;

            } else if (variableAssPattern.matcher(line).lookingAt()) {
                variableVerifier.variableValid(line, new VarHashMapWrapper(this),
                        loopVariables);
                currentMethodLine++;

            } else if (variableDeclarationPattern.matcher(line).lookingAt()) {
                variableVerifier.variableValid(line, new VarHashMapWrapper(this),
                        loopVariables);
                currentMethodLine++;

            } else if (ifAndWhileFullPattern.matcher(line).lookingAt()) {
                IfAndWhile ifAndWhileLoop = new IfAndWhile(methodFirstLineIndex, methodLines, variableVerifier,
                        sJavaMethods, new VarHashMapWrapper(this));
                currentMethodLine = ifAndWhileLoop.ifWhileLoopVerifier(currentMethodLine);

            } else if (methodCallPattern.matcher(line).lookingAt()) {
                methodCallVerify.validMethod((currentMethodLine + methodFirstLineIndex)
                        , line, new VarHashMapWrapper((this)));
                currentMethodLine++;

            } else if (closingBlockPattern.matcher(line).matches()) {
                currentMethodLine++;
                resetBlockVariables();
                return currentMethodLine;

            } else {
                throw new UnValidLineException(methodFirstLineIndex + currentMethodLine);
            }
        }
        throw new UnClosedLoopException(methodFirstLineIndex + currentMethodLine);
    }


    /**
     * this method reset the block variable hashMap
     */
    private void resetBlockVariables() {
        this.loopVariables = new HashMap<>();
    }


    //see in HashMapFacade
    @Override
    public boolean containsKey(String key) {
        boolean loopContainKey = loopVariables.containsKey(key);
        if (loopContainKey) {
            return true;
        }
        return globalVariable.containsKey(key);
    }


    //see in HashMapFacade
    @Override
    public Object[] get(String key) {
        Object[] value = loopVariables.get(key);
        if (value != null) {
            return value;
        }
        return globalVariable.get(key);
    }


    //see in HashMapFacade
    @Override
    public Object[] put(String key, Object[] value) {
        return loopVariables.put(key, value);
    }
}
