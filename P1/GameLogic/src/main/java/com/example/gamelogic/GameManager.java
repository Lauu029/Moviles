package com.example.gamelogic;

import com.example.engine.IEngine;

public class GameManager {
    private static GameManager instance;
    private IEngine myEngine_;
    private int width_;
    private int height_;
    // Paso 2: Hacer el constructor privado para evitar la creación de instancias adicionales.
    private GameManager() {
        // Constructor privado
    }

    // Paso 3: Crear un método público para obtener la única instancia de la clase.
    public static GameManager getInstance() {
        // Si la instancia no ha sido creada, la creamos.
        if (instance == null) {
            instance = new GameManager();
        }
        return instance;
    }
    public static int init(IEngine engine,int width,int height){
        instance.myEngine_=engine;
        instance.width_=width;
        instance.height_=height;
        return 1;
    }

    // Ahora, puedes agregar métodos y propiedades a tu clase Singleton.
    // Por ejemplo:
    public void changeScene() {
        System.out.println("Cambio de escena");
    }
}
