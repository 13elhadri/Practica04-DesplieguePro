package org.example;

import org.example.calc.Calculadora;

import java.util.Scanner;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean salir = false;

        do {
            System.out.println("Elija una opción:");
            System.out.println("1. Suma");
            System.out.println("2. Resta");
            System.out.println("3. Multiplicación");
            System.out.println("4. División");
            System.out.println("5. Salir");
            System.out.print("Ingrese su opción: ");

            // Verifica si hay un entero disponible
            if (!scanner.hasNextInt()) {
                System.out.println("Error: Debe ingresar un número entre 1 y 5.");
                scanner.next(); // Limpia el buffer de entrada
                continue; // Vuelve al inicio del bucle
            }

            int opcion = scanner.nextInt();

            // Verifica que la opción esté dentro del rango permitido
            if (opcion < 1 || opcion > 5) {
                System.out.println("Error: Debe ingresar un número entre 1 y 5.");
                continue; // Vuelve al inicio del bucle
            }

            switch (opcion) {
                case 1:
                    System.out.print("Escoja el primer número: ");
                    int num1 = scanner.nextInt();
                    System.out.print("Escoja el segundo número: ");
                    int num2 = scanner.nextInt();
                    int resultadoSuma = Calculadora.sumar(num1, num2);
                    System.out.println("El resultado es: " + resultadoSuma);
                    break;

                case 2:
                    System.out.print("Escoja el primer número: ");
                    int num3 = scanner.nextInt();
                    System.out.print("Escoja el segundo número: ");
                    int num4 = scanner.nextInt();
                    int resultadoResta = Calculadora.restar(num3, num4);
                    System.out.println("El resultado es: " + resultadoResta);
                    break;

                case 3:
                    System.out.print("Escoja el primer número: ");
                    int num5 = scanner.nextInt();
                    System.out.print("Escoja el segundo número: ");
                    int num6 = scanner.nextInt();
                    int resultadoMultiplicacion = Calculadora.multiplicar(num5, num6);
                    System.out.println("El resultado es: " + resultadoMultiplicacion);
                    break;

                case 4:
                    System.out.print("Escoja el primer número: ");
                    int num7 = scanner.nextInt();
                    System.out.print("Escoja el segundo número: ");
                    int num8 = scanner.nextInt();
                    if (num8 != 0) {
                        double resultadoDivision = Calculadora.dividir(num7, num8);
                        System.out.println("El resultado es: " + resultadoDivision);
                    } else {
                        System.out.println("Error: No se puede dividir entre cero.");
                    }
                    break;

                case 5:
                    salir = true;
                    System.out.println("Saliendo de la calculadora...");
                    break;

                default:
                    System.out.println("Opción no válida. Por favor elija una opción entre 1 y 5.");
                    break;
            }
        } while (!salir);

        scanner.close();
    }
}
