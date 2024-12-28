package utils;

import java.util.Scanner;
import utils.ComparisonEvaluator;

public class Reader {
    private final VariableStorage variableStorage;
    private final ArithmeticEvaluator arithmeticEvaluator;
    private final ComparisonEvaluator comparisonEvaluator;

    public Reader(VariableStorage variableStorage) {
        this.variableStorage = variableStorage;
        this.arithmeticEvaluator = new ArithmeticEvaluator(variableStorage);
        this.comparisonEvaluator = new ComparisonEvaluator(variableStorage);
    }

    public void start() {
        System.out.println("Welcome to MiniKotlin reader!");
        System.out.println("Type 'help' for a list of commands.");

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("> ");
            String input = scanner.nextLine().trim();
            System.out.println("Input received: " + input);  // Debugging line

            if (input.isEmpty()) continue;

            try {
                processCommand(input);
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }


    private void processCommand(String input) {
        if (input.startsWith("if")) {
            processIfStatement(input);  // Route the if statement properly
        } else if (input.startsWith("while")) {
            processWhileLoop(input);
        } else if (input.startsWith("var ")) {
            processVariableDeclaration(input);
        } else if (input.startsWith("print(")) {
            processPrint(input);
        } else if (input.equals("list")) {
            variableStorage.listVariables();
        } else if (input.equals("help")) {
            displayHelp();
        } else if (input.equals("exit")) {
            exitInterpreter();
        } else {
            System.out.println("Error: Unknown command.");
        }
    }




    private void processAssignment(String input) {
        String[] parts = input.split("=");
        if (parts.length != 2) {
            throw new IllegalArgumentException("Invalid assignment: " + input);
        }

        String variableName = parts[0].trim();
        String valueExpression = parts[1].trim();
        Object value = evaluateExpression(valueExpression);

        variableStorage.setVariable(variableName, value);
    }


    private void processWhileLoop(String input) {
        System.out.println("Processing while loop: " + input); // Debugging line

        // Ensure the while loop starts with "while(" and ends with ")"
        if (!input.startsWith("while(") || !input.contains("){")) {
            throw new IllegalArgumentException("Syntax error: Invalid while loop.");
        }

        // Extract the condition between "while(" and ")"
        String condition = input.substring(input.indexOf("(") + 1, input.indexOf(")")).trim();
        // Extract the body between "{ and }"
        String body = input.substring(input.indexOf("{") + 1, input.lastIndexOf("}")).trim();

        // Debugging: Check if we are getting the correct condition and body
        System.out.println("Condition: " + condition);  // Debugging line
        System.out.println("Body: " + body);  // Debugging line

        // Loop until the condition is false
        while (evaluateCondition(condition)) {
            // Split the body by semicolon and process each statement
            String[] statements = body.split(";");
            for (String statement : statements) {
                statement = statement.trim();
                // Process each statement inside the loop
                if (statement.startsWith("print(")) {
                    processPrint(statement);
                } else {
                    processAssignment(statement);  // This will handle the assignment like x = x + 1
                }
            }

            // Re-evaluate the condition after executing the body
            // We need to ensure that the condition is evaluated again with the updated variable values
            condition = input.substring(input.indexOf("(") + 1, input.indexOf(")")).trim();
        }
    }

    private void processIfStatement(String input) {
        // Ensure the if statement has parentheses and curly braces
        if (input.startsWith("if") && input.contains("(") && input.contains(")")) {
            // Extract the condition inside the parentheses
            String conditionPart = input.substring(input.indexOf("(") + 1, input.indexOf(")")).trim();

            // Extract the body of the 'if' block, starting after the first '{' and ending before the last '}'
            String bodyPart = input.substring(input.indexOf("{") + 1, input.indexOf("}")).trim();

            // Check if there's an 'else' part
            String elsePart = "";
            if (input.contains("else")) {
                elsePart = input.substring(input.indexOf("else") + 4).trim();
            }

            // Evaluate the condition (true or false)
            boolean condition = evaluateCondition(conditionPart);
            if (condition) {
                // Process the body if the condition is true
                processCommand(bodyPart);
            } else if (!elsePart.isEmpty()) {
                // Process the 'else' body if the condition is false and there's an 'else' part
                processCommand(elsePart);
            }
        } else {
            throw new IllegalArgumentException("Syntax error: Invalid if statement.");
        }
    }





    private boolean evaluateCondition(String condition) {
        return comparisonEvaluator.evaluateComparison(condition);
    }

    private Object evaluateExpression(String expression) {
        try {
            if (expression.matches("-?\\d+")) {
                return Integer.parseInt(expression);
            } else if (expression.matches("\".*\"")) {
                return expression.substring(1, expression.length() - 1);
            } else {
                return arithmeticEvaluator.evaluateArithmetic(expression);
            }
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid expression: " + expression);
        }
    }

    private void processVariableDeclaration(String input) {
        String[] parts = input.substring(4).split("=");
        if (parts.length != 2) {
            System.out.println("Syntax error: Invalid variable declaration.");
            return;
        }

        String name = parts[0].trim();
        String value = parts[1].trim();
        Object parsedValue = evaluateExpression(value);
        variableStorage.setVariable(name, parsedValue);
    }

    private void processPrint(String input) {
        if (!input.startsWith("print(") || !input.endsWith(")")) {
            throw new IllegalArgumentException("Syntax error: Invalid print statement.");
        }

        String content = input.substring(6, input.length() - 1).trim();
        Object result;

        if (variableStorage.hasVariable(content)) {
            result = variableStorage.getVariable(content);
        } else {
            result = evaluateExpression(content);
        }

        System.out.println(result);
    }

    private void displayHelp() {
        System.out.println(
                "Welcome to MiniKotlin Interpreter!\n" +
                        "Supported commands:\n" +
                        "- var <name> = <value> : Declare a variable.\n" +
                        "- print(<value>) : Print a value or variable.\n" +
                        "- while(<condition>){ <statements>; } : Perform a loop.\n" +
                        "- list : List all stored variables.\n" +
                        "- help : Display this help message.\n" +
                        "- exit : Exit the interpreter."
        );
    }

    private void exitInterpreter() {
        System.out.println("Exiting MiniKotlin Interpreter.");
        System.exit(0);
    }
}



