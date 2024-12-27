package main;

import utils.VariableStorage;
import utils.Reader;

public class Interpreter {
    public static void main(String[] args) {
        // Create an instance of VariableStorage to store variables
        VariableStorage variableStorage = new VariableStorage();

        // Create an instance of Interpreter and pass the VariableStorage to it
        Reader reader = new Reader(variableStorage);

        // Start the interpreter to read and process commands
        reader.start();

    }
}
