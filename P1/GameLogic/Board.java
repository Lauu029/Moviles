public class Board {
    private int colores, intentos, colores_usar;
    private int intentoActual;
    private boolean rep_color;
    private int[][] tablero;

    Board(int c, int i, int u, boolean rep) {
        colores = c;
        intentos = i;
        colores_usar = u;
        rep_color = rep;
        intentoActual=0;
        tablero = new int[intentos][colores];
    }

    void putColor(int pos, int ficha) {
        tablero[intentoActual][pos] = ficha;
    }
    void siguienteIntento(){
        intentoActual++;
    }
}
