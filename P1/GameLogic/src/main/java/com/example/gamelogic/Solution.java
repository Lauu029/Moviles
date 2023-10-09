package com.example.gamelogic;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

public class Solution {
    private Map<Integer, Map<Integer, Boolean>> solution_ = new HashMap<>();
    int[] sol;
    boolean win=false;
    private int posCorrecta, colorCorrecto;
    int size_sol;
    void createSolution(Boolean repeat, int color_game, int posible_color) {

        size_sol=color_game;
        sol = new int[size_sol];
        // Definir el rango (por ejemplo, de 1 a 100)
        int minimo = 0;
        int maximo = posible_color-1;

        // Crear una instancia de Random
        Random rand = new Random();


        // Generar un número aleatorio en el rango

        for(int i=0;i<size_sol;i++){
            int color = rand.nextInt(maximo - minimo + 1) + minimo;
            if(!repeat){
                while(solution_.containsKey(color))color = rand.nextInt(maximo - minimo + 1) + minimo;
            }
            if (solution_.containsKey(color))
            solution_.get(color).put(i, false);
            else{
                Map<Integer, Boolean>s=new HashMap<>();
                s.put(i, false);
                solution_.put(color, s);
            }

            sol[i]=color;
        }
    }
    public int[] getSol(){
        return sol;
    }
    public void imprimeSol(){
        for(int i=0;i<sol.length;i++){
            System.out.println(sol[i]);
        }
    }
    public void imprime(){

            System.out.println("colorCorrecto "+colorCorrecto);
            System.out.println("posCorrecto "+posCorrecta);

    }
    public void compureba(int[] possible_sol) {
        posCorrecta = 0;
        colorCorrecto = 0;
        for (int i = 0; i < possible_sol.length; i++) {
            //si la solucion real contiene el color
            if (solution_.containsKey(possible_sol[i])) {
                //vemos si el color esta en la misma posicion que la solucion real
                Map<Integer, Boolean> valor = solution_.get(possible_sol[i]);
                System.out.println("memeti "+i);
                if (valor.containsKey(i)) {

                    posCorrecta++;
                    //si ha sido combrobado antes es porque hay una casila con el mismo color pero no en la misma pos
                    if (valor.get(i)) colorCorrecto--;
                    solution_.get(possible_sol[i]).put(i, true);
                }
                else {
                    int j=0;
                    Iterator<Map.Entry<Integer, Boolean>> iterator = valor.entrySet().iterator();
                    boolean combrobado=true;
                    //combrobamos en el map si hay posiciones en la solucion de colores que no hayamos comprobado antes
                    while (iterator.hasNext()&&combrobado) {
                        Map.Entry<Integer, Boolean> entrada = iterator.next();
                         int clave = entrada.getKey();
                         Boolean vas= entrada.getValue();
                         if(!vas){
                             solution_.get(possible_sol[i]).put(clave, true);

                             combrobado=false;
                         }
                    }
                    if(!combrobado) colorCorrecto++;
                }
            }
        }
        if(posCorrecta==size_sol)win=true;
        imprimeSolution();
        resetearMap();

    }
    public int getposCorrecta(){
        return  posCorrecta;
    }
    public int getColorCorrecto(){
        return  colorCorrecto;
    }
    private void resetearMap(){

        for (Map<Integer, Boolean> mapaInterno : solution_.values()) {

            for (Integer clave : mapaInterno.keySet()) {
                mapaInterno.put(clave, false);
            }
        }
    }
    public void imprimeSolution() {
        for (Map.Entry<Integer, Map<Integer, Boolean>> entradaExterna : solution_.entrySet()) {
            Integer claveExterna = entradaExterna.getKey();
            Map<Integer, Boolean> mapaInterno = entradaExterna.getValue();

            System.out.println("Clave Externa: " + claveExterna);

            for (Map.Entry<Integer, Boolean> entradaInterna : mapaInterno.entrySet()) {
                Integer claveInterna = entradaInterna.getKey();
                Boolean valor = entradaInterna.getValue();

                System.out.println("  Clave Interna: " + claveInterna + ", Valor: " + valor);
            }
        }
    }




}
