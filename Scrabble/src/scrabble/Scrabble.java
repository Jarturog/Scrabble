// Código hecho por Juan Arturo Abaurrea Calafell e Iker García De León Seguí
package scrabble;

import ficheros.Fichero;

public class Scrabble {

    LT lector = new LT();// creo la instancia lector, la cual usaré como input
    Jugar juego;// declaro la instancia juego (con la que voy a jugar)

    private Palabra alias;// el nombre del jugador
    private int turnos;// turnos indicados

    public static void main(String[] args) {// esta clase es en la que se ejecuta el programa
        Scrabble s = new Scrabble();
        s.inicio();// usaré Inicio como el menú inicial
    }

    private void inicio() {
        boolean salir = false;// declaro booleano necesario para que acabe el while
        while (!salir) {
            System.out.println("");
            System.out.println("// ------ ----- Menú ----- ------ \\\\");
            System.out.println("//                                \\\\");
            System.out.println("//        1 Jugar Scrabble        \\\\");
            System.out.println("//   2 Ver historial de partidas  \\\\");
            System.out.println("//        x Salir del juego       \\\\");
            System.out.println("//                                \\\\");
            System.out.println("// ------ ----- ---- ----- ------ \\\\");
            System.out.print("Opción: ");
            switch (lector.leerCaracter()) {// input
                case '1':// si se ha escrito 1
                    nombreYTurnos();
                    break;
                case '2':// si se ha escrito 2
                    Fichero.verHistorial();// enseño el historial de partidas
                    break;
                case 'x':// si se ha escrito x
                    System.out.println("Fin del juego");
                    salir = true;
                    break;
                case 'X':// independientemente de si es mayúscula
                    System.out.println("Fin del juego");
                    salir = true;
                    break;
                default:
                    System.out.println("Opción incorrecta");
            }
        }
    }

    // subprograma que se encarga de pedir el nombre y los turnos
    private void nombreYTurnos() {
        System.out.println("");
        System.out.println("//        Inserta tu nombre       \\\\");
        System.out.print("Bienvenido: ");
        alias = new Palabra(lector.leerLinea());
        // mientras el nombre sea muy largo o inexistente
        while (alias.getLongitud() > 15 || alias.vacia()) {
            if (alias.vacia()) {// si no ha escrito ningún nombre
                System.out.print("Por favor inserte un nombre: ");
                alias = new Palabra(lector.leerLinea());
            } else {// si se ha pasado de longitud
                System.out.print("Por favor inserte un nombre más corto: ");
                alias = new Palabra(lector.leerLinea());
            }
        }
        System.out.println("");
        System.out.println("// ¿Cuántos turnos quieres jugar?  \\\\");
        System.out.print("Turnos: ");
        Palabra turn = new Palabra(lector.leerLinea());// creo una palabra que sean los turnos
        turnos = turn.convertir();// y convierto los valores char al valor real numérico
        System.out.println("");
        idioma();
    }

    // subprograma que decidirá el diccionario que se usará
    private void idioma() {
        System.out.println("// ----- ----- Idioma ----- ----- \\\\");
        System.out.println("//                                \\\\");
        System.out.println("//       1 Jugar en español       \\\\");
        System.out.println("//       2 Jugar en catalán       \\\\");
        System.out.println("//                                \\\\");
        System.out.println("// ----- ----- ------ ----- ----- \\\\");
        System.out.print("Opción: ");
        switch (lector.leerCaracter()) {
            case '1':
                juego = new Jugar("esp.dic");// diccionario español
                modoJuego();
                break;
            case '2':
                juego = new Jugar("cat.dic");// diccionario catalán
                modoJuego();
                break;
            default:
                System.out.println("Opción incorrecta");
                break;
        }
    }

    // subprograma que consigue la modalidad querida
    private void modoJuego() {
        System.out.println("");
        System.out.println("// --- --- Modo de juego --- --- \\\\");
        System.out.println("//                               \\\\");
        System.out.println("//         1 Jugar solo          \\\\");
        System.out.println("//  2 Jugar contra el ordenador  \\\\");
        System.out.println("//                               \\\\");
        System.out.println("// --- --- --- ----- --- --- --- \\\\");
        System.out.print("Opción: ");
        switch (lector.leerCaracter()) {
            case '1':
                juego.partida(alias, turnos, 0);// dificultad 0, juega solo
                break;
            case '2':
                dificultad();
                break;
            default:
                System.out.println("Opción incorrecta");
        }
    }

    // subprograma que consigue la dificultad querida
    private void dificultad() {
        System.out.println("");
        System.out.println("// ------ ----- Dificultad ----- ------ \\\\");
        System.out.println("//                                      \\\\");
        System.out.println("//       1 Muy fácil                    \\\\");
        System.out.println("//       2 Fácil                        \\\\");
        System.out.println("//       3 Normal                       \\\\");
        System.out.println("//       4 Difícil                      \\\\");
        System.out.println("//       5 Muy difícil                  \\\\");
        System.out.println("//       6 Imposible (cerebro superior) \\\\");
        System.out.println("//                                      \\\\");
        System.out.println("// ------ ----- ---------- ----- ------ \\\\");
        System.out.print("Opción: ");
        char opción = lector.leerCaracter();
        System.out.println("");
        if (opción > '0' && opción < '7') {// si ha escrito alguna opción
            if (opción != '6') {// si no se ha elegido el modo cerebro superior
                System.out.println("Se seleccionó el modo simulador.");
                System.out.println("");
            }// se llama al método Ronda con el alias, los turnos y la dificultad
            juego.partida(alias, turnos, opción - '0');
        } else {//convierto el valor decimal del carácter al valor del entero
            System.out.println("Opción incorrecta");
        }
    }
}
