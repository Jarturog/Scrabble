// Código hecho por Juan Arturo Abaurrea Calafell e Iker García De León Seguí
package ficheros;

import java.io.BufferedReader;
import java.io.FileReader;
import scrabble.Palabra;

public class FicheroIn {

    private FileReader f;
    private BufferedReader br;
    private int c; // letra para ir leyendo en el fichero
    
    // consturctor que abre un fichero (el introducido como String)
    public FicheroIn(String nombre) {
        try {
            f = new FileReader(nombre);
            br = new BufferedReader(f);
        } catch (Exception e) {
            System.out.println("Error, fichero no encontrado");
        }
    }

    // método que cierra el fichero
    public void cerrar() {
        try {
            br.close();
            f.close();
        } catch (Exception e) {
            System.out.println("Error al cerrar");
        }
    }

    // método que lee una línea entera y la devuelve
    public String leerLinea() {
        try {
            String l = br.readLine();
            return l;
        } catch (Exception e) {
            System.out.println("Error al leer una línea");
            return "";
        }
    }

    // método que devuelve la Palabra que haya leído
    public Palabra leerPalabra() {
        Palabra pal = new Palabra();
        try {
            c = br.read();
        } catch (Exception e) {
            System.out.println("Error al leer palabra");
        }
        saltarBlancosYOtros();
        while (c > 32) { // mientras sea alguna letra o número y no final (-1)
            pal.anyadirCaracter((char) c);
            try {
                c = br.read();
            } catch (Exception e) {
                System.out.println("Error al leer palabra");
            }
        }
        return pal;
    }

    // método que salta lo que no sean carácteres visibles
    private void saltarBlancosYOtros() {
        while (c != -1 && c <= 32) {
            try { // si no es el final y no son letras o números paso por encima
                c = br.read();
            } catch (Exception e) {
                System.out.println("Error saltando blancos y otros");
            }
        }
    }
}
