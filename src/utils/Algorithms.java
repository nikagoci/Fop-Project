package utils;

public class Algorithms {

    public int sumOfNumbers(int n) {
        return n * (n + 1) / 2;
    }

    public int factorial(int n) {
        if (n <= 1) return 1;
        return n * factorial(n - 1);
    }

    public int gcd(int a, int b) {
        return b == 0 ? a : gcd(b, a % b);
    }

    public int reverse(int n) {
        int reversed = 0;
        while (n != 0) {
            reversed = reversed * 10 + n % 10;
            n /= 10;
        }
        return reversed;
    }

    public boolean primeChecker(int n) {
        if (n <= 1) return false;
        for (int i = 2; i * i <= n; i++) {
            if (n % i == 0) return false;
        }
        return true;
    }

    public boolean palindromeChecker(int n) {
        return n == reverse(n);
    }

    public int largestDigit(int n) {
        int largest = 0;
        while (n != 0) {
            largest = Math.max(largest, n % 10);
            n /= 10;
        }
        return largest;
    }

    public int sumOfDigits(int n) {
        int sum = 0;
        while (n != 0) {
            sum += n % 10;
            n /= 10;
        }
        return sum;
    }

    public String multiplicationTable(int n) {
        StringBuilder result = new StringBuilder();
        for (int i = 1; i <= 10; i++) {
            result.append(n).append(" x ").append(i).append(" = ").append(n * i).append("\n");
        }
        return result.toString();
    }

    public int fibonacciPos(int n) {
        if (n <= 1) return n;
        int a = 0, b = 1;
        for (int i = 2; i <= n; i++) {
            int temp = a + b;
            a = b;
            b = temp;
        }
        return b;
    }
}

