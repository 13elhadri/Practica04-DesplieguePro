import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean salir = false;

        do {
            mostrarMenu();

            // Verifica si hay un entero disponible
            int opcion = leerOpcion(scanner);
            if (opcion == -1) {
                continue; // Vuelve al inicio del bucle si hubo un error en la opción
            }

            switch (opcion) {
                case 1:
                    realizarOperacion(scanner, "suma");
                    break;

                case 2:
                    realizarOperacion(scanner, "resta");
                    break;

                case 3:
                    realizarOperacion(scanner, "multiplicación");
                    break;

                case 4:
                    realizarDivision(scanner);
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

    private static void mostrarMenu() {
        System.out.println("Elija una opción:");
        System.out.println("1. Suma");
        System.out.println("2. Resta");
        System.out.println("3. Multiplicación");
        System.out.println("4. División");
        System.out.println("5. Salir");
        System.out.print("Ingrese su opción: ");
    }

    private static int leerOpcion(Scanner scanner) {
        if (!scanner.hasNextInt()) {
            System.out.println("Error: Debe ingresar un número entre 1 y 5.");
            scanner.next(); // Limpia el buffer de entrada
            return -1; // Indicador de error
        }
        return scanner.nextInt();
    }

    private static void realizarOperacion(Scanner scanner, String operacion) {
        System.out.print("Escoja el primer número: ");
        int num1 = leerNumero(scanner);
        System.out.print("Escoja el segundo número: ");
        int num2 = leerNumero(scanner);
        
        int resultado = 0;
        switch (operacion) {
            case "suma":
                resultado = Calculadora.sumar(num1, num2);
                break;
            case "resta":
                resultado = Calculadora.restar(num1, num2);
                break;
            case "multiplicación":
                resultado = Calculadora.multiplicar(num1, num2);
                break;
        }
        System.out.println("El resultado es: " + resultado);
    }

    private static void realizarDivision(Scanner scanner) {
        System.out.print("Escoja el primer número: ");
        int num1 = leerNumero(scanner);
        System.out.print("Escoja el segundo número: ");
        int num2 = leerNumero(scanner);
        
        if (num2 != 0) {
            double resultado = Calculadora.dividir(num1, num2);
            System.out.println("El resultado es: " + resultado);
        } else {
            System.out.println("Error: No se puede dividir entre cero.");
        }
    }

    private static int leerNumero(Scanner scanner) {
        while (true) {
            if (scanner.hasNextInt()) {
                return scanner.nextInt();
            } else {
                System.out.println("Error: Debe ingresar un número válido.");
                scanner.next(); // Limpia el buffer de entrada
            }
        }
    }
}
