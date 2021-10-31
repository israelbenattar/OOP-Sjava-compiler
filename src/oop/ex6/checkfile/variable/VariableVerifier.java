package oop.ex6.checkfile.variable;

import oop.ex6.checkfile.variable.exceptions.VariableException;
import oop.ex6.checkfile.variable.exceptions.InValidVarAssignException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.regex.Pattern;

public class VariableVerifier {

    //the int pattern
    private Pattern intPattern;

    //the double pattern
    private Pattern doublePattern;

    //the String pattern
    private Pattern stringPattern;

    //the char pattern
    private Pattern charPattern;

    //the boolean pattern
    private Pattern booleanPattern;

    //the full variable assignment pattern
    private Pattern fullVariableAssPattern;

    //the int variable object
    private intVariable intVar;

    //the double variable object
    private doubleVariable doubleVar;

    //the boolean variable object
    private BooleanVariable booleanVar;

    //the String variable object
    private StringVariable StringVar;

    //the char variable object
    private CharVariable charVar;

    //the variable assignment object
    private VariableAssignment varAssign;

    //zero
    private final int ZERO = 0;


    //-----------------------------     constructor     -----------------------------//


    /**
     * the constructor of the VariableVerifier
     */
    public VariableVerifier() {

        this.intVar = new intVariable();

        this.doubleVar = new doubleVariable();

        this.booleanVar = new BooleanVariable();

        this.StringVar = new StringVariable();

        this.charVar = new CharVariable();

        this.varAssign = new VariableAssignment();

        //===================== compile all the patterns =================//

        String intStrPattern = "^\\s*(final\\s+)?int\\s+((_\\w|[a-zA-Z])\\w*(\\s*=\\s*(-?\\d+|(_\\w|" +
                "[a-zA-z])\\w*))?\\s*,\\s*)*(_\\w|[a-zA-Z])\\w*(\\s*=\\s*(-?\\d+|(_\\w|[a-zA-Z])\\w*))" +
                "?\\s*;\\s*$";
        this.intPattern = Pattern.compile(intStrPattern);

        String doubleStrPattern = "^\\s*(final\\s+)?double\\s+((_\\w|[a-zA-Z])\\w*(\\s*=\\s*(-?\\d+(\\" +
                ".\\d+)?|(_\\w|[a-z])\\w*))?\\s*,\\s*)*(_\\w|[a-zA-Z])\\w*(\\s*=\\s*(-?\\d+(\\.\\d+)?|" +
                "(_\\w|[a-zA-Z])\\w*))?\\s*;\\s*$";
        this.doublePattern = Pattern.compile(doubleStrPattern);

        String stringStrPattern = "^\\s*(final\\s+)?String\\s+((_\\w|[a-zA-Z])\\w*(\\s*=\\s*(\".*\"|(_\\w" +
                "|[a-zA-Z])\\w*))?\\s*,\\s*)*(_\\w|[a-zA-Z])\\w*(\\s*=\\s*(\".*\"|(_\\w|[a-zA-Z])\\w*))?" +
                "\\s*;\\s*$";
        this.stringPattern = Pattern.compile(stringStrPattern);

        String booleanStrPattern = "^\\s*(final\\s+)?boolean\\s+((_\\w|[a-zA-Z])\\w*(\\s*=\\s*((true|false" +
                "|-?\\d+(.\\d*)?)|(_\\w|[a-zA-Z])\\w*))?\\s*,\\s*)*(_\\w|[a-zA-Z])\\w*(\\s*=\\s*((true|" +
                "false|-?\\d+(.\\d*)?)|(_\\w|[a-zA-Z])\\w*))?\\s*;\\s*$";
        this.booleanPattern = Pattern.compile(booleanStrPattern);

        String charStrPattern = "^\\s*(final\\s+)?char\\s+((_\\w|[a-zA-Z])\\w*(\\s*=\\s*(\'.?\'|(_\\w|" +
                "[a-zA-Z])\\w*))?\\s*,\\s*)*(_\\w|[a-zA-Z])\\w*(\\s*=\\s*(\'.?\'|(_\\w|[a-zA-Z])\\w*))" +
                "?\\s*;\\s*$";
        this.charPattern = Pattern.compile(charStrPattern);

        String fullVariableAssStrPattern = "^\\s*(_\\w|\\w)\\w*\\s*=\\s*(\".*\"|\'\\.\'|-?\\d+(.\\d)?" +
                "|true|false|(_\\w|[a-z])\\w*)\\s*;\\s*$";
        this.fullVariableAssPattern = Pattern.compile(fullVariableAssStrPattern);
    }


    /**
     * this method get line of variable and check if its valid
     *
     * @param line         the line of the variable
     * @param globalVarLst the global variable
     * @param localVarLst  the local variable
     * @throws VariableException if the variable is illegal
     */
    public void variableValid(String line, HashMap<String, Object[]> globalVarLst,
                              HashMap<String, Object[]> localVarLst) throws VariableException {

        ArrayList<String> lineLst = lineDivider(line);

        if (intPattern.matcher(line).matches()) {
            intVar.checkValid(lineLst, globalVarLst, localVarLst);

        } else if (doublePattern.matcher(line).matches()) {
            doubleVar.checkValid(lineLst, globalVarLst, localVarLst);

        } else if (booleanPattern.matcher(line).matches()) {
            booleanVar.checkValid(lineLst, globalVarLst, localVarLst);

        } else if (stringPattern.matcher(line).matches()) {
            StringVar.checkValid(lineLst, globalVarLst, localVarLst);

        } else if (charPattern.matcher(line).matches()) {
            charVar.checkValid(lineLst, globalVarLst, localVarLst);

        } else if (fullVariableAssPattern.matcher(line).matches()) {
            varAssign.checkValid(lineLst, globalVarLst);

        } else {
            throw new InValidVarAssignException();
        }
    }


    /**
     * this method get line of variable and divide it to arrayList
     *
     * @param line the all line
     * @return array list with the line splits
     */
    private ArrayList<String> lineDivider(String line) {

        ArrayList<String> output = new ArrayList<String>();
        String[] splitLines = line.split("(\\s+|[,;])");

        for (int i = 0; i < splitLines.length ; i++) {
            String splitLine = splitLines[i];

            if (splitLine.length() != ZERO) {

                if (splitLine.contains("=")) {
                    String[] var = splitLine.split("");
                    output.addAll(Arrays.asList(var));

                } else if (splitLine.equals("\"") && i+2 < splitLines.length
                        &&splitLines[i+2].equals("\"")) {

                    splitLine = splitLine + splitLines[i+1] +splitLine;
                    output.add(splitLine);
                    i += 2;

                } else {
                    output.add(splitLine);
                }
            }
        }
        return output;
    }
}
