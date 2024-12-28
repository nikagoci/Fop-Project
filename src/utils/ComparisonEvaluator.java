package utils;

public class ComparisonEvaluator {
    private final VariableStorage variableStorage;

    // Constructor to initialize with VariableStorage
    public ComparisonEvaluator(VariableStorage variableStorage) {
        this.variableStorage = variableStorage;
    }

    // Evaluates a comparison expression (e.g., x < 5, y >= 10)
    public boolean evaluateComparison(String expression) {
        // Supported comparison operators
        String[] operators = {"==", "!=", "<=", ">=", "<", ">"};

        for (String operator : operators) {
            String[] parts = expression.split("\\" + operator);
            if (parts.length == 2) {
                Object leftOperand = getOperandValue(parts[0].trim());
                Object rightOperand = getOperandValue(parts[1].trim());

                // Ensure both operands are integers for comparison
                if (!(leftOperand instanceof Integer) || !(rightOperand instanceof Integer)) {
                    throw new IllegalArgumentException("Comparison only supports integers.");
                }

                int left = (int) leftOperand;
                int right = (int) rightOperand;

                // Perform the comparison based on the operator
                switch (operator) {
                    case "==":
                        return left == right;
                    case "!=":
                        return left != right;
                    case "<=":
                        return left <= right;
                    case ">=":
                        return left >= right;
                    case "<":
                        return left < right;
                    case ">":
                        return left > right;
                }
            }
        }
        throw new IllegalArgumentException("Invalid comparison expression: " + expression);
    }

    // Helper to get the value of a variable or a literal number
    private Object getOperandValue(String operand) {
        if (variableStorage.hasVariable(operand)) {
            return variableStorage.getVariable(operand); // Fetch the value of the variable
        } else if (operand.matches("-?\\d+")) {
            return Integer.parseInt(operand); // Parse as an integer
        } else {
            throw new IllegalArgumentException("Invalid operand: " + operand);
        }
    }
}

