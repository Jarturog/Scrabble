// Código hecho por Juan Arturo Abaurrea Calafell e Iker García De León Seguí
package ficheros;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FicheroOut {

    private FileWriter f;
    private BufferedWriter bw;

    // constructor que abre el fichero
    public FicheroOut(String nombre) {
        try {
            f = new FileWriter(nombre, true);// he de poner true para que no sobreescriba
            bw = new BufferedWriter(f);
        } catch (Exception e) {
            System.out.println("Error creando fichero de escritura");
        }
    }

    // método que cierra el fichero
    public void cerrar() {
        try {
            bw.close();
            f.close();
        } catch (Exception e) {
            System.out.println("Error al cerrar");
        }
    }

    // método que escribe los resultados
    public void escribirRes(String resultados) {
        try {
            bw.newLine();
            bw.write(resultados);
        } catch (Exception e) {
            System.out.println("Error al guardar resultados");
        }
    }

    // método que devuelve la fecha actual como String
    public String fechaActual() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        return formatter.format(date);
    }
}
