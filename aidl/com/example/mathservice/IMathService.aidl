package com.example.mathservice;

/**
 * AIDL interface for Math Service
 * Provides basic arithmetic operations
 */
interface IMathService {
    /**
     * Add two integers
     * @param a First operand
     * @param b Second operand
     * @return Sum of a and b
     */
    int add(int a, int b);

    /**
     * Subtract two integers
     * @param a First operand
     * @param b Second operand
     * @return Difference of a and b (a - b)
     */
    int subtract(int a, int b);
}
