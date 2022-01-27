// Código que forma parte del proyecto de Juan Arturo Abaurrea Calafell e
// Iker García De León Seguí, pero que no lo creamos nosotros sino que se nos
// fue entregado por el profesorado de la UIB
package scrabble;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class LT {

    private char[] leerln() {
        String res = "";
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(System.in, "ISO-8859-1"));
            res = br.readLine();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res.toCharArray();
    }

    public Integer leerEntero() {
        Integer res = 0;
        try {
            char[] pal = leerln();
            res = Integer.parseInt(new String(pal));
        } catch (Exception e) {
            res = null;
        }
        return res;
    }

    public Double leerReal() {
        Double res = 0.0;
        try {
            char[] pal = leerln();
            res = Double.parseDouble(new String(pal));
        } catch (Exception e) {
            res = null;
        }
        return res;
    }

    public String leerLinea() {
        String res;
        char[] pal = leerln();
        res = new String(pal);
        return res;
    }

    public Character leerCaracter() {
        Character res;
        char[] pal = leerln();
        String s = new String(pal);
        if (s.length() == 0) {
            res = null;
        } else {
            res = new Character(s.charAt(0));
        }
        return res;
    }
}
