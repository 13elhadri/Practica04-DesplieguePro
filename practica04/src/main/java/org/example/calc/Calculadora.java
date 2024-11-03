package org.example.calc;

public class Calculadora {
    /**
     * Realiza la suma de dos números enteros.
     * @author Yahya El Hadri
     * @param num1 Primer número a sumar.
     * @param num2 Segundo número a sumar.
     * @return Resultado de la suma de num1 y num2.
     */
    public static int sumar(int num1, int num2) {
        return (num1 + num2);
    }

    /**
     * Realiza la resta entre dos números enteros.
     * @author Yahya El Hadri
     * @param num1 Minuendo de la operación.
     * @param num2 Sustraendo de la operación.
     * @return Resultado de la resta de num1 menos num2.
     */
    public static int restar(int num1, int num2) {
        return (num1 - num2);
    }

    /**
     * Realiza la multiplicación de dos números enteros.
     * @author Yahya El Hadri
     * @param num1 Primer factor de la multiplicación.
     * @param num2 Segundo factor de la multiplicación.
     * @return Producto de num1 y num2.
     */
    public static int multiplicar(int num1, int num2) {
        return (num1 * num2);
    }

    /**
     * Realiza la división de dos números enteros.
     * @author Yahya El Hadri
     * @param num1 Dividendo de la operación.
     * @param num2 Divisor de la operación.
     * @return Cociente de la división de num1 entre num2.
     */
    public static int dividir(int num1, int num2) {
        return (num1 / num2);
    }

}
