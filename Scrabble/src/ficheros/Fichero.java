// Código hecho por Juan Arturo Abaurrea Calafell e Iker García De León Seguí
package ficheros;

import scrabble.Palabra;
import java.util.Random;

public class Fichero {// clase en la que ocurre todo lo relacionado con ficheros

    private static final int TAMANYO_MAX = 11; // se sortearán 11 fichas disponibles
    private int numFichas; // letras o fichas totales
    private Palabra[] fichas; // conjunto de fichas o letras
    private Palabra[] tiposFichas; // cada tipo de ficha una vez
    private boolean[] usadas; // cantidad de veces que pueden usarse
    private int[] valores; // el valor de las fichas
    private int[] tiposValores; // el valor de cada tipo de ficha

    private String diccionario; // el diccionario con el que se juega

    // constructor que actualiza el diccionario que se va a usar
    public Fichero(String dic) {// esencial para los métodos "rival" y "buscarYPuntuar"
        diccionario = dic;
    }

    // método que actúa como simulador cuando dificultad está entre 1 y 5
    // y como cerebro superior cuando la dificultad es 6
    // inicialmente iba a estar en Jugar pero fue asignado aquí por la cantidad
    // de usos de métodos y atributos de esta clase, además de abrir y cerrar fichero
    public int rival(int dificultad) {
        // abro el diccionario
        FicheroIn f = new FicheroIn(diccionario);
        // creo instancia de la clase Random (que solo la usará el modo simulador)
        Random r = new Random();
        // declaro y asigno los puntos
        int puntos = 0;
        // booleano que indica que no se quiere parar de buscar mejores palabras
        boolean parado = false;
        // consigo las letras que le han tocado a la IA
        Palabra[] seleccion = generar();
        // creo las palabras auxiliar y la mejor entre ellas
        Palabra aux = f.leerPalabra();// creo la palabra auxiliar y leo la primera palabra del diccionario
        Palabra mejor = new Palabra(aux.toString());// y la mejor (que por ahora es la primera)
        // imprimo la selección de la máquina
        for (int idx = 0; idx < seleccion.length; idx++) {
            System.out.print(seleccion[idx] + " ");
        }// recorro el diccionario
        while (!aux.vacia() && !parado) {
            // si aux está dentro de selección y tiene más valor que la mejor anterior
            if (aux.dentroDeSeleccion(seleccion)
                    && mejor.aPuntos(tiposFichas, tiposValores)
                    < aux.aPuntos(tiposFichas, tiposValores)) {
                // la palabra actual y sus puntos se vuelven los mejores 
                puntos = aux.aPuntos(tiposFichas, tiposValores);
                mejor = new Palabra(aux.toString());
                // la siguiente condición solo se aplica al simulador y es lo
                // que le diferencia al cerebro superior, esta condición hace
                // que se calcule un número aleatorio entre 0 y la dificultad 4 veces
                // el cual si da cero parará la búsqueda de mejores palabras
                // si la dificultad es 5 habrán 10/100 posibilidades de parar
                // si la dificultad es 1 habrán 50/100 posibilidades de parar
                if (dificultad != 6 && r.nextInt(dificultad * 2) == 0) {
                    parado = true;// activo la condición de finalización 
                }
            }// a buscar la siguiente palabra
            aux = f.leerPalabra();
        }
        f.cerrar();// una vez recorrido todo el diccionario se cierra
        System.out.println("");
        if (puntos == 0) {// si la CPU no ha elegido palabra
            System.out.println("El rival no ha podido encontrar palabra, -10 puntos.");
            return -10;
        } else {
            System.out.println(mejor + ": es la palabra del rival, que le otorga " + puntos + " puntos.");
            System.out.println("");
            return puntos;
        }
    }

    // método que imprime el historial de partidas
    public static void verHistorial() {
        FicheroIn f = new FicheroIn("estadisticas.txt");// abro el fichero

        String linea = f.leerLinea();// leo la primera línea
        System.out.println("");
        while (linea != null) { // imprime cada línea hasta que no contenga nada
            System.out.println(linea);
            linea = f.leerLinea();
        }
        f.cerrar();// cierra fichero
    }

    // método que guarda los resultados en el historial de partidas o estadisticas.txt
    public static void guardarRes(Palabra alias, int turnos, int puntos, int dificultad, int puntosIA) {
        FicheroOut e = new FicheroOut("estadisticas.txt");// abro fichero
        String resultados;// creo el texto que escribiré
        String tabs;// creo una variable String 
        if (alias.getLongitud() > 7){
            tabs = "\t";// si el nombre es largo solo un tabulador
        } else{
            tabs = "\t\t";// si es corto pongo dos tabuladores
        }
        if (dificultad==0) {// si ha jugado solo lo indico
            resultados = alias + tabs + turnos + "\t\t" + puntos + "\t\t" + "Solo" + "\t\t" + e.fechaActual();
        } else {// y si ha jugado con el rival muestro los resultados
            resultados = alias + tabs + turnos + "\t\t" + puntos + "\t\t" + dificultad + ": " + puntosIA + " puntos" + "\t" + e.fechaActual();
        }// escribo resultados en estadisticas.txt
        e.escribirRes(resultados);
        e.cerrar();// cierro el fichero
    }

    // método que busca una palabra en el diccionario y si la encuentra puntúa
    public int buscarYPuntuar(Palabra escrita) {
        // abro el diccionario
        FicheroIn f = new FicheroIn(diccionario);
        boolean iguales = false;// pongo que no son iguales
        Palabra aux = f.leerPalabra();// leo la primera palabra
        while (!iguales && !aux.vacia()) {
            // recorro el diccionario, y si alguna encaja paro
            iguales = aux.esIgual(escrita);
            aux = f.leerPalabra();// leo la siguiente palabra
        }
        if (iguales) {// si ha encontrado iguales cuento puntos
            int puntos = escrita.aPuntos(tiposFichas, tiposValores);
            f.cerrar();// cierro el diccionario y devuelvo los puntos
            return puntos;
        }
        f.cerrar();// si no encuentra cierro igualmente
        System.out.println("La palabra escrita no está en el diccionario");
        return -10; // si no existe o no está en el diccionario -10 puntos
    }

    // método que inica el alfabeto
    public void inicioAlfabeto() {
        // abro el fichero que contiene las fichas
        FicheroIn f = new FicheroIn("esp.alf");
        // leo el número total de fichas y convierto los valores char a enteros
        numFichas = f.leerPalabra().convertir();
        // creo un conjunto de fichas con el número de tipos de fichas que hay
        fichas = new Palabra[numFichas];
        // si está usada o no la ficha (todas las posiciones inicializan en false)
        usadas = new boolean[numFichas];
        // el valor de cada ficha
        valores = new int[numFichas];
        // como máximo tendrá su número de fichas (luego lo reduzco)
        tiposFichas = new Palabra[numFichas];
        // el valor de cada tipo de ficha (reduzco la longitud también)
        tiposValores = new int[numFichas];

        if (numFichas > 0) { // si hay fichas 
            int linea = 0;// índice de los tipos de fichas, llamado línea porque representa cada línea
            int ind = 0;// índice de las fichas esparcidas
            // consigo el primer tipo de ficha, la cantidad de veces que se puede usar y su valor
            tiposFichas[linea] = f.leerPalabra();
            Palabra cantidad = f.leerPalabra();
            tiposValores[linea] = f.leerPalabra().convertir(); // convierto Palabra a entero

            while (!tiposFichas[linea].vacia()) { // mientras hayan fichas
                // esparzo la cantidad de veces que está el tipo de ficha y su valor
                for (int idx = 0; idx < cantidad.convertir(); idx++, ind++) {
                    fichas[ind] = tiposFichas[linea];// esparzo las fichas
                    valores[ind] = tiposValores[linea];// esparzo los valores 
                }
                linea++;// aumento porque voy a leer la siguiente línea
                tiposFichas[linea] = f.leerPalabra();
                cantidad = f.leerPalabra();
                tiposValores[linea] = f.leerPalabra().convertir();
            }
            ajustarLongitud(linea);// ajusta la longitud del array
        }
        f.cerrar(); // cierro el fichero, ya no leeré de él
    }

    // método que ajusta la longitud de un array de Palabra y otro de enteros
    private void ajustarLongitud(int longitudReal) {
        // arrays vacíos de la longitud querida
        Palabra[] trueTipos = new Palabra[longitudReal];
        int[] trueTValores = new int[longitudReal];
        // copio cada elemento hasta la longitud real de los nuevos arrays
        for (int idx = 0; idx < longitudReal; idx++) {
            trueTipos[idx] = tiposFichas[idx];
            trueTValores[idx] = tiposValores[idx];
        }// pego el contenido en ambos
        tiposFichas = trueTipos;
        tiposValores = trueTValores;
    }

    // método que coge una ficha aleatoria
    private Palabra fichaAleatoria() {
        Random r = new Random(); // creo instancia
        int n = r.nextInt(numFichas); // ficha entre 0 y el valor de numFichas
        while (usadas[n]) { // si ha sido usada
            n = r.nextInt(numFichas); // busca otra ficha
        }
        usadas[n] = true; // la marco como usada
        return fichas[n]; // devuelve una ficha aletoria no usada antes
    }

    // método que combina las fichas aleatorias
    public Palabra[] generar() {
        Palabra fichasRandom[] = new Palabra[TAMANYO_MAX];
        for (int i = 0; i < TAMANYO_MAX; i++) {
            fichasRandom[i] = fichaAleatoria();
        }// una vez entregadas las fichas aleatorias se asignan a sin usar
        for (int n = 0; n < usadas.length; n++) {
            usadas[n] = false;
        }
        return fichasRandom; // y genera un conjunto de letras aleatorias
    }
}
