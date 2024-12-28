package utils;

public class ArithmeticEvaluator {

    private final VariableStorage variableStorage;

    // Constructor to initialize with VariableStorage
    public ArithmeticEvaluator(VariableStorage variableStorage) {
        this.variableStorage = variableStorage;
    }

    // Evaluates a basic arithmetic expression (addition, subtraction, multiplication, division, modulo)
    public Object evaluateArithmetic(String expression) {
        // List of supported operators
        String[] operators = {"+", "-", "*", "/", "%"};

        for (String operator : operators) {
            // Escape the operator correctly for regular expressions
            String escapedOperator = "\\" + operator;

            // Split the expression using the operator, ensuring it's properly escaped
            String[] parts = expression.split(escapedOperator);
            if (parts.length == 2) {
                Object leftOperand = getOperandValue(parts[0].trim());
                Object rightOperand = getOperandValue(parts[1].trim());

                // Perform the arithmetic operation based on the operator
                int left = (int) leftOperand;
                int right = (int) rightOperand;

                switch (operator) {
                    case "+":
                        return left + right;
                    case "-":
                        return left - right;
                    case "*":
                        return left * right;
                    case "/":
                        if (right == 0) {
                            throw new ArithmeticException("Cannot divide by zero.");
                        }
                        return left / right;
                    case "%":
                        return left % right;
                }
            }
        }
        throw new IllegalArgumentException("Unsupported expression: " + expression);
    }

    // Helper to get the value of a variable or number from an expression
    private Object getOperandValue(String operand) {
        if (variableStorage.hasVariable(operand)) {
            return variableStorage.getVariable(operand); // Return the value of the variable
        } else if (operand.matches("-?\\d+")) {
            return Integer.parseInt(operand); // Parse as integer
        } else {
            throw new IllegalArgumentException("Invalid operand: " + operand);
        }
    }
}


