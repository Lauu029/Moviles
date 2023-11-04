package com.example.gamelogic;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

public class Solution {
    private Map<Integer, Map<Integer, Boolean>> solution_ = new HashMap<>();
    private int[] sol_;
    private boolean win_ = false;
    private int actualTry = 0;
    private int correctPos = 0, correctColor = 0;
    private int solutionSize_;
    private int[][] registeredSols_;

    void createSolution(Boolean repeat, int color_game, int posible_color, int maxturnos) {

        solutionSize_ = color_game;
        sol_ = new int[solutionSize_];
        // Definir el rango (por ejemplo, de 1 a 100)
        int minimo = 0;
        int maximo = posible_color - 1;

        // Crear una instancia de Random
        Random rand = new Random();
        //aqui se guardan las soluciones para luego ser renderizadas,registerSols[x][0] hara ref a la posiciones correctas,
        //registerSols[x][1] hara ref a lod colores correctos
        registeredSols_ = new int[maxturnos][2];
        // Generar un número aleatorio en el rango

        for (int i = 0; i < solutionSize_; i++) {
            int color = rand.nextInt(maximo - minimo + 1) + minimo;
            if (!repeat) {
                while (solution_.containsKey(color))
                    color = rand.nextInt(maximo - minimo + 1) + minimo;
            }
            if (solution_.containsKey(color))
                solution_.get(color).put(i, false);
            else {
                Map<Integer, Boolean> s = new HashMap<>();
                s.put(i, false);
                solution_.put(color, s);
            }

            sol_[i] = color;
        }
    }

    public int[] getSol() {
        return sol_;
    }

    public void printSol() {
        System.out.print("Solucion: ");
        for (int i = 0; i < sol_.length; i++) {
            System.out.print(sol_[i]);
        }
        System.out.println();
    }

    public void print() {
        System.out.println("colorCorrecto " + correctColor);
        System.out.println("posCorrecto " + correctPos);
    }

    public void check(int[] possible_sol) {
        correctPos = 0;
        correctColor = 0;
        for (int i = 0; i < possible_sol.length; i++) {
            //si la solucion real contiene el color
            if (solution_.containsKey(possible_sol[i])) {
                //vemos si el color esta en la misma posicion que la solucion real
                Map<Integer, Boolean> value = solution_.get(possible_sol[i]);
                if (value.containsKey(i)) {
                    correctPos++;
                    //si ha sido combrobado antes es porque hay una casila con el mismo color pero no en la misma pos
                    solution_.get(possible_sol[i]).put(i, true);
                } else {
                    int j = 0;
                    Iterator<Map.Entry<Integer, Boolean>> iterator = value.entrySet().iterator();
                    boolean checked = true;
                    //combrobamos en el map si hay posiciones en la solucion de colores que no hayamos comprobado antes
                    while (iterator.hasNext() && checked) {
                        Map.Entry<Integer, Boolean> entry = iterator.next();
                        int key = entry.getKey();
                        Boolean val = entry.getValue();
                        if (!val && possible_sol[key] != sol_[key]) {
                            solution_.get(possible_sol[i]).put(key, true);
                            checked = false;
                        }
                    }
                    if (!checked)
                        correctColor++;
                }
            }
        }
        if (correctPos == solutionSize_) win_ = true;
        registeredSols_[actualTry][0] = correctPos;
        registeredSols_[actualTry][1] = correctColor;
        printSolution();
        resetMap();
        actualTry++;
    }

    public int getTry() {
        return actualTry;
    }

    public int getCorrectPos(int try_) {
        return registeredSols_[try_][0];
    }

    public int getCorrectColor(int try_) {
        return registeredSols_[try_][1];
    }

    private void resetMap() {
        for (Map.Entry<Integer, Map<Integer, Boolean>> externEntry : solution_.entrySet()) {
            Integer externEntryKey = externEntry.getKey();
            Map<Integer, Boolean> internMap = externEntry.getValue();

            for (Map.Entry<Integer, Boolean> internEntry : internMap.entrySet()) {
                Integer internEntryKey = internEntry.getKey();
                solution_.get(externEntryKey).put(internEntryKey, false);
            }
        }
        correctColor = 0;
        correctPos = 0;
    }

    public void printSolution() {
        for (Map.Entry<Integer, Map<Integer, Boolean>> externEntry : solution_.entrySet()) {
            Integer externEntryKey = externEntry.getKey();
            Map<Integer, Boolean> internMap = externEntry.getValue();

            System.out.println("Clave Externa: " + externEntryKey);

            for (Map.Entry<Integer, Boolean> internEntry : internMap.entrySet()) {
                Integer internEntryKey = internEntry.getKey();
                Boolean value = internEntry.getValue();
                System.out.println("  Clave Interna: " + internEntryKey + ", Valor: " + value);
            }
        }
    }
}
