package oop.ex6.checkfile;

import oop.ex6.checkfile.exception.*;
import oop.ex6.checkfile.methods.exceptions.MethodException;
import oop.ex6.checkfile.variable.VariableVerifier;
import oop.ex6.checkfile.methods.Methods;
import oop.ex6.checkfile.variable.exceptions.VariableException;

import java.util.*;
import java.util.regex.Pattern;


/**
 * this class is represent the check file and is run on the file and call relevant
 * classes
 */
public class CheckFile {

    // tha pattern for the comment line
    private final Pattern commentLinePattern;

    // tha pattern for the method
    private final Pattern methodPattern;

    // tha pattern for the variable assignment
    private final Pattern variableAssPattern;

    // tha pattern for the empty line
    private final Pattern emptyLinePattern;

    // tha pattern for the variable
    private final Pattern variablePattern;

    //zero
    private final int ZERO = 0;

    //the MethodExtractor object
    private MethodExtractor methodExtractor;

    //the VariableVerifier object
    private VariableVerifier variableVerifier;

    //the methods object
    private Methods methodVerifier;

    //hashMap of the variable
    private HashMap<String, Object[]> variables;

    //arrayList of the methods names
    private ArrayList<String> methodsNames;

    //arrayList of all the methods
    private ArrayList<MethodWrapper> methods;


    //-----------------------------     constructor     -----------------------------//


    /**
     * the constructor of the CheckFile
     */
    public CheckFile() {

        this.variables = new HashMap<String, Object[]>();

        this.methodsNames = new ArrayList<String>();

        this.methods = new ArrayList<MethodWrapper>();

        this.variableVerifier = new VariableVerifier();

        this.methodExtractor = new MethodExtractor();

        //===================== compile all the patterns =================//

        String commentLine = "^//.*$";
        this.commentLinePattern = Pattern.compile(commentLine);

        String emptyLine = "^\\s*$";
        this.emptyLinePattern = Pattern.compile(emptyLine);

        String fullMethodStrPattern = "^\\s*void\\s+([a-zA-Z]\\w*)\\s*(\\( *\\)|\\(\\s*((final\\s+)?" +
                "(char|int|String|boolean|double)\\s+((_\\w|[a-z])\\w*))(\\s*,\\s*(final\\s+)?(char|" +
                "int|String|boolean|double)\\s+((_\\w|[a-z])\\w*))*\\s*\\))\\s*\\{\\s*$";
        this.methodPattern = Pattern.compile(fullMethodStrPattern);

        String variableStrPattern = "^\\s*((final\\s+)?(char|int|String|boolean|double)|" +
                "(_\\w|\\w)\\w*\\s*=\\s*)";
        this.variablePattern = Pattern.compile(variableStrPattern);

        String variableAssStrPattern = "^\\s*(_\\w|\\w)\\w*\\s*=\\s*";
        this.variableAssPattern = Pattern.compile(variableAssStrPattern);
    }


    //-----------------------------     methods     -----------------------------//


    /**
     * this method get the file in array list of the sJavaFile and run un all
     * the line and make sure it valid,
     * every line he make sure its valid and call to the relevant class
     *
     * @param sJavaFile the sJavaFile in arrayList
     */
    public void checkValid(List<String> sJavaFile) {
        int line = ZERO;
        try {
            while (line < sJavaFile.size()) {
                String lineStr = sJavaFile.get((line));

                if (variablePattern.matcher(lineStr).lookingAt() ||
                        variableAssPattern.matcher(lineStr).lookingAt()) {
                    variableVerifier.variableValid(lineStr, variables, variables);
                    line++;

                } else if (methodPattern.matcher(lineStr).lookingAt()) {
                    line = methodExtractor.extractMethod(line, sJavaFile, methodsNames, methods);

                } else if (commentLinePattern.matcher(lineStr).matches() ||
                        emptyLinePattern.matcher(lineStr).matches()) {
                    line++;

                } else {
                    throw new InvalidLineException(line);
                }
            }
            verifyMethods();
        } catch (VariableException | CheckFileException | MethodException e) {
            System.out.println("1");
            return;
        }
        System.out.println("0");
    }


    /**
     * this method verify methods
     *
     * @throws VariableException if the variable is illegal
     * @throws MethodException if the method is illegal
     */
    private void verifyMethods() throws VariableException, MethodException {
        this.methodVerifier = new Methods(variables, methods, variableVerifier);
        for (MethodWrapper method : methods) {
            methodVerifier.methodsVerifier(method.getStartingLineIndex(), method.getArray(),
                    method.getParam());
        }
    }
}