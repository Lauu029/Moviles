public class GameInit {
    void crearNiveles(Niveles nivel_){
        int colores,intentos,colores_usar;
        boolean rep_color=true;
        switch (nivel_) {
            case FACIL:
                colores=4;
                intentos=6;
                colores_usar=4;
                rep_color=false;
                break;
            case MEDIO:
                colores=4;
                intentos=8;
                colores_usar=6;
                rep_color=false;
                break;
            case DIFICIL:
                colores=5;
                intentos=10;
                colores_usar=8;
                break;
            case IMPOSIBLE:
                colores=6;
                intentos=10;
                colores_usar=9;
                break;
            default:

        }
    }

}
