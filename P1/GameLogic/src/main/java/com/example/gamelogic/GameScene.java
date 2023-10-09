package com.example.gamelogic;

public class GameScene implements com.example.engine.Scene {
    Solution sol;
    @Override
    public void init() {
        //creacion de la solucion
        sol=new Solution();
        sol.createSolution(true,5,8);
        sol.imprimeSol();
        int[] miArray = {1,2, 3, 4, 0};

        sol.compureba(miArray);
        sol.imprime();
    }
    @Override
    public void render() {

    }

    @Override
    public void update() {

    }


}
