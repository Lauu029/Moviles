package com.example.gamelogic;

public class GameLogic {
    public static void main(String[] args) {
        Solution sol=new Solution();
        sol.createSolution(true,5,5);
        sol.imprimeSol();
        int[] miArray = {1,0, 0, 0, 0};

        sol.compureba(miArray);
        sol.imprime();


    }
}

