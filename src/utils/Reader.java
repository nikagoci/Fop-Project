package utils;

import java.util.Scanner;

public class Reader {
    private final VariableStorage variableStorage; // Reference to the variable storage for managing variables
    private final ArithmeticEvaluator arithmeticEvaluator; // Reference to the arithmetic evaluator

    // Constructor to initialize the reader with the given VariableStorage and ArithmeticEvaluator
    public Reader(VariableStorage variableStorage) {
        this.variableStorage = variableStorage;
        this.arithmeticEvaluator = new ArithmeticEvaluator(variableStorage); // Initialize the evaluator
    }

    // Starts the reader, reading and processing commands from the terminal
    public void start() {
        System.out.println("Welcome to MiniKotlin reader!");
        System.out.println("Type 'help' for a list of commands.");

        Scanner scanner = new Scanner(System.in); // Scanner for reading user input

        // Main loop for processing commands
        while (true) {
            System.out.print("> "); // Prompt for user input
            String input = scanner.nextLine().trim(); // Read and trim user input

            if (input.isEmpty()) continue; // Skip empty input

            try {
                processCommand(input); // Process the user command
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage()); // Print error messages for exceptions
            }
        }
    }

    // Processes a single command based on the input
    private void processCommand(String input) {
        if (input.startsWith("var ")) {
            processVariableDeclaration(input); // Handle variable declarations
        } else if (input.startsWith("print(")) {
            processPrint(input); // Handle print statements
        } else if (input.equals("list")) {
            variableStorage.listVariables(); // List all stored variables
        } else if (input.equals("help")) {
            displayHelp(); // Show help message
        } else if (input.equals("exit")) {
            exitInterpreter(); // Exit the interpreter
        } else {
            System.out.println("Error: Unknown command."); // Handle unknown commands
        }
    }

    // Processes a variable declaration command (example var x = a + b)
    private void processVariableDeclaration(String input) {
        String[] parts = input.substring(4).split("="); // Remove "var " prefix and split by "="

        if (parts.length != 2) {
            System.out.println("Syntax error: Invalid variable declaration.");
            return;
        }

        String name = parts[0].trim(); // Extract variable name
        String value = parts[1].trim(); // Extract the value to assign
        Object parsedValue = evaluateExpression(value); // Evaluate the expression (arithmetic)
        variableStorage.setVariable(name, parsedValue); // Store the variable
    }

    // Processes a print command (example print(x))
    private void processPrint(String input) {
        if (!input.startsWith("print(") || !input.endsWith(")")) {
            System.out.println("Syntax error: Invalid print statement.");
            return;
        }

        String content = input.substring(6, input.length() - 1).trim(); // Remove "print(" and ")"
        Object result;

        if (variableStorage.hasVariable(content)) {
            result = variableStorage.getVariable(content); // Fetch the variable value if it exists
        } else {
            result = evaluateExpression(content); // Evaluate the expression if it's a literal or arithmetic expression
        }

        System.out.println(result); // Print the result (value of the variable or the literal)
    }

    // Evaluates an expression (either a number, string, or arithmetic)
    private Object evaluateExpression(String expression) {
        try {
            // Check if the expression is a simple number or string
            if (expression.matches("-?\\d+")) {
                return Integer.parseInt(expression); // Integer
            } else if (expression.matches("\".*\"")) {
                return expression.substring(1, expression.length() - 1); // String
            } else {
                // Delegate to ArithmeticEvaluator for arithmetic expressions
                return arithmeticEvaluator.evaluateArithmetic(expression);
            }
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid expression: " + expression);
        }
    }

    // Displays the help message with supported commands
    private void displayHelp() {
        System.out.println(
                "Welcome to MiniKotlin Interpreter!\n" +
                        "Supported commands:\n" +
                        "- var <name> = <value> : Declare a variable.\n" +
                        "- print(<value>) : Print a value or variable.\n" +
                        "- list : List all stored variables.\n" +
                        "- help : Display this help message.\n" +
                        "- exit : Exit the interpreter."
        );
    }

    // Exits the interpreter and prints a goodbye message
    private void exitInterpreter() {
        System.out.println("Exiting MiniKotlin Interpreter.");
        System.exit(0); // Exit the program
    }
}


