package utils;

import java.util.HashMap;
import java.util.Map;

public class VariableStorage {
    // A map to store variables with their names and values
    private final Map<String, Object> variables = new HashMap<>();

    // Sets a variable with a given name and value
    public void setVariable(String name, Object value) {
        if (!isValidName(name)) {
            throw new IllegalArgumentException("Invalid variable name: '" + name + "'");
        }
        variables.put(name, value);
    }

    // Gets the value of a variable by its name
    public Object getVariable(String name) {
        if (!variables.containsKey(name)) {
            throw new IllegalArgumentException("Variable '" + name + "' does not exist.");
        }
        return variables.get(name);
    }

    // Checks if a variable exists
    public boolean hasVariable(String name) {
        return variables.containsKey(name);
    }

    // Lists all the defined variables
    public void listVariables() {
        System.out.println("Current Variables:");
        if (variables.isEmpty()) {
            System.out.println("No variables defined.");
        } else {
            for (Map.Entry<String, Object> entry : variables.entrySet()) {
                System.out.println(entry.getKey() + " = " + entry.getValue());
            }
        }
    }

    // Validates if the variable name follows the Java naming conventions
    private boolean isValidName(String name) {
        return name.matches("^[a-zA-Z_][a-zA-Z0-9_]*$");
    }
}
