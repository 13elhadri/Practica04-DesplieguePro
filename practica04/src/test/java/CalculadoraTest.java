import org.example.calc.Calculadora;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CalculadoraTest {

    /**
     * Verifica que la operación de suma en la calculadora funcione correctamente.
     * Se espera que la suma de 2 y 3 resulte en 5.
     */
    @Test
    void sumar() {
        int resultado = Calculadora.sumar(2, 3);
        assertEquals(5, resultado);
    }

    /**
     * Verifica que la operación de resta en la calculadora funcione correctamente.
     * Se espera que la resta de 5 menos 2 resulte en 3.
     */
    @Test
    void restar() {
        int resultado = Calculadora.restar(5, 2);
        assertEquals(3, resultado);
    }

    /**
     * Verifica que la operación de multiplicación en la calculadora funcione correctamente.
     * Se espera que la multiplicación de 2 por 3 resulte en 6.
     */
    @Test
    void multiplicar() {
        int resultado = Calculadora.multiplicar(2, 3);
        assertEquals(6, resultado);
    }

    /**
     * Verifica que la operación de división en la calculadora funcione correctamente.
     * Se espera que la división de 10 entre 2 resulte en 5.
     */
    @Test
    void dividir() {
        int resultado = Calculadora.dividir(10, 2);
        assertEquals(5, resultado);
    }

    /**
     * Verifica que la división entre cero lance una excepción de tipo ArithmeticException.
     * Esto garantiza que el método de división maneje correctamente un divisor de valor cero.
     */
    @Test
    void divisionPorCero() {
        assertThrows(ArithmeticException.class, () -> Calculadora.dividir(10, 0));
    }
}

