// Código hecho por Juan Arturo Abaurrea Calafell e Iker García De León Seguí
package scrabble;

public class Palabra {

    private char[] letras;// las letras que forman la palabra
    private int longitud;// la cantidad de letras que tiene
    private static final int MAX = 24; // la palabra más larga del diccionario es "electroencefalografista", que tiene 23 letras

    // constructor que genera una Palabra vacía
    public Palabra() {
        letras = new char[MAX];
        longitud = 0;
    }

    // constructor que convierte un String en Palabra
    public Palabra(String frase) {
        letras = frase.toCharArray();
        longitud = letras.length;
    }

    // método que añade carácteres a instancias
    public void anyadirCaracter(char c) {
        letras[longitud++] = c;
    }

    // método que devuelve true si la palabra está vacía
    public boolean vacia() {
        return (longitud == 0);
    }

    // método devuelve la cantidad de letras de una palabra
    public int getLongitud() {
        return longitud;
    }

    // método que convierte una Palabra a String
    @Override
    public String toString() {
        String s = "";
        for (int idx = 0; idx < longitud; idx++) {
            s = s + letras[idx];
        }
        return s;
    }

    // método que devuelve el valor entero real de dígitos escritos con chars
    public int convertir() {
        int res = 0;
        int mult = 1;
        for (int i = longitud - 1; i >= 0; i--) {
            res = res + (letras[i] - '0') * mult;// un dígito char menos 0 char da su valor real
            mult = mult * 10;// al usar base 10 cada dígito vale 10 veces más
        }
        return res;// resultado entero real
    }

    // método que devuelve true si dos palabras son exactamente iguales
    public boolean esIgual(Palabra comparada) {
        // si no son del mismo tamaño no son iguales
        if (longitud != comparada.longitud) {
            return false;
        } // ahora recorre ambas palabras comprobando si cada letra coincide
        for (int idx = 0; idx < longitud; idx++) {
            if (letras[idx] != comparada.letras[idx]) {
                return false; // si una letra no es igual, no son iguales
            }
        }
        return true;// son iguales
    }

    // método que pasa cada letra a su valor numérico de puntaje
    public int aPuntos(Palabra[] tiposFichas, int[] tiposValores) {
        int puntos = 0;
        for (int idx = 0; idx < longitud; idx++) {// recorre la palabra
            for (int ind = 0; ind < tiposFichas.length; ind++) {// y los tipos de fichas
                if (tiposFichas[ind].letras[0] == letras[idx]) {// compara chars
                    // comparo la letra 0 porque tiposFichas es un array de Palabras de 1 de longitud cada una
                    puntos = puntos + tiposValores[ind];// acumula puntos
                }
            }
        }
        return puntos;// devuelve el valor total de la palabra
    }

    // método que devuelve true si hay un false en un array de boolean
    private static boolean falseEntreTrues(boolean[] arrayBoolean) {
        for (int idx = 0; idx < arrayBoolean.length; idx++) {
            if (!arrayBoolean[idx]) {
                return true; // hay algún false entre los trues
            }
        }
        return false; // solo hay trues
    }

    // método que encuentra si se puede hacer una palabra con una selección de letras
    public boolean dentroDeSeleccion(Palabra[] seleccion) {
        // creo las listas para saber si cada letra ha sido usada
        // y están rellenas de falses de por sí
        boolean[] selUsadas = new boolean[seleccion.length];
        boolean[] palUsadas = new boolean[longitud];
        // recorro la palabra
        for (int idx = 0; idx < longitud; idx++) {
            // y en cada letra de la palabra recorro la selección de letras
            for (int ind = 0; ind < seleccion.length && !palUsadas[idx]; ind++) {
                // si alguna letra en la selección está en la palabra y no se ha usado ya
                if (seleccion[ind].letras[0] == letras[idx]// comparo chars
                        && !selUsadas[ind]) {
                    selUsadas[ind] = true;
                    palUsadas[idx] = true;
                }// las marco como usadas
            }
        }
        // si todas las letras de la palabra han sido usadas devuelve true
        return !falseEntreTrues(palUsadas);
    }
}
