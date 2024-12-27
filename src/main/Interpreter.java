package main;

import utils.VariableStorage;
//import Utilities2.Interpreter;

public class Interpreter {
    public static void main(String[] args) {
        // Create an instance of VariableStorage to store variables
        VariableStorage variableStorage = new VariableStorage();

        // Way to set variable
        variableStorage.setVariable("x", 2);
        variableStorage.setVariable("y", 2);

        // List created variables
        variableStorage.listVariables();

    }
}
