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
}
