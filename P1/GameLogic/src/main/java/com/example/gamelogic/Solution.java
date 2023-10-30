package com.example.gamelogic;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

public class Solution  {
    private Map<Integer, Map<Integer, Boolean>> solution_ = new HashMap<>();
    int[] sol_;
    boolean win_ =false;
    int actualturno_ =0;
    private int posCorrecta_ =0, colorCorrecto_ =0;
    int solutionSize_;
    int[][] registeredSols_;
    void createSolution(Boolean repeat, int color_game, int posible_color,int maxturnos) {

        solutionSize_ =color_game;
        sol_ = new int[solutionSize_];
        // Definir el rango (por ejemplo, de 1 a 100)
        int minimo = 0;
        int maximo = posible_color-1;

        // Crear una instancia de Random
        Random rand = new Random();
        //aqui se guardan las soluciones para luego ser renderizadas,registerSols[x][0] hara ref a la posiciones correctas,
        //registerSols[x][1] hara ref a lod colores correctos
        registeredSols_ =new int[maxturnos][2];
        // Generar un número aleatorio en el rango

        for(int i = 0; i< solutionSize_; i++){
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

            sol_[i]=color;
        }
    }
    public int[] getSol(){
        return sol_;
    }
    public void imprimeSol(){
        System.out.print("Solucion: ");
        for(int i = 0; i< sol_.length; i++){
            System.out.print(sol_[i]);
        }
        System.out.println();
    }
    public void imprime(){

            System.out.println("colorCorrecto "+ colorCorrecto_);
            System.out.println("posCorrecto "+ posCorrecta_);

    }
    public void compureba(int[] possible_sol) {
        posCorrecta_ = 0;
        colorCorrecto_ = 0;
        for (int i = 0; i < possible_sol.length; i++) {
            //si la solucion real contiene el color
            if (solution_.containsKey(possible_sol[i])) {
                //vemos si el color esta en la misma posicion que la solucion real
                Map<Integer, Boolean> valor = solution_.get(possible_sol[i]);
                if (valor.containsKey(i)) {

                    posCorrecta_++;
                    //si ha sido combrobado antes es porque hay una casila con el mismo color pero no en la misma pos

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
                         if(!vas&&possible_sol[clave]!= sol_[clave]){
                             solution_.get(possible_sol[i]).put(clave, true);

                             combrobado=false;
                         }
                    }
                    if(!combrobado){
                        colorCorrecto_++;}
                }
            }
        }
        if(posCorrecta_ == solutionSize_) win_ =true;
        registeredSols_[actualturno_][0]= posCorrecta_;
        registeredSols_[actualturno_][1]= colorCorrecto_;
        imprimeSolution();
        resetearMap();

        actualturno_++;

    }
    public int getTurno(){
        return actualturno_;
    }
    public int getposCorrecta(int turno){
        return  registeredSols_[turno][0];
    }
    public int getColorCorrecto(int turno){
        return  registeredSols_[turno][1];
    }
    private void resetearMap(){


        for (Map.Entry<Integer, Map<Integer, Boolean>> entradaExterna : solution_.entrySet()) {
            Integer claveExterna = entradaExterna.getKey();
            Map<Integer, Boolean> mapaInterno = entradaExterna.getValue();


            for (Map.Entry<Integer, Boolean> entradaInterna : mapaInterno.entrySet()) {
                Integer claveInterna = entradaInterna.getKey();

                solution_.get(claveExterna).put(claveInterna, false);
                Boolean valor = entradaInterna.getValue();

            }
        }
        colorCorrecto_ =0;
        posCorrecta_ =0;

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
