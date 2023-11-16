package com.example.androidgame.GameLogic;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

public class Solution {
    private Map<Integer, Map<Integer, Boolean>> solution = new HashMap<>();
    private int[] sol_;
    private boolean win_ = false;
    private int actualTry_ = 0;
    private int correctPos_ = 0, correctColor_ = 0;
    private int solutionSize_;
    private int[][] registeredSols_;

    void createSolution(Boolean repeat, int colorGame, int posibleColor, int maxTries) {

        this.solutionSize_ = colorGame;
        this.sol_ = new int[solutionSize_];
        // Definir el rango (por ejemplo, de 1 a 100)
        int min = 0;
        int max = posibleColor - 1;

        // Crear una instancia de Random
        Random rand = new Random();
        //aqui se guardan las soluciones para luego ser renderizadas,registerSols[x][0] hara ref a la posiciones correctas,
        //registerSols[x][1] hara ref a lod colores correctos
        this.registeredSols_ = new int[maxTries][2];
        // Generar un número aleatorio en el rango

        for (int i = 0; i < solutionSize_; i++) {
            int color = rand.nextInt(max - min + 1) + min;
            if (!repeat) {
                while (solution.containsKey(color))
                    color = rand.nextInt(max - min + 1) + min;
            }
            if (solution.containsKey(color))
                solution.get(color).put(i, false);
            else {
                Map<Integer, Boolean> s = new HashMap<>();
                s.put(i, false);
                solution.put(color, s);
            }

            sol_[i] = color;
        }
    }

    public int[] getSol_() {
        return sol_;
    }

    public void check(int[] possible_sol) {
        correctPos_ = 0;
        correctColor_ = 0;
        for (int i = 0; i < possible_sol.length; i++) {
            //si la solucion real contiene el color
            if (solution.containsKey(possible_sol[i])) {
                //vemos si el color esta en la misma posicion que la solucion real
                Map<Integer, Boolean> value = solution.get(possible_sol[i]);
                if (value.containsKey(i)) {
                    correctPos_++;
                    //si ha sido combrobado antes es porque hay una casila con el mismo color pero no en la misma pos
                    solution.get(possible_sol[i]).put(i, true);
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
                            solution.get(possible_sol[i]).put(key, true);
                            checked = false;
                        }
                    }
                    if (!checked)
                        correctColor_++;
                }
            }
        }
        if (correctPos_ == solutionSize_) win_ = true;
        registeredSols_[actualTry_][0] = correctPos_;
        registeredSols_[actualTry_][1] = correctColor_;

        resetMap();
        actualTry_++;
    }

    public int getCorrectPos(int try_) {
        return registeredSols_[try_][0];
    }

    public int getCorrectColor(int try_) {
        return registeredSols_[try_][1];
    }

    private void resetMap() {
        for (Map.Entry<Integer, Map<Integer, Boolean>> externEntry : solution.entrySet()) {
            Integer externEntryKey = externEntry.getKey();
            Map<Integer, Boolean> internMap = externEntry.getValue();

            for (Map.Entry<Integer, Boolean> internEntry : internMap.entrySet()) {
                Integer internEntryKey = internEntry.getKey();
                solution.get(externEntryKey).put(internEntryKey, false);
            }
        }
        correctColor_ = 0;
        correctPos_ = 0;
    }


}
