// Código hecho por Juan Arturo Abaurrea Calafell e Iker García De León Seguí
package scrabble;

import ficheros.Fichero;

public class Jugar {

    Fichero fichero;// declaro la instancia fichero
    LT lector = new LT();

    private int puntos; // del jugador
    private int puntosRival; // de la CPU

    public Jugar(String diccionario) {
        puntos = 0;// puntos totales = 0
        puntosRival = 0;// puntos totales del rival = 0
        fichero = new Fichero(diccionario);// asigno el diccionario elegido
    }

    // método partida, el cual se encarga de cada ronda
    public void partida(Palabra alias, int turnos, int dificultad) {
        fichero.inicioAlfabeto();// inicio el alfabeto
        // for que ocurrirá tantas veces como turnos se hayan indicado
        for (int turnoActual = 0; turnoActual < turnos; turnoActual++) {
            System.out.println("Turno " + (turnoActual + 1) + " de " + turnos + ".");
            System.out.println("Haz una palabra con las siguientes fichas: ");
            System.out.println("");
            // genero la selección de fichas aleatorias que le han tocado al usuario
            Palabra[] seleccion = fichero.generar();
            for (int idx = 0; idx < seleccion.length; idx++) {
                System.out.print(seleccion[idx] + " ");// las imprimo
            }
            System.out.println("");
            System.out.print("Palabra: ");// pido la palabra que ha puesto basada en la selección
            Palabra escrita = new Palabra(lector.leerLinea());
            // si la palabra está dentro de la selección
            if (escrita.dentroDeSeleccion(seleccion)) {
                // busca que esté en el diccionario y le da los puntos correspondientes
                int ptos = fichero.buscarYPuntuar(escrita);
                System.out.println("Tus puntos ganados son: " + ptos);
                puntos = puntos + ptos;// acumula los puntos totales
            } else {// si no estaba en la selección
                System.out.print("La palabra escrita no se podía escribir con la selección, ");
                System.out.println("-10 puntos.");
                puntos = puntos - 10;
            }
            System.out.println("");
            if (dificultad != 0) { // si no ha jugado solo
                puntosRival = puntosRival + fichero.rival(dificultad);// se suman los puntos
            }
        }// al acabar la partida
        System.out.println("Tus puntos totales en esta partida son: " + puntos);
        Fichero.guardarRes(alias, turnos, puntos, dificultad, puntosRival);// guarda las estadísticas de la partida
        if (dificultad != 0) { // si no ha jugado solo
            System.out.println("Los puntos del rival son: " + puntosRival);
            if (puntos > puntosRival) {
                System.out.println("Has ganado");
            } else if (puntos == puntosRival) {
                System.out.println("Habéis empatado");
            } else {
                System.out.println("Has perdido");
            }
        }
        System.out.println("Pulsa 'INTRO' para continuar");
        lector.leerCaracter();
    }
}


