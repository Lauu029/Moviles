public class Board {
    private int colores, intentos, colores_usar;
    private boolean rep_color;
    private int[][] tablero;

    Board(int c, int i, int u, boolean rep) {
        colores = c;
        intentos = i;
        colores_usar = u;
        rep_color = rep;
        tablero = new int[intentos][colores];
    }

    void putColor(int pos, int intento, int ficha) {
        tablero[intento][pos] = ficha;
    }
}
